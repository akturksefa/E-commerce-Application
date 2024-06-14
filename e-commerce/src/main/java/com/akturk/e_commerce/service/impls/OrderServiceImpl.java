package com.akturk.e_commerce.service.impls;


import com.akturk.e_commerce.dto.requests.OrderRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.CartNotFoundException;
import com.akturk.e_commerce.exceptions.OrderNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserNotFoundException;
import com.akturk.e_commerce.model.Cart;

import com.akturk.e_commerce.model.Order;
import com.akturk.e_commerce.repository.CartRepository;
import com.akturk.e_commerce.repository.OrderRepository;
import com.akturk.e_commerce.repository.ProductRepository;
import com.akturk.e_commerce.repository.UserRepository;
import com.akturk.e_commerce.service.NotificationService;
import com.akturk.e_commerce.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NotificationService notificationService;


    public ResponseEntity<ApiResponseDto<?>> createOrder(OrderRequestDto request) throws UserNotFoundException, ServiceLogicException, CartNotFoundException {

        try {

            Cart cart = this.cartRepository.findById(request.getCartId()).orElseThrow(() -> new CartNotFoundException("No cart found for id " + request.getCartId()));

            if (cart.getCartItems().isEmpty()) {
                throw new CartNotFoundException("No items in the cart!");
            }

            Order order = orderRequestDtoToOrder(request, cart);

            order = orderRepository.insert(order);
            clearCart(cart);
            notificationService.sendOrderConfirmationEmail(userRepository.findById(cart.getUserId()).orElse(null), order);

            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Order has been successfully placed!")
                            .build()
            );
        }catch (CartNotFoundException e) {
            throw new CartNotFoundException(e.getMessage());
        }catch (Exception e) {
            log.error("Failed to create order: " + e.getMessage());
            throw new ServiceLogicException("Unable to proceed order!");
        }
    }

    public ResponseEntity<ApiResponseDto<?>> getOrdersByUser(String userId) throws UserNotFoundException, ServiceLogicException {
        try {
            if (userRepository.existsById(userId)) {
                Set<Order> orders = orderRepository.findByUserIdOrderByIdDesc(userId);
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message(orders.size() + " orders found!")
                                .response(orders)
                                .build()
                );

            }
        }catch (Exception e) {
            log.error("Failed to create order: " + e.getMessage());
            throw new ServiceLogicException("Unable to find orders!");
        }
        throw new UserNotFoundException("User not found wit id " + userId);
    }


    public ResponseEntity<ApiResponseDto<?>> cancelOrder(String orderId) throws ServiceLogicException, OrderNotFoundException {
        try {
            if(orderRepository.existsById(orderId)) {
                Order order = orderRepository.findById(orderId).orElse(null);
                orderRepository.save(order);
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message("Order successfully cancelled")
                                .build()
                );
            }
        }catch (Exception e) {
            log.error("Failed to create order: " + e.getMessage());
            throw new ServiceLogicException("Unable to find orders!");
        }
        throw new OrderNotFoundException("Order not found with id " + orderId);
    }

    private void clearCart(Cart cart) {
        cart.setCartItems(new HashSet<>());
        cartRepository.save(cart);
    }

    private Order orderRequestDtoToOrder(OrderRequestDto request, Cart cart) {
        return Order.builder()
                .userId(cart.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .phoneNo(request.getPhoneNo())
                .orderAmt(request.getTotal())
                .orderItems(cart.getCartItems())
                .build();
    }

}
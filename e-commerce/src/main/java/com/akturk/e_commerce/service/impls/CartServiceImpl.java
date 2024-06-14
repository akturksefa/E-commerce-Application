package com.akturk.e_commerce.service.impls;

import com.akturk.e_commerce.dto.requests.CartItemRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.dto.responses.CartResponseDto;
import com.akturk.e_commerce.exceptions.CartNotFoundException;
import com.akturk.e_commerce.exceptions.ProductNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserNotFoundException;
import com.akturk.e_commerce.model.Cart;
import com.akturk.e_commerce.model.CartItem;
import com.akturk.e_commerce.model.Product;
import com.akturk.e_commerce.repository.CartRepository;
import com.akturk.e_commerce.repository.ProductRepository;
import com.akturk.e_commerce.repository.UserRepository;
import com.akturk.e_commerce.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Set;
import java.util.HashSet;
@Component
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;


    public ResponseEntity<ApiResponseDto<?>> addItemToCart (CartItemRequestDto requestDto) throws ServiceLogicException, UserNotFoundException, ProductNotFoundException {
        try{
            if(!userRepository.existsById(requestDto.getUserId())) {
                throw new UserNotFoundException("User Not Found With id" + requestDto.getUserId());

            }
            if (!productRepository.existsById(requestDto.getProductId())) {
                throw new ProductNotFoundException("Product Not Found With id" + requestDto.getProductId());

            }
            Cart userCart = getCart(requestDto.getUserId());
            Set<CartItem> userCartItems = userCart.getCartItems();
            CartItem cartItem = createCartItem(userCartItems,requestDto);

            userCartItems.add(cartItem);
            userCart.setCartItems(userCartItems);

            cartRepository.save(userCart);
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Item successfully added to cart!")
                            .build()
            );
        }catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }catch (ProductNotFoundException e) {
            throw  new ProductNotFoundException(e.getMessage());
        }catch (Exception e) {
            log.error("Failed to add item to cart: " + e.getMessage());
            throw new ServiceLogicException("Unable to add item to cart!");
        }

    }

    public ResponseEntity<ApiResponseDto<?>> getCartItemsByUser(String userId) throws UserNotFoundException , ServiceLogicException {
        try {
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User not found with id " + userId);
            }

            if (!cartRepository.existsByUserId(userId)) {
                createAndSaveNewCart(userId);
            }
            Cart userCart = cartRepository.findByUserId(userId);

            CartResponseDto cartResponse = cartToCartResponseDto(userCart);

            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .response(cartResponse)
                            .build()
            );
        }catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }catch (Exception e) {
            log.error("Failed to find cart: " + e.getMessage());
            throw new ServiceLogicException("Unable to find cart!");
        }
    }


    public ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(String userId, String productId) throws ServiceLogicException {
        try {
            if(!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User Not Found with id" + userId);
            }
            if (!productRepository.existsById(productId)) {
                throw new ProductNotFoundException("Product Not Found with id" + productId);
            }
            Product product = getProduct(productId);

            if(!cartRepository.existsByUserId(userId)) {
                throw new CartNotFoundException("No cart found for user " + userId);
            }
            Cart userCart = cartRepository.findByUserId(userId);
            Set<CartItem> removedItemsSet = removeCartItem(userCart.getCartItems(), product);
            userCart.setCartItems(removedItemsSet);
            cartRepository.save(userCart);

            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Item successfully removed to cart!")
                            .build()
            );
        }catch (Exception e) {
            log.error("Failed to add item to cart: " + e.getMessage());
            throw new ServiceLogicException("Unable to add item to cart!");
        }
        }


    private Set<CartItem> removeCartItem(Set<CartItem> userCartItems, Product product) {
        CartItem existingCartItem = getExistingCartItem(userCartItems, product);

        if (existingCartItem != null) {
            userCartItems.remove(existingCartItem);
        }

        return userCartItems;
    }

    private void createAndSaveNewCart(String userId) {
        if(!cartRepository.existsByUserId(userId)) {
            Cart cart = Cart.builder()
                    .userId(userId)
                    .cartItems(new HashSet<>())
                    .build();
            cartRepository.insert(cart);
        }
    }

    private Cart getCart(String userId) {
        //  if cart is not already present create new cart
        createAndSaveNewCart(userId);
        return cartRepository.findByUserId(userId);
    }

    private Product getProduct(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    private CartItem createCartItem(Set<CartItem> userCartItems, CartItemRequestDto requestDto) {

        Product product = getProduct(requestDto.getProductId());
        CartItem cartItem = getExistingCartItem(userCartItems, product);

        if (cartItem == null) {
            cartItem = getNewCartItem(product, requestDto);
        }else {
            if (requestDto.getQuantity() <= 0) requestDto.setQuantity(-1);
            if (requestDto.getQuantity() > 0) requestDto.setQuantity(1);
            if (cartItem.getQuantity() + requestDto.getQuantity() <= 0) requestDto.setQuantity(0);
            userCartItems.remove(cartItem);
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
            cartItem.setTotalPrice(Double.parseDouble(String.format("%.2f", cartItem.getQuantity() * product.getPrice())));
        }

        return cartItem;
    }
    private CartItem getExistingCartItem(Set<CartItem> userCartItems, Product product) {
        List<CartItem> existingCartItems = userCartItems.stream().filter(item -> item.getProduct().getId().equals(product.getId())).toList();
        if (existingCartItems.isEmpty()){
            return null;
        }
        return existingCartItems.get(0);
    }

    private CartItem getNewCartItem(Product product, CartItemRequestDto requestDto) {
        return CartItem.builder()
                .product(productRepository.findById(requestDto.getProductId()).orElse(null))
                .quantity(1)
                .totalPrice(product.getPrice() * requestDto.getQuantity())
                .build();
    }

    private CartResponseDto cartToCartResponseDto(Cart userCart) {
        int noOfCartItems = 0;
        double subtotal = 0.0;

        for(CartItem cartItem: userCart.getCartItems()) {
            noOfCartItems += cartItem.getQuantity();
            subtotal += cartItem.getTotalPrice();
        }

        return CartResponseDto.builder()
                .cartId(userCart.getId())
                .cartItems(userCart.getCartItems())
                .noOfCartItems(noOfCartItems)
                .subtotal(subtotal)
                .build();
    }

}

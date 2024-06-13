package com.akturk.e_commerce.service.impls;

import com.akturk.e_commerce.repository.CartRepository;
import com.akturk.e_commerce.repository.OrderRepository;
import com.akturk.e_commerce.repository.ProductRepository;
import com.akturk.e_commerce.repository.UserRepository;
import com.akturk.e_commerce.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl {

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


}

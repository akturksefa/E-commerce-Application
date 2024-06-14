package com.akturk.e_commerce.controller;

import com.akturk.e_commerce.dto.requests.OrderRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.CartNotFoundException;
import com.akturk.e_commerce.exceptions.OrderNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserNotFoundException;
import com.akturk.e_commerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<?>> createOrder(OrderRequestDto request) throws UserNotFoundException, ServiceLogicException , CartNotFoundException
    {
        return orderService.createOrder(request);
    }

    @GetMapping("/get/byUser")
    public ResponseEntity<ApiResponseDto<?>> getOrdersByUser (String userId) throws UserNotFoundException, ServiceLogicException
    {
        return orderService.getOrdersByUser(userId);
    }

    @PatchMapping("/cancel")
    public ResponseEntity<ApiResponseDto<?>> cancelOrder(String orderId) throws ServiceLogicException, OrderNotFoundException
    {
        return orderService.cancelOrder(orderId);
    }
}

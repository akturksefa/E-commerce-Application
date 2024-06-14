package com.akturk.e_commerce.service;

import com.akturk.e_commerce.dto.requests.OrderRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.CartNotFoundException;
import com.akturk.e_commerce.exceptions.OrderNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    ResponseEntity<ApiResponseDto<?>> createOrder(OrderRequestDto request) throws UserNotFoundException, ServiceLogicException, CartNotFoundException;
    ResponseEntity<ApiResponseDto<?>> getOrdersByUser(String userId) throws UserNotFoundException, ServiceLogicException;
    ResponseEntity<ApiResponseDto<?>> cancelOrder(String orderId) throws ServiceLogicException, OrderNotFoundException;

}

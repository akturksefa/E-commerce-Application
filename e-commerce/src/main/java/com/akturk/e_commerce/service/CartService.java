package com.akturk.e_commerce.service;

import com.akturk.e_commerce.dto.requests.CartItemRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.ProductNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

     ResponseEntity<ApiResponseDto<?>> addItemToCart (CartItemRequestDto requestDto) throws ServiceLogicException, UserNotFoundException, ProductNotFoundException ;
     ResponseEntity<ApiResponseDto<?>> getCartItemsByUser(String userId) throws UserNotFoundException , ServiceLogicException;
     ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(String userId, String productId) throws ServiceLogicException;


}

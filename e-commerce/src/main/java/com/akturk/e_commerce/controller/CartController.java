package com.akturk.e_commerce.controller;

import com.akturk.e_commerce.dto.requests.CartItemRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.ProductNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserNotFoundException;
import com.akturk.e_commerce.model.Cart;
import com.akturk.e_commerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private  CartService cartService;

    @PostMapping("/add")
    ResponseEntity<ApiResponseDto<?>> addItemToCart(@RequestBody CartItemRequestDto requestDto) throws UserNotFoundException, ServiceLogicException, ProductNotFoundException {
        return cartService.addItemToCart(requestDto);
    }

    @DeleteMapping("/remove")
    ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(@RequestParam String userId, @RequestParam String productId) throws ServiceLogicException {
        return cartService.removeCartItemFromCart(userId , productId);
    }

    @GetMapping("/get/byUser")
    ResponseEntity<ApiResponseDto<?>> getCartItemsByUser (@RequestParam String id) throws UserNotFoundException , ServiceLogicException {
        return cartService.getCartItemsByUser(id);

    }
}

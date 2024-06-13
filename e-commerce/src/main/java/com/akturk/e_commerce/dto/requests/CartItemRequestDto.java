package com.akturk.e_commerce.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemRequestDto {
    private String userId;
    private String productId;
    private int quantity;
}
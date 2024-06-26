package com.akturk.e_commerce.dto.responses;

import com.akturk.e_commerce.model.CartItem;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CartResponseDto {

    private String cartId;
    private Set<CartItem> cartItems;
    private int noOfCartItems;
    private double subtotal;

}
package com.akturk.e_commerce.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private Product product;

    private int quantity;

    private double totalPrice;

}

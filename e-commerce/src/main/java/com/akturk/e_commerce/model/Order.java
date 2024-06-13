package com.akturk.e_commerce.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String userId;

    private String firstName;

    private String lastName;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String phoneNo;

    private double orderAmt;

    private LocalDateTime placedOn;

    private Set<CartItem> orderItems;

}
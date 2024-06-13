package com.akturk.e_commerce.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    private String productName;

    private double price;

    private String description;

    private String imageUrl;

    private Category category;

}
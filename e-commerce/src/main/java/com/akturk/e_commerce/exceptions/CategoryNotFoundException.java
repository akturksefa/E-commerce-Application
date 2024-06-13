package com.akturk.e_commerce.exceptions;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(String message){
        super(message);
    }

}
package com.akturk.e_commerce.exceptions;

public class CategoryAlreadyExistsException extends Exception{
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
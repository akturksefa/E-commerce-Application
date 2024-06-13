package com.akturk.e_commerce.exceptions;

public class CartNotFoundException extends Exception{

    public CartNotFoundException(String message){
        super(message);
    }
}

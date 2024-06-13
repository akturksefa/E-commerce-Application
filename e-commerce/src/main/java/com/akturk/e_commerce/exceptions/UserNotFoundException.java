package com.akturk.e_commerce.exceptions;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message){
        super(message);
    }
}

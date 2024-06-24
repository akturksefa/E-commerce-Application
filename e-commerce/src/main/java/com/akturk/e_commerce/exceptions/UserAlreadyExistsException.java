package com.akturk.e_commerce.exceptions;

public class UserAlreadyExistsException extends  Exception{

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

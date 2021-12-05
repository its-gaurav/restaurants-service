package com.gaurav.restaurantsservice.exceptions;

public class InvalidUserIdException extends RuntimeException{

    public InvalidUserIdException(String message) {
        super(message);
    }
}

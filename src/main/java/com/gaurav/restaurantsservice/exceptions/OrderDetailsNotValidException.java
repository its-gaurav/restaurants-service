package com.gaurav.restaurantsservice.exceptions;

public class OrderDetailsNotValidException extends RuntimeException{

    public OrderDetailsNotValidException(String message) {
        super(message);
    }
}

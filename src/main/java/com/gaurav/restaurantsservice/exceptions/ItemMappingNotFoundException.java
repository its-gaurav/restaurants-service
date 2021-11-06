package com.gaurav.restaurantsservice.exceptions;

public class ItemMappingNotFoundException extends RuntimeException{

    public ItemMappingNotFoundException(String message) {
        super(message);
    }
}

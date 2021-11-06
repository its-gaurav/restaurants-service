package com.gaurav.restaurantsservice.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryAlreadyExistException extends RuntimeException{

    private final List<String> messages;

    public CategoryAlreadyExistException(List<String> messages) {
        this.messages = messages;
    }
}

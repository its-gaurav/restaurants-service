package com.gaurav.restaurantsservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String detail;
    private List<ServiceExceptions> serviceExceptions;

    public ExceptionResponse(Date timestamp, String message, String detail) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
    }
}

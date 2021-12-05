package com.gaurav.restaurantsservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessLogicException extends RuntimeException{

    private List<ServiceException> serviceExceptions;
    private HttpStatus httpStatus;

    public static BusinessLogicException badRequest(List<ServiceException> serviceExceptions){
        BusinessLogicException businessLogicException = new BusinessLogicException();
        businessLogicException.serviceExceptions = serviceExceptions;
        businessLogicException.httpStatus = HttpStatus.BAD_REQUEST;
        return businessLogicException;
    }

    public static BusinessLogicException conflict(List<ServiceException> serviceExceptions){
        BusinessLogicException businessLogicException = new BusinessLogicException();
        businessLogicException.serviceExceptions = serviceExceptions;
        businessLogicException.httpStatus = HttpStatus.CONFLICT;
        return businessLogicException;
    }

    public static BusinessLogicException notFound(List<ServiceException> serviceExceptions){
        BusinessLogicException businessLogicException = new BusinessLogicException();
        businessLogicException.serviceExceptions = serviceExceptions;
        businessLogicException.httpStatus = HttpStatus.NOT_FOUND;
        return businessLogicException;
    }
}

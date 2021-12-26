package com.gaurav.restaurantsservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private String errorMessage;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        errorMessage = "generic exception";
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),errorMessage, request.getDescription(false));
        logger.error(errorMessage,ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public final ResponseEntity<Object> handleRestaurantNotFoundException(RestaurantNotFoundException ex, WebRequest request){
        return handleNotFoundException(ex, request);
    }

    private ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        errorMessage = ex.getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),errorMessage, request.getDescription(false));
        logger.error(errorMessage, ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public final ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request){
        return handleNotFoundException(ex, request);
    }

    @ExceptionHandler(InvalidItemMappingIdException.class)
    public final ResponseEntity<Object> handleInvalidItemMappingIdException(ItemNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public final ResponseEntity<Object> handleCategoryAlreadyExistException(CategoryAlreadyExistException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessages().toString(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserIdException.class)
    public final ResponseEntity<Object> handleInvalidUserIdException(InvalidUserIdException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public final ResponseEntity<Object> handleBusinessLogicException(BusinessLogicException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false), ex.getServiceExceptions());
        return new ResponseEntity<>(exceptionResponse, ex.getHttpStatus());
    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public final ResponseEntity<Object> handleInvalidFormatException(HttpMessageNotReadableException ex, WebRequest request){
//        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
//    }

    /*
     * Whenever a validation added on instance variable fails e.g @Size(min=2)
     * This method would be invoked
     * */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),"Validation Failed", validationMessage(ex));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),"JSON Conversion failed",ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private String validationMessage(MethodArgumentNotValidException ex) {
        return ex.getFieldError().getField()+": "+ex.getFieldError().getDefaultMessage();
    }
}

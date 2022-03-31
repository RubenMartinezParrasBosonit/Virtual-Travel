package com.ruben.backweb.shared.exceptions;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public final ResponseEntity<CustomError> handleNotFoundException(IdNotFoundException ex, WebRequest request) {
        CustomError exceptionResponse = new CustomError(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), "INFO");
        return new ResponseEntity<CustomError>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public final ResponseEntity<CustomError> handleEmailNotFoundException(EmailNotFoundException ex, WebRequest request) {
        CustomError exceptionResponse = new CustomError(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), "INFO");
        return new ResponseEntity<CustomError>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusWithoutEspaceException.class)
    public final ResponseEntity<CustomError> handleBusWithoutEspaceException(BusWithoutEspaceException ex, WebRequest request) {
        CustomError exceptionResponse = new CustomError(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "INFO");
        return new ResponseEntity<CustomError>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<CustomError> handleBusWithoutEspaceExceptionFeign(FeignException ex, WebRequest request) {
        CustomError exceptionResponse = new CustomError(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "INFO");
        return new ResponseEntity<CustomError>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

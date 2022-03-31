package com.ruben.backweb.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EmailNotFoundException(String message) {
        super(message);
    }

}

package com.ruben.backempresa.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BusWithoutEspaceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BusWithoutEspaceException(String message) {
        super(message);
    }

}

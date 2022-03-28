package com.ruben.backempresa.shared.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class CustomError {


    private Date timestamp;
    int HttpCode;
    String mensaje;
    String tipo;

}

package com.ruben.backempresa.correo.infraestructure.controller.dtos.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class CorreoInputDto {

    @NotNull
    @NotBlank
    private String ciudadDestino;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    private Date fechaReserva;

    @NotNull
    private Float horaReserva;

}

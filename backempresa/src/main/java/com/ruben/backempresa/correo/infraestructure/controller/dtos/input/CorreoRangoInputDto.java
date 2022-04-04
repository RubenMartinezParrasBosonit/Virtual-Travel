package com.ruben.backempresa.correo.infraestructure.controller.dtos.input;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class CorreoRangoInputDto {
    @NotNull
    private String ciudadDestino;

    @NotNull
    private Date fechaSuperior;

    @NotNull
    private Date fechaInferior;

    @NotNull
    private Float horaInferior;

    @NotNull
    private Float horaSuperior;
}

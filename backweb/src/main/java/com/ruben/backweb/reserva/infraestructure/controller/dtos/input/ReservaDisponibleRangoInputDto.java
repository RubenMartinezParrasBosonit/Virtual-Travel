package com.ruben.backweb.reserva.infraestructure.controller.dtos.input;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class ReservaDisponibleRangoInputDto {

    @NotNull
    private Date fechaSuperior;

    @NotNull
    private Date fechaInferior;

    @NotNull
    private Float horaInferior;

    @NotNull
    private Float horaSuperior;
}

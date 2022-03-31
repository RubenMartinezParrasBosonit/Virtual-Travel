package com.ruben.backweb.reserva.infraestructure.controller.dtos.input;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class ReservaDisponibleInputDto {
    @NotNull
    @NotBlank
    private String ciudadDestino;

    @NotNull
    private Date fechaSalida;

    @NotNull
    private Float horaReserva;
}

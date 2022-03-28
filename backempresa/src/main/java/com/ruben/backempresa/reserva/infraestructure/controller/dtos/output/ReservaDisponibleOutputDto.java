package com.ruben.backempresa.reserva.infraestructure.controller.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class ReservaDisponibleOutputDto {
    @NotNull
    @NotBlank
    private String ciudadDestino;

    @NotNull
    private Date fechaSalida;

    @NotNull
    private Float horaReserva;

    @NotNull Integer numeroPlazas;
}

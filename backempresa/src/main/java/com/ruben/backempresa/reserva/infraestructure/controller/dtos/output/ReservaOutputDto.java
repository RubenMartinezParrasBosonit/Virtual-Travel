package com.ruben.backempresa.reserva.infraestructure.controller.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class ReservaOutputDto {

    @NotNull
    @NotBlank
    private String ciudadDestino;

    @NotNull
    @NotBlank
    private String nombre;

    @NotNull
    @NotBlank
    private String apellido;

    @NotNull
    @NotBlank
    private String telefono;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Date fechaReserva;

    @NotNull
    private Float horaReserva;

}

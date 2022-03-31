package com.ruben.backweb.reserva.infraestructure.controller.dtos.output;

import com.ruben.backweb.reserva.domain.ReservaDisponible;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDisponibleOutputDto {
    @NotNull
    @NotBlank
    private String ciudadDestino;

    @NotNull
    private Date fechaSalida;

    @NotNull
    private Float horaReserva;

    @NotNull Integer numeroPlazas;

    public ReservaDisponibleOutputDto (ReservaDisponible reservaDisponible){
        this.ciudadDestino = reservaDisponible.getCiudadDestino();
        this.fechaSalida = reservaDisponible.getFechaSalida();
        this.horaReserva = reservaDisponible.getHoraSalida();
        this.numeroPlazas = reservaDisponible.getNumeroPlazas();
    }
}

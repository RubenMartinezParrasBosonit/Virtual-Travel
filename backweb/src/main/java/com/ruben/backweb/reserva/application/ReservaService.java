package com.ruben.backweb.reserva.application;

import com.ruben.backweb.reserva.infraestructure.controller.dtos.input.ReservaDisponibleInputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.input.ReservaDisponibleRangoInputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaDisponibleOutputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;

import java.util.List;

public interface ReservaService {
    void hacerReserva(ReservaOutputDto reservaOutputDto);
    ReservaDisponibleOutputDto buscarDisponibilidad(ReservaDisponibleInputDto reservaDisponibleInputDto);
    List<ReservaDisponibleOutputDto> buscarReservasDisponibles (String ciudadDestino, ReservaDisponibleRangoInputDto reservaDisponibleRangoInputDto);
    ReservaOutputDto hacerReservaKafka(ReservaOutputDto reservaOutputDto);
    public void escucharReservaKafka(ReservaOutputDto reservaOutputDto);

}

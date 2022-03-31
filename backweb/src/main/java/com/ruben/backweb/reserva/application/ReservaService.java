package com.ruben.backweb.reserva.application;

import com.ruben.backweb.reserva.domain.Reserva;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.input.ReservaDisponibleInputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaDisponibleOutputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;

public interface ReservaService {
    void hacerReserva(ReservaOutputDto reservaOutputDto);
    ReservaDisponibleOutputDto buscarDisponibilidad(ReservaDisponibleInputDto reservaDisponibleInputDto);
    ReservaOutputDto hacerReservaKafka(ReservaOutputDto reservaOutputDto);
    public void escucharReservaKafka(ReservaOutputDto reservaOutputDto);

}

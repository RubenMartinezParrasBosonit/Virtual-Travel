package com.ruben.backempresa.reserva.application;

import com.ruben.backempresa.reserva.domain.Reserva;
import com.ruben.backempresa.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;

public interface ReservaService {

    ReservaOutputDto hacerReserva(ReservaOutputDto reservaOutputDto);

}

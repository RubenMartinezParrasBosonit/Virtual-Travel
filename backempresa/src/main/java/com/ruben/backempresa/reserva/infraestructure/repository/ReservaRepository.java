package com.ruben.backempresa.reserva.infraestructure.repository;

import com.ruben.backempresa.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByEmailAndCiudadDestinoAndFechaReservaAndHoraReserva(String email, String ciudadDestino, Date fechaReserva, Float horaReserva);

}

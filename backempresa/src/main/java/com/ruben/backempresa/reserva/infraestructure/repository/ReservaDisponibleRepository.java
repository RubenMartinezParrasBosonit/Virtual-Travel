package com.ruben.backempresa.reserva.infraestructure.repository;

import com.ruben.backempresa.reserva.domain.ReservaDisponible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservaDisponibleRepository extends JpaRepository<ReservaDisponible, Integer> {
    List<ReservaDisponible> findByCiudadDestino (String ciudadDestino);
}

package com.ruben.backweb.reserva.infraestructure.repository;

import com.ruben.backweb.reserva.domain.ReservaDisponible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaDisponibleRepository extends JpaRepository<ReservaDisponible, Integer> {
    List<ReservaDisponible> findByCiudadDestino (String ciudadDestino);
}

package com.ruben.backempresa.reserva.infraestructure.repository;

import com.ruben.backempresa.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}

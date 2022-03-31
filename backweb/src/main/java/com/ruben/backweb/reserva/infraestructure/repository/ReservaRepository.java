package com.ruben.backweb.reserva.infraestructure.repository;

import com.ruben.backweb.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}

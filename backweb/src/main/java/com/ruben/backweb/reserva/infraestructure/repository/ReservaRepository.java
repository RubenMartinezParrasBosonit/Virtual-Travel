package com.ruben.backweb.reserva.infraestructure.repository;

import com.ruben.backweb.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query(value = "SELECT * FROM reserva r WHERE r.fecha_reserva<=:fechaSuperior AND r.fecha_reserva>=:fechaInferior" +
            " AND r.hora_reserva<=:horaSuperior AND r.hora_reserva>=:horaInferior AND r.ciudad_destino = :ciudadDestino"
            , nativeQuery = true)
    List<Reserva> findReservaConRangos(@Param("ciudadDestino") String ciudadDestino, @Param("fechaSuperior") Date fechaSuperior, @Param("fechaInferior") Date fechaInferior
            , @Param("horaInferior") Float horaInferior, @Param("horaSuperior") Float horaSuperior);
}

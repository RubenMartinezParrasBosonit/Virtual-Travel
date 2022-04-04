package com.ruben.backweb.reserva.infraestructure.repository;

import com.ruben.backweb.reserva.domain.ReservaDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservaDisponibleRepository extends JpaRepository<ReservaDisponible, Integer> {
    List<ReservaDisponible> findByCiudadDestino (String ciudadDestino);
    ReservaDisponible findByCiudadDestinoAndHoraSalidaAndFechaSalida(String ciudadDestino, Float horaSalida, Date fechaSalida);

    @Query(value = "SELECT * FROM reservadisponible r WHERE r.fecha_salida<=:fechaSuperior AND r.fecha_salida>=:fechaInferior" +
            " AND r.hora_salida<=:horaSuperior AND r.hora_salida>=:horaInferior AND r.ciudad_destino = :ciudadDestino"
            , nativeQuery = true)
    List<ReservaDisponible> findReservaConRangos(@Param("ciudadDestino") String ciudadDestino, @Param("fechaSuperior") Date fechaSuperior, @Param("fechaInferior") Date fechaInferior
            , @Param("horaInferior") Float horaInferior, @Param("horaSuperior") Float horaSuperior);
}

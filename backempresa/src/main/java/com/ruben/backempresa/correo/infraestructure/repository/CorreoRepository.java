package com.ruben.backempresa.correo.infraestructure.repository;

import com.ruben.backempresa.correo.domain.Correo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CorreoRepository extends JpaRepository<Correo, Integer> {

    List<Correo> findByEmail(String email);

    @Query(value = "SELECT * FROM correo c WHERE c.fecha_reserva<=:fechaSuperior AND c.fecha_reserva>=:fechaInferior" +
            " AND c.hora_reserva<=:horaSuperior AND c.hora_reserva>=:horaInferior AND c.ciudad_destino = :ciudadDestino"
            , nativeQuery = true)
    List<Correo> findCorreoConRangos(@Param("ciudadDestino") String ciudadDestino, @Param("fechaSuperior") Date fechaSuperior, @Param("fechaInferior") Date fechaInferior
            , @Param("horaInferior") Float horaInferior, @Param("horaSuperior") Float horaSuperior);
}

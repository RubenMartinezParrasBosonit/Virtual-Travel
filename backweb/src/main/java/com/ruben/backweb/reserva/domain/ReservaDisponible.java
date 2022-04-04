package com.ruben.backweb.reserva.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservadisponible")
public class ReservaDisponible {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @NotNull
    @Column(name = "ciudad_destino")
    String ciudadDestino;

    @NotNull
    @Column(name = "fecha_salida")
    Date fechaSalida;

    @NotNull
    @Column(name = "hora_salida")
    Float horaSalida;

    @NotNull
    Integer numeroPlazas;
}
package com.ruben.backweb.reserva.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @NotBlank
    @Column(name = "ciudad_destino")
    private String ciudadDestino;

    @NotNull
    @NotBlank
    private String nombre;

    @NotNull
    @NotBlank
    private String apellido;

    @NotNull
    @NotBlank
    private String telefono;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Column(name = "fecha_reserva")
    private Date fechaReserva;

    @NotNull
    @Column(name = "hora_reserva")
    private Float horaReserva;

}
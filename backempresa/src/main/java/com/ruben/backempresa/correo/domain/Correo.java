package com.ruben.backempresa.correo.domain;

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
@Table(name = "correo")
public class Correo {
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
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "fecha_reserva")
    private Date fechaReserva;

    @NotNull
    @Column(name = "hora_reserva")
    private Float horaReserva;
}

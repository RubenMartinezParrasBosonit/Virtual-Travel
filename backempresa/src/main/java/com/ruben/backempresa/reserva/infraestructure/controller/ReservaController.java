package com.ruben.backempresa.reserva.infraestructure.controller;

import com.ruben.backempresa.correo.application.CorreoService;
import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoInputDto;
import com.ruben.backempresa.reserva.application.ReservaService;
import com.ruben.backempresa.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @Autowired
    CorreoService correoService;

    @PostMapping("/reserva")
    public ResponseEntity<ReservaOutputDto> reserva(@RequestBody ReservaOutputDto reservaOutputDto) {
        ReservaOutputDto reservaFinal = reservaService.hacerReserva(reservaOutputDto);

        correoService.saveMail(new CorreoInputDto(reservaFinal.getCiudadDestino(), reservaFinal.getEmail()
                , reservaFinal.getFechaReserva(), reservaFinal.getHoraReserva()));
        return ResponseEntity.ok().body(reservaFinal);

    }
}

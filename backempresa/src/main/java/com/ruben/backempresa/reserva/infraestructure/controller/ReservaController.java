package com.ruben.backempresa.reserva.infraestructure.controller;

import com.ruben.backempresa.reserva.application.ReservaService;
import com.ruben.backempresa.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReservaController {

    @Autowired
    ReservaService reservaService;



    @PostMapping("/reserva2")
    public ResponseEntity<ReservaOutputDto> reserva(@RequestBody ReservaOutputDto reservaOutputDto) {
        ReservaOutputDto reservaFinal = reservaService.hacerReserva(reservaOutputDto);


        return ResponseEntity.ok().body(reservaFinal);

    }

    @PostMapping("/reserva")
    public ResponseEntity<ReservaOutputDto> reservaKafka(@RequestBody ReservaOutputDto reservaOutputDto) {
        ReservaOutputDto reservaFinal = reservaService.hacerReservaKafka(reservaOutputDto);

        return ResponseEntity.ok().body(reservaFinal);

    }

    //@GetMapping("/correos")
    //public ResponseEntity<>
}

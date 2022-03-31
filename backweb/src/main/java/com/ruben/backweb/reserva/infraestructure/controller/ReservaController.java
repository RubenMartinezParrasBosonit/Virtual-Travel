package com.ruben.backweb.reserva.infraestructure.controller;

import com.ruben.backweb.reserva.application.ReservaService;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.input.ReservaDisponibleInputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaDisponibleOutputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReservaController {



    @Autowired
    ReservaService reservaService;

    @PostMapping("/reserva2")
    ResponseEntity<ReservaOutputDto> reserva(@RequestBody ReservaOutputDto reservaOutputDto){
        //Comprobamos plazas disponibles en backweb
        reservaService.hacerReserva(reservaOutputDto);
        return ResponseEntity.ok().body(reservaOutputDto);
    }

    @GetMapping("/reserva")
    ResponseEntity<ReservaDisponibleOutputDto> getReserva(@RequestBody ReservaDisponibleInputDto reservaDisponibleInputDto){
        return ResponseEntity.ok().body(reservaService.buscarDisponibilidad(reservaDisponibleInputDto));
    }

    @PostMapping("/reserva")
    ResponseEntity<ReservaOutputDto> reservaKafka(@RequestBody ReservaOutputDto reservaOutputDto){
        return ResponseEntity.ok().body(reservaService.hacerReservaKafka(reservaOutputDto));
    }

}

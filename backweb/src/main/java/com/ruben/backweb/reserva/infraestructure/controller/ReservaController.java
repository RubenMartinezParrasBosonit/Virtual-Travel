package com.ruben.backweb.reserva.infraestructure.controller;

import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import com.ruben.backweb.shared.IFeign;
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
    IFeign iFeing;

    @PostMapping("/reserva")
    ResponseEntity<ReservaOutputDto> reserva(@RequestBody ReservaOutputDto reservaOutputDto){
        return iFeing.callReserva(reservaOutputDto);
    }
}

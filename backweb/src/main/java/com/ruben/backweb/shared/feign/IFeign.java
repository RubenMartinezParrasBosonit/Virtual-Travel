package com.ruben.backweb.shared.feign;

import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="BackEmpresa",url="http://localhost:8081")
public interface IFeign {

    @GetMapping("/api/reserva")
    ResponseEntity<ReservaOutputDto> callReserva(@RequestBody ReservaOutputDto reservaOutputDto);

    @PostMapping("/api/login")
    String login(@RequestBody LoginForm loginForm);
}

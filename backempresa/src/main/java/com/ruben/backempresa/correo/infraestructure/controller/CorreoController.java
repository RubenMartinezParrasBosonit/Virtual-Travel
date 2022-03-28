package com.ruben.backempresa.correo.infraestructure.controller;

import com.ruben.backempresa.correo.application.CorreoService;
import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CorreoController {

    @Autowired
    CorreoService correoService;

    @PostMapping("/mail/send")
    public ResponseEntity<String> sendMail(@RequestBody CorreoInputDto correoInputDto){
        correoService.sendMail(correoInputDto);
        return ResponseEntity.ok().body("Correo enviado");
    }

    @PostMapping("/mail/save")
    public ResponseEntity<CorreoInputDto> saveMail(@RequestBody CorreoInputDto correoInputDto){
        correoService.saveMail(correoInputDto);
        return ResponseEntity.ok().body(correoInputDto);
    }

}

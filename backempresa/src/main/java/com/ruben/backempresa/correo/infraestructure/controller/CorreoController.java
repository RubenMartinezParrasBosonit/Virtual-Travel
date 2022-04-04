package com.ruben.backempresa.correo.infraestructure.controller;

import com.ruben.backempresa.correo.application.CorreoService;
import com.ruben.backempresa.correo.domain.Correo;
import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoInputDto;
import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoRangoInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/correos")
    public ResponseEntity<List<CorreoInputDto>> getCorreos(@RequestBody CorreoRangoInputDto correoRangoInputDto){
        return ResponseEntity.ok().body(correoService.getCorreos(correoRangoInputDto));
    }

}

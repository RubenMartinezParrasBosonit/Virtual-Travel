package com.ruben.backempresa.correo.application;

import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoInputDto;

public interface CorreoService {
    void sendMail(CorreoInputDto correoInputDto);
    void saveMail(CorreoInputDto correoInputDto);
}

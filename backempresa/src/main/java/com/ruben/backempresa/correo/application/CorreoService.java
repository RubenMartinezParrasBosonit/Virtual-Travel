package com.ruben.backempresa.correo.application;

import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoInputDto;
import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoRangoInputDto;

import java.util.List;

public interface CorreoService {
    void sendMail(CorreoInputDto correoInputDto);
    void saveMail(CorreoInputDto correoInputDto);
    List<CorreoInputDto> getCorreos(CorreoRangoInputDto correoRangoInputDto);
}

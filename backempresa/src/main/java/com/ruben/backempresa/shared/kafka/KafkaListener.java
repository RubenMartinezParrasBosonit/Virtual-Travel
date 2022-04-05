package com.ruben.backempresa.shared.kafka;

import com.ruben.backempresa.reserva.application.ReservaService;
import com.ruben.backempresa.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @Autowired
    ReservaService reservaService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "${message.topic.name:actualiza}")
    public void listenTopicActualiza(ReservaOutputDto reservaOutputDto) {
        reservaService.escucharReservaKafka(reservaOutputDto);
    }

}

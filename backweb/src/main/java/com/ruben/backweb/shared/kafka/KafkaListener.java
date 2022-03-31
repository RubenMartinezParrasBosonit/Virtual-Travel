package com.ruben.backweb.shared.kafka;

import com.ruben.backweb.reserva.application.ReservaService;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @Autowired
    ReservaService reservaService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "${message.topic.name:actualiza}"
            , groupId = "${message.group.name:virtualtravel}")
    public void listenTopicActualiza(ReservaOutputDto reservaOutputDto) {
        reservaService.escucharReservaKafka(reservaOutputDto);
    }

}

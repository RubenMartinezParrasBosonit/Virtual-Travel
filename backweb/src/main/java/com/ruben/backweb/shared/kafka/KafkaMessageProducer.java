package com.ruben.backweb.shared.kafka;

import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, ReservaOutputDto reserva) {


        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, reserva);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("Enviando reserva al topic "
                        + topic + " con offset = [" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.err.println("No se ha podido enviar la reserva por : " + ex.getMessage());
            }
        });
    }

}

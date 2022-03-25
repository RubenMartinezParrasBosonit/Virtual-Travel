package com.ruben.backempresa.shared.kafka;

import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(topics = "${message.topic.name:actualiza}"
            , groupId = "${message.group.name:virtualtravel}")
    public void listenTopicActualiza(String message) {
        System.out.println("Listener: Recibido mensaje desde actualiza: " + message);
    }

}

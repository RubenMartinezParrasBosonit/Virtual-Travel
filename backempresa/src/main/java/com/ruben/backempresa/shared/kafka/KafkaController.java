package com.ruben.backempresa.shared.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    KafkaMessageProducer kafkaMessageProducer;

    @PostMapping("/api/test/{topic}")
    public void addIdCustomer(@PathVariable String topic, @RequestBody String body)
    {
        kafkaMessageProducer.sendMessage(topic,body);
    }

}

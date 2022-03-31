package com.ruben.backweb.shared.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ReservaOutputDtoSerializer  implements Serializer<ReservaOutputDto> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, ReservaOutputDto reservaOutputDto) {
        byte[] serializedBytes = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            serializedBytes = objectMapper.writeValueAsString(reservaOutputDto).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serializedBytes;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, ReservaOutputDto data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}

package com.ruben.backweb.shared.kafka.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ReservaOutputDtoDeserializer implements Deserializer<ReservaOutputDto>{
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ReservaOutputDto deserialize(String s, byte[] reservabytes) {
        ObjectMapper mapper = new ObjectMapper();
        ReservaOutputDto reservaOutputDto = null;
        try {
            reservaOutputDto = mapper.readValue(reservabytes, ReservaOutputDto.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return reservaOutputDto;
    }

    @Override
    public ReservaOutputDto deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}

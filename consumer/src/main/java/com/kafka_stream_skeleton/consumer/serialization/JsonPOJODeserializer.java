package com.kafka_stream_skeleton.consumer.serialization;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import com.kafka_stream_skeleton.model.proxy.footprints.FootprintsData;

import java.util.Map;

public class JsonPOJODeserializer implements Deserializer<FootprintsData> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public FootprintsData deserialize(String topic, byte[] data) {
        if (data == null)
            return null;

        FootprintsData footprintsData;
        try {
            footprintsData = objectMapper.readValue(data, FootprintsData.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return footprintsData;
    }


    @Override
    public void close() {
    }

}
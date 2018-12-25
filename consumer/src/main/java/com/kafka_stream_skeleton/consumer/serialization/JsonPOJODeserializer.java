package com.kafka_stream_skeleton.consumer.serialization;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_stream_skeleton.model.proxy.footprints.SubmitFootprintsRequest;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JsonPOJODeserializer implements Deserializer<SubmitFootprintsRequest> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public SubmitFootprintsRequest deserialize(String topic, byte[] data) {
        if (data == null)
            return null;

        SubmitFootprintsRequest request;
        try {
            request = objectMapper.readValue(data, SubmitFootprintsRequest.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return request;
    }


    @Override
    public void close() {
    }

}
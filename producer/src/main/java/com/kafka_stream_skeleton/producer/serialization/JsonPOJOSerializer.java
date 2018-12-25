package com.kafka_stream_skeleton.producer.serialization;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_stream_skeleton.model.proxy.footprints.FootprintsData;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class JsonPOJOSerializer implements Serializer<FootprintsData> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String arg0, FootprintsData arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
        return retVal;
    }

    @Override
    public void close() {
    }

}
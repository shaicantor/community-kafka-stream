package com.kafka_stream_skeleton.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_stream_skeleton.model.proxy.footprints.FootprintsData;

import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {
    public static FootprintsData getFootprints(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = FootprintsData.class.getResourceAsStream("/Footprints.json");
        FootprintsData result = null;
        try {
            result = mapper.readValue(is, FootprintsData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

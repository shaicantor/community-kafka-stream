package com.kafka_stream_skeleton.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_stream_skeleton.producer.proxy.footprints.FootprintsData;

import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {
    public static void printFootprints(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = FootprintsData.class.getResourceAsStream("/Footprints.json");
        FootprintsData result = null;
        try {
            result = mapper.readValue(is, FootprintsData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("############################");
        System.out.println(result);
        System.out.println("############################");

    }
}

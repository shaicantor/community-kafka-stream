package com.kafka_stream_skeleton.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka_stream_skeleton.model.proxy.build.BuildMappingRequest;
import com.kafka_stream_skeleton.model.proxy.footprints.FootprintsData;
import com.kafka_stream_skeleton.model.proxy.footprints.SubmitFootprintsRequest;

import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {
    public static SubmitFootprintsRequest getFootprints(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = FootprintsData.class.getResourceAsStream("/Footprints.json");
        SubmitFootprintsRequest result = null;
        try {
            result = mapper.readValue(is, SubmitFootprintsRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static BuildMappingRequest getBuild(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = BuildMappingRequest.class.getResourceAsStream("/Build.json");
        BuildMappingRequest result = null;
        try {
            result = mapper.readValue(is, BuildMappingRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public static FootprintsData getFootprints(){
//        ObjectMapper mapper = new ObjectMapper();
//        InputStream is = FootprintsData.class.getResourceAsStream("/Footprints.json");
//        FootprintsData result = null;
//        try {
//            result = mapper.readValue(is, FootprintsData.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


}

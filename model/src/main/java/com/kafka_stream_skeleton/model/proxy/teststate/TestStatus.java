package com.kafka_stream_skeleton.model.proxy.teststate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestStatus {

    private String name;
    private int duration;
    private String framework;
    private String result;
    private String status;

    public String getStatus(){
        return status;
    }
}

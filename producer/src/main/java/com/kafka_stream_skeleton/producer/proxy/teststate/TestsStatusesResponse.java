package com.kafka_stream_skeleton.producer.proxy.teststate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestsStatusesResponse {

    private List<TestStatus> tests;

    public List<TestStatus> getTests() {
        return  tests;
    }

}

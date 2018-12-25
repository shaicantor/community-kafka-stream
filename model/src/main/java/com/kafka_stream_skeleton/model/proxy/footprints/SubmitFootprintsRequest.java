package com.kafka_stream_skeleton.model.proxy.footprints;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ronis on 4/4/2017.
 */
@Data
public class SubmitFootprintsRequest {
    private String customerId;
    private Object environment;
    private Object configurationData;
    private List<TestExecData> tests;
    private List<AppData> apps;
    private Map<Long, String> threadIdToTestIdentifier;
    private Map<String, Long> testIdentifierToThreadId;

    public SubmitFootprintsRequest() {
        this.tests = new ArrayList<>();
        this.apps = new ArrayList<>();
        environment = new Object();
    }
}
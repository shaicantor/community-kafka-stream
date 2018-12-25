package com.kafka_stream_skeleton.model.proxy.teststate;

/**
 * Created by nadav on 08/12/2016.
 */
public class TestExecutionRequest {
    private String customerId;
    private String labId;

    public TestExecutionRequest(String customerId, String labId) {
        this.customerId = customerId;
        this.labId = labId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }
}

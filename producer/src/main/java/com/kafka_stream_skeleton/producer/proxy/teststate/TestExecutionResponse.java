package com.kafka_stream_skeleton.producer.proxy.teststate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by nadav on 08/12/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestExecutionResponse {
    private String executionId;
    private String newEnvironment;
    private String status;


    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getNewEnvironment() {
        return newEnvironment;
    }

    public void setNewEnvironment(String newEnvironment) {
        this.newEnvironment = newEnvironment;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TestExecutionResponse{" +
                "executionId='" + executionId + '\'' +
                ", newEnvironment='" + newEnvironment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestStage(){
        return this.getNewEnvironment();
    }
}

package com.kafka_stream_skeleton.model.proxy.build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvironmentData {

    public String getAgentType() {
        return "JavaBuildScanner";
    }
    
}

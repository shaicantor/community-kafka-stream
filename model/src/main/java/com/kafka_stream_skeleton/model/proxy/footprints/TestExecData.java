package com.kafka_stream_skeleton.producer.proxy.footprints;

import lombok.Data;

/**
 * Created by Ronis on 4/4/2017.
 */
@Data
public class TestExecData {
    private String testName;
    private String executionId;
    private long localTime;
    private String testId;
}
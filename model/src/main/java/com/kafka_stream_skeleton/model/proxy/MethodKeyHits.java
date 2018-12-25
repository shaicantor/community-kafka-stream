package com.kafka_stream_skeleton.model.proxy;

import lombok.Data;

/**
 * Created by shai on 12/25/18.
 */
@Data
public class MethodKeyHits {
    private String methodKey;
    private Long hits;

    public MethodKeyHits(String methodKey, Long hits) {
        this.methodKey = methodKey;
        this.hits = hits;
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public String getMethodKey() {
        return methodKey;
    }

    public void setMethodKey(String methodKey) {
        this.methodKey = methodKey;
    }
}

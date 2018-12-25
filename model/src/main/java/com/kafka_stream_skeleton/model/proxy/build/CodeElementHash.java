package com.kafka_stream_skeleton.model.proxy.build;

import lombok.Data;

/**
 * Created by Ronis on 4/6/2017.
 */
@Data
public class CodeElementHash {
    private String hash;

    public CodeElementHash() {
    	this(null);
    }
    
    public CodeElementHash(String hash) {
        this.hash = hash;
    }

}

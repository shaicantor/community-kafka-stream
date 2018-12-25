package com.kafka_stream_skeleton.model.proxy.build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Ronis on 4/2/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper=true)
public class MethodData extends CodeElementHash {
    private String uniqueId;
    private String displayName;
    private Integer[] position;
    private Integer[] endPosition;
    private MethodMetaData meta;
    private String sigHash;
    private String elementId;

    public MethodData(){};
    public MethodData(String uniqueId, String displayName, Integer[] position, Integer[]endPosition, MethodMetaData metaData, String hash, String elementId){
        super(hash);
        this.uniqueId =uniqueId;
        this.displayName = displayName;
        this.position = position;
        this.endPosition = endPosition;
        this.meta= metaData;
        this.elementId = elementId;

    }
}

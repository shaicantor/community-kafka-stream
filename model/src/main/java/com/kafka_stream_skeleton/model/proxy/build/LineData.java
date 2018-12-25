package com.kafka_stream_skeleton.model.proxy.build;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Ronis on 4/6/2017.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class LineData extends CodeElementHash {
    private String uniqueId;
    private int name;
}

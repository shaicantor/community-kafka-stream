package com.kafka_stream_skeleton.model.proxy.footprints;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronis on 4/4/2017.
 */
@Data
public class FootprintsData {
    private String uniqueId;
    List<Integer[]> hits = new ArrayList<>();
}
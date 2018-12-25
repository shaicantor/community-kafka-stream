package com.kafka_stream_skeleton.model.proxy.footprints;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronis on 4/4/2017.
 */
@Data
public class FileData {
    private String path;
    private List<FootprintsData> methods = new ArrayList<>();
    private List<FootprintsData> lines = new ArrayList<>();
    private List<FootprintsData> branches;
}
package com.kafka_stream_skeleton.model.proxy.footprints;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronis on 4/4/2017.
 */
@Data
public class AppData {
    private String appName;
    private String branchName;
    private String buildName;
    private String moduleName;
    private List<FileData> files = new ArrayList<>();
}
package com.kafka_stream_skeleton.model.proxy.build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by Ronis on 4/6/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BuildMappingRequest {
    private Map<String, Object> meta;
    private ConfigurationInfo configurationInfo;
    private List<? extends FileData> files;
    private List<DependencyData> dependencies;

    public BuildMappingRequest(){}
//    public BuildMappingRequest(Map<String, Object> meta, ConfigurationInfo configurationInfo, List<? extends FileData> files, List<DependencyData> dependencies) {
//        this.meta = meta;
//        this.configurationInfo = configurationInfo;
//        this.files = files;
//        this.dependencies = dependencies;
//    }

    public int getNumOfReportedMethods() {
        int counter = 0;
        for (FileData file : getFiles()) {
            counter += file.getMethods().size();
        }
        return counter;
    }

    public int getNumOfReportedFiles() {
        return getFiles().size();
    }
}

package com.kafka_stream_skeleton.model.proxy.build;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ronis on 4/6/2017.
 */
@Data
public class FileData {

    private String logicalPath;
    private String physicalPath;
    private List<MethodData> methods;
    private List<LineData> lines;
    private String hash;
    private List<Integer> commitIndexes = new ArrayList<>();

    public FileData(){}
    public FileData(String className, String physicalPath, List<MethodData> methods, List<LineData> lines, String hash) {
        this.logicalPath = className;
        this.physicalPath =physicalPath;
        this.methods = methods;
        this.lines = lines;
        this.hash = hash;
    }

    public void initCommitIndexes(Map<String, List<Integer>> commitsPerFile) {
        if (commitsPerFile != null) {
            //If we have commitsPerFile, use it.
            commitIndexes = commitsPerFile.get(getPhysicalPath());
        }

        if (commitIndexes == null) {
            //We didn't find commitIndexes so we assume the file didn't have commits.
            commitIndexes = new ArrayList<>();
        }
    }
    
}



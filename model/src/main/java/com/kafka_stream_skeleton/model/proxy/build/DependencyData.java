package com.kafka_stream_skeleton.model.proxy.build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/*
 * This class represent dependency in the dependencies data, that
 * should be sent to the cloud as part of the mapping request.
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DependencyData {

	public DependencyData() {
		this(null,null,null);
	}

	public DependencyData(String appName, String branch, String build) {
		this.appName = appName;
		this.branch = branch;
		this.build = build;
	}

	private final static String SEPARATOR = "@";

	private String appName;
	private String branch;
	private String build;

	public static DependencyData parse(String dependency) {
		//Expected format: AppName@Branch@Build
		if (dependency == null)
			throw new RuntimeException("Dependency couldn't be null");

		String[] tokens = dependency.split(SEPARATOR);

		if (tokens == null || tokens.length != 3){
			throw new RuntimeException("Invalid dependency value: '" +dependency+ "'. Please see the 'help' screen for proper use.");
		}

		String appname = tokens[0];
		String branch = tokens[1];
		String build = tokens[2];
		DependencyData data = new DependencyData(appname, branch, build);



		return data;
	}

	public String toString() {
		return appName + SEPARATOR + 
				branch + SEPARATOR + 
				build;
	}
}

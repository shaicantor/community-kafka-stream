package com.kafka_stream_skeleton.model.proxy.build;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * This class holds the configuration that are saved/loaded to/load the file.
 * 
 * */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationInfo {

	private String customerId;
	private String token;
	private String server;
	private String proxy;
	private boolean compressRequest;
	private int commitHistoryLength;
	private boolean isLineCoverageEnabled;
	private boolean isReportOnConstructors = true;
	private boolean isReportOnGettersAndSetters = true;
	private boolean ignoreMethodsWithoutLineNumbers;
	private String scmProvider;
	private String scmVersion;
	private String scmBaseUrl;
	private String scmType;

	public ConfigurationInfo(String customerId, String server, String proxy, String token, int commitHistoryLength) {
		super();
		this.customerId = customerId;
		this.token =token;
		this.server = server;
		this.proxy = proxy;
		this.compressRequest = true;
		this.commitHistoryLength = commitHistoryLength;
	}

	public ConfigurationInfo() {
		this(null,null,null, null, 0);
	}



}

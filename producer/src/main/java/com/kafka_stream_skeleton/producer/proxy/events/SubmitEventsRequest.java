package com.kafka_stream_skeleton.producer.proxy.events;


import java.util.ArrayList;
import java.util.List;

public class SubmitEventsRequest {
	private String customerId;
	private String appName;
	private String moduleName;
	private String uniqueModuleId;
	private String build;
	private String branch;
	private List<Object> events = null;
	private Object environment = null;
	private Object configurationData;
	private long creationTime = System.currentTimeMillis();

	public SubmitEventsRequest() {
		events = new ArrayList<Object>();
		environment = new Object();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Object getEnvironment() {
		return environment;
	}

	public void setEnvironment(Object environment) {
		this.environment = environment;
	}


	public List<Object> getEvents() {
		return events;
	}

	public void setEvents(List<Object> events) {
		this.events = events;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}


	public Object getConfigurationData() {
		return configurationData;
	}

	public void setConfigurationData(Object configurationData) {
		this.configurationData = configurationData;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public String getUniqueModuleId() {
		return uniqueModuleId;
	}

	public void setUniqueModuleId(String uniqueModuleId) {
		this.uniqueModuleId = uniqueModuleId;
	}
}

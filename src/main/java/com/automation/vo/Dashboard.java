package com.automation.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Dashboard {
	
	private List<String> hierarchy;
	private List<String> locations;
	private Metrics currentMonth;
	private Metrics pastMonth;
	
	public List<String> getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(List<String> hierarchy) {
		this.hierarchy = hierarchy;
	}

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public Metrics getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(Metrics currentMonth) {
		this.currentMonth = currentMonth;
	}

	public Metrics getPastMonth() {
		return pastMonth;
	}

	public void setPastMonth(Metrics pastMonth) {
		this.pastMonth = pastMonth;
	}
}

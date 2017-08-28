package com.automation.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Metrics {
	
	private String productivity;
	private String prodPercent;
	private ActivityCodes shrinkage;
	private String headCount;
	
	public String getProductivity() {
		return productivity;
	}
	public void setProductivity(String productivity) {
		this.productivity = productivity;
	}
	public ActivityCodes getShrinkage() {
		return shrinkage;
	}
	public void setShrinkage(ActivityCodes shrinkage) {
		this.shrinkage = shrinkage;
	}
	public String getHeadCount() {
		return headCount;
	}
	public void setHeadCount(String headCount) {
		this.headCount = headCount;
	}
	public String getProdPercent() {
		return prodPercent;
	}
	public void setProdPercent(String prodPercent) {
		this.prodPercent = prodPercent;
	}

}

package com.automation.vo;

public class Agent {
	
	private String name;
	private String emailId;
	private String idleFrom;
	private String idleTo;
	private String websitesVisited;
	private int projectId;
	private String shiftTimings;
	private String loginTime;
	private String logoutTime;
	private String productiveHours;
	private String idleHours;
	private String ErrorDesc;
	
	
	public String getErrorDesc() {
		return ErrorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		ErrorDesc = errorDesc;
	}
	private String DATE;
	
	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		DATE = dATE;
	}

	@Override
	public String toString() {
		return "Agent [name=" + name + ", emailId=" + emailId + "]";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getIdleFrom() {
		return idleFrom;
	}
	public void setIdleFrom(String idleFrom) {
		this.idleFrom = idleFrom;
	}
	public String getIdleTo() {
		return idleTo;
	}
	public void setIdleTo(String idleTo) {
		this.idleTo = idleTo;
	}
	public String getWebsitesVisited() {
		return websitesVisited;
	}
	public void setWebsitesVisited(String websitesVisited) {
		this.websitesVisited = websitesVisited;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getShiftTimings() {
		return shiftTimings;
	}
	public void setShiftTimings(String shiftTimings) {
		this.shiftTimings = shiftTimings;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}
	public String getProductiveHours() {
		return productiveHours;
	}
	public void setProductiveHours(String productiveHours) {
		this.productiveHours = productiveHours;
	}
	public String getIdleHours() {
		return idleHours;
	}
	public void setIdleHours(String idleHours) {
		this.idleHours = idleHours;
	}

}

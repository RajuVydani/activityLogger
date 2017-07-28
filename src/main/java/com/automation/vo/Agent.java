package com.automation.vo;

public class Agent {
	private String servicename;
	private String name;
	private String emailId;
	private String idleFrom;
	private String idleTo;
	private String websitesVisited;
	private String projectId;
	private String fromDate;
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	private String toDate;
 
	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	private String shiftTimings;
	private String loginTime;
	private String logoutTime;
	private String productiveHours;
	private String idleHours;
	private String ErrorDesc;
	private String recordedTime;
	private String idleTimings;
	private int idleInterval;
	public String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

 

	public String hcmSupervisor;
 

	public String getHcmSupervisor() {
		return hcmSupervisor;
	}

	public void setHcmSupervisor(String hcmSupervisor) {
		this.hcmSupervisor = hcmSupervisor;
	}

	public int getIdleInterval() {
		return idleInterval;
	}

	public void setIdleInterval(int idleInterval) {
		this.idleInterval = idleInterval;
	}

	public String getIdleTimings() {
		return idleTimings;
	}

	public void setIdleTimings(String idleTimings) {
		this.idleTimings = idleTimings;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getRecordedTime() {
		return recordedTime;
	}

	public void setRecordedTime(String recordedTime) {
		this.recordedTime = recordedTime;
	}

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

package com.automation.vo;

public class Agent {
	/**
	 * 
	 */
	private String servicename;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String emailId;
	/**
	 * 
	 */
	private String idleFrom;
	/**
	 * 
	 */
	private String idleTo;
	/**
	 * 
	 */
	private String websitesVisited;
	/**
	 * 
	 */
	private String projectId;
	/**
	 * 
	 */
	private String fromDate;
	/**
	 * 
	 */
	private String activityCode;
	/**
	 * 
	 */
	private String activityHrs;

	/**
	 * @return
	 */
	public String getActivityHrs() {
		return activityHrs;
	}

	/**
	 * @param activityHrs
	 */
	public void setActivityHrs(String activityHrs) {
		this.activityHrs = activityHrs;
	}

	/**
	 * @return
	 */
	public String getActivityCode() {
		return activityCode;
	}

	/**
	 * @param activityCode
	 */
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	/**
	 * @return
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * 
	 */
	private String toDate;

	/**
	 * @return
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * 
	 */
	private String shiftTimings;
	/**
	 * 
	 */
	private String loginTime;
	/**
	 * 
	 */
	private String logoutTime;
	/**
	 * 
	 */
	private String productiveHours;
	/**
	 * 
	 */
	private String idleHours;
	/**
	 * 
	 */
	private String ErrorDesc;
	/**
	 * 
	 */
	private String recordedTime;
	/**
	 * 
	 */
	private String idleTimings;
	/**
	 * 
	 */
	private int idleInterval;
	/**
	 * 
	 */
	public String location;

	/**
	 * @return
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 
	 */
	public String hcmSupervisorId;
	public String hcmSupervisorName;

 

	public String getHcmSupervisorId() {
		return hcmSupervisorId;
	}

	public void setHcmSupervisorId(String hcmSupervisorId) {
		this.hcmSupervisorId = hcmSupervisorId;
	}

	public String getHcmSupervisorName() {
		return hcmSupervisorName;
	}

	public void setHcmSupervisorName(String hcmSupervisorName) {
		this.hcmSupervisorName = hcmSupervisorName;
	}

	/**
	 * @return
	 */
	public int getIdleInterval() {
		return idleInterval;
	}

	/**
	 * @param idleInterval
	 */
	public void setIdleInterval(int idleInterval) {
		this.idleInterval = idleInterval;
	}

	/**
	 * @return
	 */
	public String getIdleTimings() {
		return idleTimings;
	}

	/**
	 * @param idleTimings
	 */
	public void setIdleTimings(String idleTimings) {
		this.idleTimings = idleTimings;
	}

	/**
	 * @return
	 */
	public String getServicename() {
		return servicename;
	}

	/**
	 * @param servicename
	 */
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	/**
	 * @return
	 */
	public String getRecordedTime() {
		return recordedTime;
	}

	/**
	 * @param recordedTime
	 */
	public void setRecordedTime(String recordedTime) {
		this.recordedTime = recordedTime;
	}

	/**
	 * @return
	 */
	public String getErrorDesc() {
		return ErrorDesc;
	}

	/**
	 * @param errorDesc
	 */
	public void setErrorDesc(String errorDesc) {
		ErrorDesc = errorDesc;
	}

	/**
	 * 
	 */
	private String DATE;

	/**
	 * @return
	 */
	public String getDATE() {
		return DATE;
	}

	/**
	 * @param dATE
	 */
	public void setDATE(String dATE) {
		DATE = dATE;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Agent [name=" + name + ", emailId=" + emailId + "]";
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return
	 */
	public String getIdleFrom() {
		return idleFrom;
	}

	/**
	 * @param idleFrom
	 */
	public void setIdleFrom(String idleFrom) {
		this.idleFrom = idleFrom;
	}

	/**
	 * @return
	 */
	public String getIdleTo() {
		return idleTo;
	}

	/**
	 * @param idleTo
	 */
	public void setIdleTo(String idleTo) {
		this.idleTo = idleTo;
	}

	/**
	 * @return
	 */
	public String getWebsitesVisited() {
		return websitesVisited;
	}

	/**
	 * @param websitesVisited
	 */
	public void setWebsitesVisited(String websitesVisited) {
		this.websitesVisited = websitesVisited;
	}

	/**
	 * @return
	 */
	public String getShiftTimings() {
		return shiftTimings;
	}

	/**
	 * @param shiftTimings
	 */
	public void setShiftTimings(String shiftTimings) {
		this.shiftTimings = shiftTimings;
	}

	/**
	 * @return
	 */
	public String getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return
	 */
	public String getLogoutTime() {
		return logoutTime;
	}

	/**
	 * @param logoutTime
	 */
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	/**
	 * @return
	 */
	public String getProductiveHours() {
		return productiveHours;
	}

	/**
	 * @param productiveHours
	 */
	public void setProductiveHours(String productiveHours) {
		this.productiveHours = productiveHours;
	}

	/**
	 * @return
	 */
	public String getIdleHours() {
		return idleHours;
	}

	/**
	 * @param idleHours
	 */
	public void setIdleHours(String idleHours) {
		this.idleHours = idleHours;
	}
	private String billable;
	public String getBillable() {
		return billable;
	}

	public void setBillable(String billable) {
		this.billable = billable;
	}

	public String getOnshoreOffshore() {
		return onshoreOffshore;
	}

	public void setOnshoreOffshore(String onshoreOffshore) {
		this.onshoreOffshore = onshoreOffshore;
	}

	private String onshoreOffshore;
	private int rownum;

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	
	private String agentId;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
}

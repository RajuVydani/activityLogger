package com.automation.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonWriteNullProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

//@JsonInclude(Include.NON_NULL)
//@JsonWriteNullProperties(false)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
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
	private String activityTimings;

	public String getActivityTimings() {
		return activityTimings;
	}

	public void setActivityTimings(String activityTimings) {
		this.activityTimings = activityTimings;
	}

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

	/*
	 * (non-Javadoc)
	 * 
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
	private String month;
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	private String year;
	private String agentId;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	private String startTime;

	public String getStartTimeRange() {
		return startTime;
	}

	private String EndTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	private String subProjectId;
	private String subProjectName;
	private String ProjectName;

	public String getSubProjectName() {
		return subProjectName;
	}

	public void setSubProjectName(String subProjectName) {
		this.subProjectName = subProjectName;
	}

	public String getProjectName() {
		return ProjectName;
	}

	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}

	public String getSubProjectId() {
		return subProjectId;
	}

	public void setSubProjectId(String subProjectId) {
		this.subProjectId = subProjectId;
	}

	private String shiftFrom;

	public String getShiftFrom() {
		return shiftFrom;
	}

	public void setShiftFrom(String shiftFrom) {
		this.shiftFrom = shiftFrom;
	}

	public String getShiftTo() {
		return shiftTo;
	}

	public void setShiftTo(String shiftTo) {
		this.shiftTo = shiftTo;
	}

	private String shiftTo;
	private String ShiftTimings;

	public String getShiftTimings() {
		return ShiftTimings;
	}

	public void setShiftTimings(String shiftTimings) {
		ShiftTimings = shiftTimings;
	}

	private String prodSum;
	public String getProdSum() {
		return prodSum;
	}

	public void setProdSum(String prodSum) {
		this.prodSum = prodSum;
	}

	public String getIdleSum() {
		return idleSum;
	}

	public void setIdleSum(String idleSum) {
		this.idleSum = idleSum;
	}

	public String getProdAvg() {
		return prodAvg;
	}

	public void setProdAvg(String prodAvg) {
		this.prodAvg = prodAvg;
	}

	public String getIdleAvg() {
		return idleAvg;
	}

	public void setIdleAvg(String idleAvg) {
		this.idleAvg = idleAvg;
	}

	private String idleSum;
	private String prodAvg;
	private String idleAvg;
	
	private String breakSum;
	private String mealsSum;
	private String welnessSupportSum;
	public String getBreakSum() {
		return breakSum;
	}

	public void setBreakSum(String breakSum) {
		this.breakSum = breakSum;
	}

	public String getMealsSum() {
		return mealsSum;
	}

	public void setMealsSum(String mealsSum) {
		this.mealsSum = mealsSum;
	}

	public String getWelnessSupportSum() {
		return welnessSupportSum;
	}

	public void setWelnessSupportSum(String welnessSupportSum) {
		this.welnessSupportSum = welnessSupportSum;
	}

	public String getCoachingSum() {
		return coachingSum;
	}

	public void setCoachingSum(String coachingSum) {
		this.coachingSum = coachingSum;
	}

	public String getTeamMeetingSum() {
		return teamMeetingSum;
	}

	public void setTeamMeetingSum(String teamMeetingSum) {
		this.teamMeetingSum = teamMeetingSum;
	}

	public String getFbTrainingSum() {
		return fbTrainingSum;
	}

	public void setFbTrainingSum(String fbTrainingSum) {
		this.fbTrainingSum = fbTrainingSum;
	}

	public String getNonFbTrainingSum() {
		return nonFbTrainingSum;
	}

	public void setNonFbTrainingSum(String nonFbTrainingSum) {
		this.nonFbTrainingSum = nonFbTrainingSum;
	}
	private String huddleSum;
	public String getHuddleSum() {
		return huddleSum;
	}

	public void setHuddleSum(String huddleSum) {
		this.huddleSum = huddleSum;
	}

	private String coachingSum;
	private String teamMeetingSum;
	private String fbTrainingSum;
	private String nonFbTrainingSum;
}

package com.automation.model;

public class Admin {
 
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String username;
	private String password;
	private String priviledgeId;
	private String userType;
	private String projectId;
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	private String locationId;
	private String locationName;
	public String getPriviledgeId() {
		return priviledgeId;
	}
	public void setPriviledgeId(String priviledgeId) {
		this.priviledgeId = priviledgeId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgramId() {
		return ProgramId;
	}
	public void setProgramId(String programId) {
		ProgramId = programId;
	}
	public String getSubProjectId() {
		return subProjectId;
	}
	public void setSubProjectId(String subProjectId) {
		this.subProjectId = subProjectId;
	}
	public String getLastAccessedTime() {
		return lastAccessedTime;
	}
	public void setLastAccessedTime(String lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramManagerName() {
		return programManagerName;
	}
	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}
	public String getProgramManagerId() {
		return programManagerId;
	}
	public void setProgramManagerId(String programManagerId) {
		this.programManagerId = programManagerId;
	}
	public String getProgramDesc() {
		return programDesc;
	}
	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectManagerId() {
		return projectManagerId;
	}
	public void setProjectManagerId(String projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
 
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
 
	private String status;
	private String ProgramId;
	private String subProjectId;
	private String subProjectName;
	private String subProjectDesc;
	public String getSubProjectDesc() {
		return subProjectDesc;
	}
	public void setSubProjectDesc(String subProjectDesc) {
		this.subProjectDesc = subProjectDesc;
	}
	public String getSubProjectName() {
		return subProjectName;
	}
	public void setSubProjectName(String subProjectName) {
		this.subProjectName = subProjectName;
	}
	private String accessLevel;
	 
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	private String lastAccessedTime;
    private String programName;
	private String programManagerName;
	private String programManagerId;
	private String programDesc;
	private String projectName;
	private String projectManagerId;
	private String projectManagerName;
	public String getProjectManagerName() {
		return projectManagerName;
	}
	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}
	private String projectDesc;
	public String getProjectDesc() {
		return projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	private String supervisorName;
	private String supervisorId;
	
 

	public String getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}
	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}	
}

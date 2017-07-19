package com.automation.vo;

public class Policy {
	private String servicename;

	private String emailId;

	private String policyTagging;
	private String policyUpdatedOn;
	private String policyFlag;
	private String policyContent;

	public String getPolicyContent() {
		return policyContent;
	}

	public void setPolicyContent(String policyContent) {
		this.policyContent = policyContent;
	}

	public String getPolicyTagging() {
		return policyTagging;
	}

	public void setPolicyTagging(String policyTagging) {
		this.policyTagging = policyTagging;
	}

	public String getPolicyUpdatedOn() {
		return policyUpdatedOn;
	}

	public void setPolicyUpdatedOn(String policyUpdatedOn) {
		this.policyUpdatedOn = policyUpdatedOn;
	}

	public String getPolicyFlag() {
		return policyFlag;
	}

	public void setPolicyFlag(String policyFlag) {
		this.policyFlag = policyFlag;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}

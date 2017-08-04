package com.automation.vo;

public class Policy {
	private String servicename;

	private String emailId;

	private String policyTagging;
	private String policyUpdatedOn;
	private String policyFlag;
	private String policyContent;

	/**
	 * @return
	 */
	public String getPolicyContent() {
		return policyContent;
	}

	/**
	 * @param policyContent
	 */
	public void setPolicyContent(String policyContent) {
		this.policyContent = policyContent;
	}

	/**
	 * @return
	 */
	public String getPolicyTagging() {
		return policyTagging;
	}

	/**
	 * @param policyTagging
	 */
	public void setPolicyTagging(String policyTagging) {
		this.policyTagging = policyTagging;
	}

	/**
	 * @return
	 */
	public String getPolicyUpdatedOn() {
		return policyUpdatedOn;
	}

	/**
	 * @param policyUpdatedOn
	 */
	public void setPolicyUpdatedOn(String policyUpdatedOn) {
		this.policyUpdatedOn = policyUpdatedOn;
	}

	/**
	 * @return
	 */
	public String getPolicyFlag() {
		return policyFlag;
	}

	/**
	 * @param policyFlag
	 */
	public void setPolicyFlag(String policyFlag) {
		this.policyFlag = policyFlag;
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
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}

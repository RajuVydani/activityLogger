package com.automation.model;

public class Policy {
	private String policyUpdatedOn;
	private String PolicyTagging;
	private String policyContent;
	private String policyUpdateStatus;
	/**
	 * @return
	 */
	public String getPolicyUpdateStatus() {
		return policyUpdateStatus;
	}
	/**
	 * @param policyUpdateStatus
	 */
	public void setPolicyUpdateStatus(String policyUpdateStatus) {
		this.policyUpdateStatus = policyUpdateStatus;
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
	public String getPolicyTagging() {
		return PolicyTagging;
	}
	/**
	 * @param policyTagging
	 */
	public void setPolicyTagging(String policyTagging) {
		PolicyTagging = policyTagging;
	}
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
 
}

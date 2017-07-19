package com.automation.model;

public class Policy {
	private String policyUpdatedOn;
	private String PolicyTagging;
	private String policyContent;
	private String policyUpdateStatus;
	public String getPolicyUpdateStatus() {
		return policyUpdateStatus;
	}
	public void setPolicyUpdateStatus(String policyUpdateStatus) {
		this.policyUpdateStatus = policyUpdateStatus;
	}
	public String getPolicyUpdatedOn() {
		return policyUpdatedOn;
	}
	public void setPolicyUpdatedOn(String policyUpdatedOn) {
		this.policyUpdatedOn = policyUpdatedOn;
	}
	public String getPolicyTagging() {
		return PolicyTagging;
	}
	public void setPolicyTagging(String policyTagging) {
		PolicyTagging = policyTagging;
	}
	public String getPolicyContent() {
		return policyContent;
	}
	public void setPolicyContent(String policyContent) {
		this.policyContent = policyContent;
	}
 
}

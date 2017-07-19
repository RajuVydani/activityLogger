package com.automation.idao;

import java.util.List;

import com.automation.vo.Agent;
import com.automation.vo.Policy;

public interface IPolicyDAO {

	String getAgentData(Policy policy) throws Exception;

	int updatePolicyFlag(Policy e);

	List<Policy> readPolicyFlag(String emailId);

	List<Policy> readPolicyDetails();

	int newPolicy(com.automation.model.Policy e);
	
	int updatePolicyFlagInAgentMaster();

}

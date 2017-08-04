package com.automation.idao;

import java.util.List;

import com.automation.vo.Agent;
import com.automation.vo.Policy;

public interface IPolicyDAO {

	/**
	 * @param policy
	 * @return
	 * @throws Exception
	 */
	String getAgentData(Policy policy) throws Exception;

	/**
	 * @param e
	 * @return
	 */
	int updatePolicyFlag(Policy e);

	/**
	 * @param emailId
	 * @return
	 */
	List<Policy> readPolicyFlag(String emailId);

	/**
	 * @return
	 */
	List<Policy> readPolicyDetails();

	/**
	 * @param e
	 * @return
	 */
	int newPolicy(com.automation.model.Policy e);
	
	/**
	 * @return
	 */
	int updatePolicyFlagInAgentMaster();

}

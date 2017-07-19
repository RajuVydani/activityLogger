package com.automation.idao;

import java.util.List;

import com.automation.vo.Agent;

public interface IAgentDAO {

	String getAgentData(Agent agent) throws Exception;

	List<Agent> CheckInChromeMaster(String emailId, String loginTime);

	List<Agent> CheckLoginEntry(Agent e);

	int dataUpdateInChromeMater(Agent e);

	int dataInsertionInChromeMater(Agent e);

	int dataInsertionInChromeDetails(Agent e);

	int idleInterval();

	List<Agent> readChromTempMasterTable();

	List<Agent> readChromTempTable();

	List<Agent> readChromExceptionTable();

	List<Agent> readAgentDetailsFromAgentMaster(String emailId);

	int dataInsertionInDayMaster(Agent e);

	int dataInsertionInDayDetail(Agent e);

	int totalAgentCountInDayMaster(String emailId, String Date);

	int deleteFromChromeTempDetail(Agent e);

	int deleteFromChromeTempMaster(Agent e);

	int dataInsertionInException(Agent e);

	int deleteFromChromeException(Agent e);

	List<Agent> CalculateIdleHrs(Agent e);

	int updateIdleHrsInDayMaster(Agent e);

	int totalAgentCountInChromeMater(String emailId, String logindate);

}

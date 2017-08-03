package com.automation.idao;

import java.util.List;

import com.automation.vo.Agent;

public interface IAgentDAO {

	String getAgentData(Agent agent) throws Exception;

	int dataInsertionInChromeDetails(Agent e);

	int idleInterval();

	List<Agent> readChromeTempAgentTransactions(String emailid);

	List<Agent> readChromeTempAgentIds();

	List<Agent> readAgentDetailsFromAgentMaster(String emailId);

	List<Agent> calculateTempActiviyHrs(Agent e);

	List<Agent> calculateExceptionActiviyHrs(Agent e);

	int dataInsertionInDayMaster(Agent e);

	int dataInsertionInDayDetailFromTempDetails(Agent e);

	int dataInsertionInDayDetailFromExceptionDetails(Agent e);

	int dataInsertionInException(Agent e);

	int deleteFromChromeException(Agent e);

	int deleteFromChromeTempDetail(Agent e);

	List<Agent> FetchAgentsInfoDayWise(String managerName, String fromDate, String toDate);

	List<Agent> FetchAgentsInfoOverall(String managerName, String fromDate, String toDate);

	List<Agent> FetchAgentsTransacation(String email_id, String loginTime, String logOutTime);

	List<Agent> FetchAgentsLoginLogoutTime(String emailid, String date);

	List<Agent> FetchAgentsProjectId(String managerName);

	List<Agent> FetchAgentsLocation(String managerName);

	List<Agent> FetchAgentsShiftTimings(String managerName);

	List<Agent> FetchAgentsInfoFilterSpecific(String managerName, String fromDate, String toDate, String projectId,
			String Location, String ShiftTimings);

	List<Agent> readChromeExceptionAgentTransactions(String emailid);

	List<Agent> readChromeExceptionAgentIds();

}

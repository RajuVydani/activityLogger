package com.automation.idao;

import java.util.List;

import com.automation.vo.Agent;

public interface IAgentDAO {

	/**
	 * @param agent
	 * @return
	 * @throws Exception
	 */
	String getAgentData(Agent agent) throws Exception;

	/**
	 * @param e
	 * @return
	 */
	int dataInsertionInChromeDetails(Agent e);

	/**
	 * @return
	 */
	int idleInterval();

	/**
	 * @param emailid
	 * @return
	 */
	/**
	 * @param emailid
	 * @return
	 */
	List<Agent> readChromeTempAgentTransactions(String emailid);

	/**
	 * @return
	 */
	List<Agent> readChromeTempAgentIds();

	/**
	 * @param emailId
	 * @return
	 */
	List<Agent> readAgentDetailsFromAgentMaster(String emailId);

	/**
	 * @param e
	 * @return
	 */
	List<Agent> calculateTempActiviyHrs(Agent e);

	/**
	 * @param e
	 * @return
	 */
	List<Agent> calculateExceptionActiviyHrs(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int dataInsertionInDayMaster(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int dataInsertionInDayDetailFromTempDetails(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int dataInsertionInDayDetailFromExceptionDetails(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int dataInsertionInException(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int deleteFromChromeException(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int deleteFromChromeTempDetail(Agent e);

	/**
	 * @param managerName
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	List<Agent> fetchAgentsInfoDayWise(String managerName, String fromDate, String toDate);

	/**
	 * @param managerName
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	List<Agent> fetchAgentsInfoOverall(String managerName, String fromDate, String toDate);

	/**
	 * @param email_id
	 * @param loginTime
	 * @param logOutTime
	 * @return
	 */
	List<Agent> fetchAgentsTransacation(String email_id, String loginTime, String logOutTime);

	/**
	 * @param emailid
	 * @param date
	 * @return
	 */
	List<Agent> fetchAgentsLoginLogoutTime(String emailid, String date);

	/**
	 * @param managerName
	 * @return
	 */
	List<Agent> fetchAgentsProjectId(String managerName);

	/**
	 * @param managerName
	 * @return
	 */
	List<Agent> fetchAgentsLocation(String managerName);

	/**
	 * @param managerName
	 * @return
	 */
	List<Agent> fetchAgentsShiftTimings(String managerName);

	/**
	 * @param managerName
	 * @param fromDate
	 * @param toDate
	 * @param projectId
	 * @param Location
	 * @param ShiftTimings
	 * @return
	 */
	List<Agent> fetchAgentsInfoFilterSpecific(Agent e);

	/**
	 * @param emailid
	 * @return
	 */
	List<Agent> readChromeExceptionAgentTransactions(String emailid);

	/**
	 * @return
	 */
	List<Agent> readChromeExceptionAgentIds();

}

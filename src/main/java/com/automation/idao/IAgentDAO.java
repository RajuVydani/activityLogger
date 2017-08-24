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
	public List<Agent> idleInterval(Agent agent);

	/**
	 * @param emailid
	 * @return
	 */
	/**
	 * @param emailid
	 * @return
	 */
	List<Agent> readChromeTempAgentTransactions();

	/**
	 * @return
	 */
 
	List<Agent> readAgentDetailsFromAgentMaster(String emailId);

	/**
	 * @param e
	 * @return
	 */
	List<Agent> calculateActiviyHrs(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int dataInsertionInDayMaster(Agent e);

	/**
	 * @param e
	 * @return
	 */
	public int dataInsertionInDayDetail(Agent agent);

	/**
	 * @param agent
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
	/**
	 * @return
	 */
	List<Agent> readChromeExceptionAgentIds();

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> fetchdataFromChromeTempDetails(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> fetchShiftDetails(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public int checkEntryExsistInDayMaster(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> getPreviousDayDetailsFromDayMaster(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public int getDayDetailCount(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> geLoginTimeFromDayMaster(Agent agent);

	/**
	 * @param e
	 * @return
	 */
	/**
	 * @param e
	 * @return
	 */
	public int dataUpdationInDayMaster(Agent e);
	
	/**
	 * @param agent
	 * @return
	 */
	public int checkDayDetailLastActivity(Agent agent);
	
	/**
	 * @param agent
	 * @return
	 */
	public int dataUpdationInDayDetails(Agent agent);
	/**
	 * @param agent
	 * @return
	 */
	public int checkEntryExsistInMonthMaster(Agent agent);
	
	/**
	 * @return
	 */
	public List<Agent> readAgentDetailsFromDayMaster(Agent agent) ;
	
	/**
	 * @param agent
	 * @return
	 */
	public int dataInsertionInMonthMaster(Agent agent);
	 /**
	 * @param agent
	 * @return
	 */
	public int dataUpdationInMonthMaster(Agent agent);
	
	public List<Agent> fetchProjectDetails(Agent agent);
	
}

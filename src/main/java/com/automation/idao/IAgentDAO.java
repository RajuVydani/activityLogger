package com.automation.idao;

import java.util.List;

import com.automation.vo.Agent;

public interface IAgentDAO {

	/**
	 * @param e
	 * @return
	 */
	int TemporaryTableInsert(Agent e);

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
	List<Agent> readTemporaryTable();

	/**
	 * @return
	 */

	List<Agent> readAgentDetails(String emailId);

	/**
	 * @param e
	 * @return
	 */
	List<Agent> activityHrsCalculation(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int dayMasterInsert(Agent e);

	/**
	 * @param e
	 * @return
	 */
	public int dayDetailInsert(Agent agent);

	/**
	 * @param agent
	 * @return
	 */

	int exceptionTableInsert(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int exceptionTableDelete(Agent e);

	/**
	 * @param e
	 * @return
	 */
	int temporaryTableDelete(Agent e);

	/**
	 * @param emailid
	 * @return
	 */
	List<Agent> readExceptionTable(String emailid);

	/**
	 * @return
	 */
	/**
	 * @return
	 */
	List<Agent> readExceptionTableAgentIds();

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> fetchShiftDetails(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public int dayMasterCount(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> dayMasterPreviousDayLogout(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public int dayDetailCount(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> getLoginTime(Agent agent);

	/**
	 * @param e
	 * @return
	 */
	/**
	 * @param e
	 * @return
	 */
	public int dayMasterUpdate(Agent e);

	/**
	 * @param agent
	 * @return
	 */
	public int dayDetailLastActivity(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public int dayDetailUpdate(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public int monthMasterCount(Agent agent);

	/**
	 * @return
	 */
	public List<Agent> readDayMaster(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public int monthMasterInsert(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public int monthMasterUpdate(Agent agent);

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> fetchProjectDetails(Agent agent);

	
	/**
	 * @param agent
	 * @return
	 */
	public int temporaryBkpTableInsert(Agent agent);
}

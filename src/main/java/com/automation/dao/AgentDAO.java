package com.automation.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.automation.idao.IAgentDAO;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author 597125
 *
 */
public class AgentDAO implements IAgentDAO {
	/**
	 * 
	 * AgentDAO()
	 */
	AgentDAO() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#getAgentData(com.automation.vo.Agent)
	 * This is Logger for printing logs
	 */
	private final static Logger LOGGER = Logger.getLogger(AgentDAO.class);
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#getAgentData(com.automation.vo.Agent)
	 * This method will fetch Agent Information from Agent Master
	 */
	public String getAgentData(Agent agent) throws Exception {

		// Query...
		String sql = "SELECT AGENT_NAME,SHIFT_TIMINGS FROM AGENT_MASTER";

		if (null == jdbcTemplate) {
			LOGGER.info("Could not connect to database !!!");
		}

		// Executing the query.
		jdbcTemplate.query(sql, new ResultSetExtractor<List<Agent>>() {
			public List<Agent> extractData(ResultSet resultset) throws SQLException, DataAccessException {
				List<Agent> list = new ArrayList<Agent>();
				Agent agent;
				while (resultset.next()) {
					agent = new Agent();
					agent.setName(resultset.getString("AGENT_NAME"));
					agent.setShiftTimings(resultset.getString("SHIFT_TIMINGS"));
					list.add(agent);
				}

				return list;
			}
		});
		return "Successfully";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#readChromeExceptionAgentTransactions(java.
	 * lang.String) This method will fetch Agent Transactions From Chrome
	 * Exception Table
	 */
	public List<Agent> readChromeExceptionAgentTransactions(String emailid) {
		LOGGER.info("inside readChromeExceptionAgentTransactions()");

		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select * from CHROME_EXCEPTION_DETAILS where EMAIL_ID='" + emailid.trim()
				+ "' ORDER BY FROM_TIME";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside readChromeExceptionAgentTransactions()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent agent = new Agent();
				agent.setEmailId(rs.getString(1));

				agent.setFromDate(rs.getString(2));
				agent.setToDate(rs.getString(3));
				agent.setWebsitesVisited(rs.getString(4));
				agent.setActivityCode(rs.getString(5));

				return agent;
			}
		});
	}

	public List<Agent> readChromeTempAgentTransactions() {
		LOGGER.info("inside readChromeTempAgentTransactions()");

		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select * from CHROME_TEMP_DETAILS ORDER BY EMAIL_ID,FROM_TIME";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside readChromeExceptionAgentTransactions()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent agent = new Agent();
				agent.setEmailId(rs.getString(1));

				agent.setFromDate(rs.getString(2));
				agent.setToDate(rs.getString(3));
				agent.setWebsitesVisited(rs.getString(4));
				agent.setActivityCode(rs.getString(5));

				return agent;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#readChromeTempAgentIds() This method
	 * will fetch Agent Email Ids From Chrome Temp Table
	 */
	public List<Agent> readChromeTempAgentIds() {
		LOGGER.info("inside readChromeTempAgentIds()");

		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select DISTINCT EMAIL_ID from CHROME_TEMP_DETAILS  ORDER BY EMAIL_ID,FROM_TIME";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(resultset.getString(1));

				return e;
			}
		});
	}

	///
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#readChromeExceptionAgentIds() This
	 * method will fetch Agent Email Ids From Chrome Exception Table
	 */
	public List<Agent> readChromeExceptionAgentIds() {

		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select DISTINCT E.EMAIL_ID from CHROME_EXCEPTION_DETAILS E,AGENT_MASTER A WHERE A.EMAIL_ID=E.EMAIL_ID ORDER BY E.EMAIL_ID";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside readChromeExceptionAgentIds()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(resultset.getString(1));

				return e;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#readAgentDetailsFromAgentMaster(java.lang.
	 * String) This method will fetch Agent details From Agent Master
	 */
	public List<Agent> readAgentDetailsFromAgentMaster(String emailId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside readAgentDetailsFromAgentMaster()");
			LOGGER.info("query=="
					+ "select AGENT_NAME,LOCATION_ID,HCM_SUPERVISOR_ID,HCM_SUPERVISOR_NAME,PROJECT_ID,BILLABLE,ON_OFF,AGENT_ID,SUB_PROJECT_ID,PROJECT_NAME,SUB_PROJECT_NAME from AGENT_MASTER WHERE EMAIL_ID='"
					+ emailId + "'");
		}

		return jdbcTemplate.query(
				"select IFNULL(AGENT_NAME,''),IFNULL(LOCATION_ID,''),IFNULL(HCM_SUPERVISOR_ID,0),IFNULL(HCM_SUPERVISOR_NAME,''),IFNULL(PROJECT_ID,0),IFNULL(BILLABLE,''),IFNULL(ON_OFF,''),IFNULL(AGENT_ID,0),IFNULL(SUB_PROJECT_ID,0),IFNULL(PROJECT_NAME,''),IFNULL(SUB_PROJECT_NAME,'') from AGENT_MASTER WHERE EMAIL_ID='"
						+ emailId + "'",
				new RowMapper<Agent>() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
					 * ResultSet, int)
					 */
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent agent = new Agent();
						agent.setName(rs.getString(1));

						agent.setLocation(rs.getString(2));
						agent.setHcmSupervisorId(rs.getString(3));
						agent.setHcmSupervisorName(rs.getString(4));
						agent.setProjectId(rs.getString(5));
						agent.setBillable(rs.getString(6));
						agent.setOnshoreOffshore(rs.getString(7));
						agent.setAgentId(rs.getString(8));
						agent.setSubProjectId(rs.getString(9));
						agent.setProjectName(rs.getString(10));
						agent.setSubProjectName(rs.getString(11));

						return agent;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInDayMaster(com.automation.vo.
	 * Agent) This method will insert data in Day Master
	 */
	public int dataInsertionInDayMaster(Agent e) {
		LOGGER.info("inside dataInsertionInDayMaster()");
		String query = "insert into DAY_MASTER(`DATE`, `EMAIL_ID`, `AGENT_NAME`,`SHIFT_DETAILS`,  `LOGIN_TIME`, `LOGOUT_TIME`,`LOCATION_ID`,`HCM_SUPERVISOR_ID`,`HCM_SUPERVISOR_NAME`,`PROJECT_ID`,`BILLABLE`,`ON_OFF`,`AGENT_ID`,`SUB_PROJECT_ID`,`PROJECT_NAME`,`SUB_PROJECT_NAME`) values('"
				+ e.getDATE() + "','" + e.getEmailId() + "','" + e.getName() + "','" + e.getShiftTimings() + "','"
				+ e.getLoginTime() + "','" + e.getLogoutTime() + "','" + e.getLocation() + "'," + e.getHcmSupervisorId()
				+ ",'" + e.getHcmSupervisorName() + "'," + e.getProjectId() + ",'" + e.getBillable() + "','"
				+ e.getOnshoreOffshore() + "'," + e.getAgentId() + "," + e.getSubProjectId() + ",'" + e.getProjectName()
				+ "','" + e.getSubProjectName() + "')";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.update(query);
	}

	public int dataUpdationInDayMaster(Agent e) {
		LOGGER.info("inside dataUpdationInDayMaster()");
		String query = "UPDATE DAY_MASTER SET LOGOUT_TIME='" + e.getLogoutTime() + "'," + e.getActivityHrs()
				+ " WHERE EMAIL_ID='" + e.getEmailId() + "' AND DATE='" + e.getDATE() + "'";

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#calculateTempActiviyHrs(com.automation.vo.
	 * Agent) This method will calculate Activity Hrs For Chrome Temp Table
	 */
	public List<Agent> calculateActiviyHrs(Agent agent) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside calculateTempActiviyHrs()");

			LOGGER.info("query=="
					+ "select SUM(TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME)),ACTIVITY_CODE FROM DAY_DETAIL WHERE EMAIL_ID='"
					+ agent.getEmailId() + "'  AND FROM_TIME >='" + agent.getFromDate() + "' AND TO_TIME <='"
					+ agent.getToDate() + "' GROUP BY ACTIVITY_CODE");
		}

		return jdbcTemplate.query(
				"select SUM(TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME)),ACTIVITY_CODE FROM DAY_DETAIL WHERE EMAIL_ID='"
						+ agent.getEmailId() + "'  AND FROM_TIME >='" + agent.getFromDate() + "' AND TO_TIME <='"
						+ agent.getToDate() + "' GROUP BY ACTIVITY_CODE",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent agent = new Agent();
						String seconds = rs.getString(1);
						float minutes = (Float.parseFloat(seconds) / 60);
					 

						agent.setActivityHrs(String.valueOf(minutes));
						agent.setActivityCode(rs.getString(2));

						return agent;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#deleteFromChromeTempDetail(com.automation.
	 * vo.Agent) This method will delete day from Chrome Temp Detail.
	 */
	public int deleteFromChromeTempDetail(Agent e) {

		String query = "DELETE FROM CHROME_TEMP_DETAILS " + "WHERE EMAIL_ID='" + e.getEmailId() + "'  AND FROM_TIME ='"
				+ e.getFromDate() + "'";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside deleteFromChromeTempDetail()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInException(com.automation.vo.
	 * Agent) This method will insert data in Chrome Exception Table.
	 */
	public int dataInsertionInException(Agent agent) {

		String query = "INSERT INTO `CHROME_EXCEPTION_DETAILS` (`EMAIL_ID`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE`,`ERROR_DESC`)"
				+ "VALUES('" + agent.getEmailId() + "','" + agent.getFromDate() + "','" + agent.getToDate() + "','"
				+ agent.getWebsitesVisited() + "'," + agent.getActivityCode() + ",'" + agent.getErrorDesc() + "')";

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dataInsertionInException()");
			LOGGER.info("query==" + query);

		}
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInDayDetailFromTempDetails(com
	 * .automation.vo.Agent) This method will move Transactions from Chrome Temp
	 * Table to Day Detail Table
	 */
	public int dataInsertionInDayDetail(Agent agent) {

		String query = "INSERT INTO `DAY_DETAIL` (`DATE`,`SNO`,`EMAIL_ID`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE`) VALUES('"
				+ agent.getDATE() + "'," + (agent.getRownum()) + ",'" + agent.getEmailId() + "','" + agent.getFromDate()
				+ "','" + agent.getToDate() + "','" + agent.getWebsitesVisited() + "'," + agent.getActivityCode() + ")";

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dataInsertionInDayDetail()");
			LOGGER.info("query==" + query);
		}

		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInDayDetailFromTempDetails(com
	 * .automation.vo.Agent) This method will move Transactions from Chrome Temp
	 * Table to Day Detail Table
	 */
	public List<Agent> fetchdataFromChromeTempDetails(Agent agent) {

		String query = "SELECT `EMAIL_ID`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE` FROM `CHROME_TEMP_DETAILS`"
				+ " WHERE EMAIL_ID='" + agent.getEmailId() + "' AND FROM_TIME >='" + agent.getFromDate()
				+ "' AND TO_TIME <='" + agent.getToDate() + "' ORDER BY FROM_TIME";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside fetchdataFromChromeTempDetails()");

			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent agent = new Agent();

				agent.setEmailId(resultset.getString(1));
				agent.setFromDate(resultset.getString(2));
				agent.setToDate(resultset.getString(3));
				agent.setWebsitesVisited(resultset.getString(4));
				agent.setActivityCode(resultset.getString(5));
				agent.setRownum(rownumber);

				return agent;
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#fetchShiftDetails(com.automation.vo.Agent)
	 */
	public List<Agent> fetchShiftDetails(Agent agent) {

		String query = "SELECT SHIFT_FROM,SHIFT_TO " + "FROM SHIFT_TIMINGS "
				+ " WHERE  CAST(SUBTIME(SHIFT_FROM, '01:00:00') AS TIME) <= '" + agent.getStartTime() + "' AND "
				+ "CAST(ADDTIME(SHIFT_FROM, '04:59:00') AS TIME) >= '" + agent.getStartTime() + "'";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside fetchShiftDetails()");

			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent agent = new Agent();

				agent.setShiftFrom(resultset.getString(1));
				agent.setShiftTo(resultset.getString(2));

				return agent;
			}
		});
	}

	/**
	 * @param agent
	 * @return
	 */
	public List<Agent> fetchdataFromChromeExceptionDetails(Agent agent) {

		String query = "SELECT `EMAIL_ID`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE` FROM `CHROME_EXCEPTION_DETAILS`"
				+ " WHERE EMAIL_ID='" + agent.getEmailId() + "' AND FROM_TIME >='" + agent.getFromDate()
				+ "' AND TO_TIME <='" + agent.getToDate() + "' ORDER BY FROM_TIME";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside fetchdataFromChromeExceptionDetails()");

			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent agent = new Agent();

				agent.setEmailId(resultset.getString(1));
				agent.setFromDate(resultset.getString(2));
				agent.setToDate(resultset.getString(3));
				agent.setWebsitesVisited(resultset.getString(4));
				agent.setActivityCode(resultset.getString(5));
				agent.setRownum(rownumber);

				return agent;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#
	 * dataInsertionInDayDetailFromExceptionDetails(com.automation.vo.Agent)
	 * This method will move Transactions from Chrome Exception Table to Day
	 * Detail Table
	 */
	public int dataInsertionInDayDetailFromExceptionDetails(Agent e) {

		String query = "INSERT INTO `DAY_DETAIL` (`EMAIL_ID`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE`) SELECT `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE` FROM `CHROME_EXCEPTION_DETAILS`"
				+ " WHERE EMAIL_ID='" + e.getEmailId() + "' AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='"
				+ e.getToDate() + "'";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dataInsertionInException()");
			LOGGER.info("query==" + query);

		}
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#deleteFromChromeException(com.automation.vo
	 * .Agent) This method will delete data from Chrome Exception Table.
	 */
	public int deleteFromChromeException(Agent agent) {

		String query = "DELETE FROM CHROME_EXCEPTION_DETAILS " + "WHERE EMAIL_ID='" + agent.getEmailId()
				+ "'  AND FROM_TIME ='" + agent.getFromDate() + "'";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside deleteFromChromeException()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInChromeDetails(com.automation
	 * .vo.Agent) This method will insert data in Chrome Temp Detail
	 */
	public int dataInsertionInChromeDetails(Agent agent) {

		String query = "insert into CHROME_TEMP_DETAILS( `EMAIL_ID`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE`) values("
				+ "'" + agent.getEmailId() + "','" + agent.getFromDate() + "','" + agent.getToDate() + "','"
				+ agent.getWebsitesVisited() + "'," + agent.getActivityCode() + ")";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dataInsertionInChromeDetails()");
			LOGGER.info("query==" + query);

		}
		return jdbcTemplate.update(query);
	}

	public int dataUpdationInDayDetails(Agent agent) {

		String query = "UPDATE DAY_DETAIL SET TO_TIME='"+agent.getToDate()+"' WHERE EMAIL_ID='"
				  + agent.getEmailId() + "' AND DATE='" + agent.getDATE()+ "' AND TO_TIME='" + agent.getFromDate() + "' AND ACTIVITY_CODE="
				  + agent.getActivityCode();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dataUpdationInDayDetails()");
			LOGGER.info("query==" + query);

		}
		return jdbcTemplate.update(query);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#idleInterval() This method will fetch
	 * Idle Interval
	 */
	public int idleInterval() {
		String query = "select IFNULL(INTERVAL_SECS,90)  from CHROME_IDLE_INTERVAL";

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside idleInterval()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.queryForInt(query);
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#getDayDetailCount(com.automation.vo.Agent)
	 */
	public int getDayDetailCount(Agent agent) {
		String query = "select COUNT(*) from DAY_DETAIL WHERE DATE='" + agent.getDATE() + "' AND EMAIL_ID='"
				+ agent.getEmailId() + "'";

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside getDayDetailCount()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.queryForInt(query);
	}

	
 
	/**
	 * @param agent
	 * @return
	 */
	public int checkDayDetailLastActivity(Agent agent) {
		String query = "select COUNT(*) from DAY_DETAIL WHERE DATE='" + agent.getDATE() + "' AND EMAIL_ID='"
				+ agent.getEmailId() + "' AND TO_TIME='"+agent.getToDate()+"' AND ACTIVITY_CODE="+agent.getActivityCode();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside checkDayDetailLastActivity()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.queryForInt(query);
	}
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#checkEntryExsistInDayMaster(com.automation.vo.Agent)
	 */
	public int checkEntryExsistInDayMaster(Agent agent) {

		String query = "select COUNT(*) from DAY_MASTER WHERE DATE='" + agent.getDATE() + "' AND EMAIL_ID='"
				+ agent.getEmailId() + "'";
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside checkEntryExsistInDayMaster()");
			LOGGER.info("query==" + query);
		}
		return jdbcTemplate.queryForInt(query);
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#getPreviousDayDetailsFromDayMaster(com.automation.vo.Agent)
	 */
	public List<Agent> getPreviousDayDetailsFromDayMaster(Agent agent) {

		String query = "select IFNULL(LOGOUT_TIME,''),IFNULL(AGENT_ID,'')  from DAY_MASTER WHERE DATE='"
				+ agent.getDATE() + "' AND EMAIL_ID='" + agent.getEmailId() + "'";
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside checkEntryExsistInDayMasters");
			LOGGER.info("query======" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent agent = new Agent();

				agent.setLogoutTime(resultset.getString(1));
				agent.setAgentId(resultset.getString(2));

				return agent;
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#geLoginTimeFromDayMaster(com.automation.vo.Agent)
	 */
	public List<Agent> geLoginTimeFromDayMaster(Agent agent) { 
		String query = "select IFNULL(LOGIN_TIME,'') from DAY_MASTER WHERE DATE='" + agent.getDATE()
				+ "' AND EMAIL_ID='" + agent.getEmailId() + "'";
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside checkEntryExsistInDayMasters");
			LOGGER.info("query======" + query);
		}
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent agent = new Agent();

				agent.setLoginTime(resultset.getString(1));

				return agent;
			}
		});
	}

	///////////////////////////////////////// UI
	///////////////////////////////////////// FUNCTIONS//////////////////////////////////////////////////
	/**
	 * @param managerName
	 * @return This method will fetch agent under manager Name
	 */
	public List<Agent> fetchAgentsInfoDayWise(String emailId, String fromDate, String toDate) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(
					"SELECT IFNULL(DATE_FORMAT(DATE, '%d %b %Y'),''),ROUND(IFNULL(PRODUCTIVITY_HRS,0),2),ROUND(IFNULL(IDLE_HRS,0),2),IFNULL(DATE_FORMAT(LOGIN_TIME, '%d %b %Y %T'),''),IFNULL(DATE_FORMAT(LOGOUT_TIME, '%d %b %Y %T'),''),IFNULL(SHIFT_DETAILS,'') FROM DAY_MASTER WHERE EMAIL_ID='"
							+ emailId + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate + "' ORDER BY DATE");
		}
		return jdbcTemplate.query(
				"SELECT IFNULL(DATE_FORMAT(DATE, '%d %b %Y'),''),ROUND(IFNULL(PRODUCTIVITY_HRS,0),2),ROUND(IFNULL(IDLE_HRS,0),2),IFNULL(DATE_FORMAT(LOGIN_TIME, '%d %b %Y %T'),''),IFNULL(DATE_FORMAT(LOGOUT_TIME, '%d %b %Y %T'),''),IFNULL(SHIFT_DETAILS,'') FROM DAY_MASTER WHERE EMAIL_ID='"
						+ emailId + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate + "' ORDER BY DATE",
				new RowMapper<Agent>() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
					 * ResultSet, int)
					 */
					public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Agent agent = new Agent();

						agent.setDATE(resultset.getString(1));

						agent.setProductiveHours(resultset.getString(2));
						agent.setIdleHours(resultset.getString(3));
						agent.setLoginTime(resultset.getString(4));
						agent.setLogoutTime(resultset.getString(5));
						agent.setShiftTimings(resultset.getString(6));

						return agent;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#FetchAgentsInfoOverall(java.lang.String,
	 * java.lang.String, java.lang.String) This method will fetch agent's over
	 * all Information
	 */
	public List<Agent> fetchAgentsInfoOverall(String managerName, String fromDate, String toDate) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(
					"SELECT IFNULL(EMAIL_ID,''),IFNULL(AGENT_NAME,''),ROUND(SUM(IFNULL(PRODUCTIVITY_HRS,0)),2),ROUND(SUM(IFNULL(IDLE_HRS,0)),2),IFNULL(SHIFT_DETAILS,''),IFNULL(PROJECT_ID,''),IFNULL(LOCATION,'') FROM DAY_MASTER WHERE HCM_SUPERVISOR='"
							+ managerName + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate
							+ "' GROUP BY PROJECT_ID ORDER BY AGENT_NAME");
		}
		return jdbcTemplate.query(
				"SELECT IFNULL(EMAIL_ID,''),IFNULL(AGENT_NAME,''),ROUND(SUM(IFNULL(PRODUCTIVITY_HRS,0)),2),ROUND(SUM(IFNULL(IDLE_HRS,0)),2),IFNULL(SHIFT_DETAILS,''),IFNULL(PROJECT_ID,''),IFNULL(LOCATION,'') FROM DAY_MASTER WHERE HCM_SUPERVISOR='"
						+ managerName + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate
						+ "' GROUP BY PROJECT_ID ORDER BY AGENT_NAME",
				new RowMapper<Agent>() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
					 * ResultSet, int)
					 */
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent agent = new Agent();

						agent.setEmailId(rs.getString(1));
						agent.setName(rs.getString(2));

						agent.setProductiveHours(rs.getString(3));
						agent.setIdleHours(rs.getString(4));
						agent.setShiftTimings(rs.getString(5));
						agent.setProjectId(rs.getString(6));
						agent.setLocation(rs.getString(7));

						return agent;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#FetchAgentsInfoFilterSpecific(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String) This method will fetch agent Info
	 * filter Specific
	 */
	public List<Agent> fetchAgentsInfoFilterSpecific(Agent agent) {
		String managerName = agent.getHcmSupervisorName();
		String fromDate = agent.getFromDate();
		String toDate = agent.getToDate();
		String projectId = agent.getProjectId();
		String location = agent.getLocation();
		String shiftTimings = agent.getShiftTimings();
		StringBuffer filterCriteria = new StringBuffer();
		if (!projectId.trim().equalsIgnoreCase("")) {
			filterCriteria = filterCriteria.append("AND PROJECT_ID='" + projectId + "'");
		}

		if (!location.trim().equalsIgnoreCase("")) {
			filterCriteria = filterCriteria.append(" AND LOCATION='" + location + "'");
		}

		if (!shiftTimings.trim().equalsIgnoreCase("")) {
			filterCriteria = filterCriteria.append(" AND SHIFT_DETAILS='" + shiftTimings + "'");
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(
					"SELECT IFNULL(EMAIL_ID,''),IFNULL(AGENT_NAME,''),ROUND(SUM(IFNULL(PRODUCTIVITY_HRS,0)),2),ROUND(SUM(IFNULL(IDLE_HRS,0)),2),IFNULL(SHIFT_DETAILS,''),IFNULL(PROJECT_ID,''),IFNULL(LOCATION,'') FROM DAY_MASTER WHERE HCM_SUPERVISOR='"
							+ managerName + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate + "' "
							+ filterCriteria.toString() + " ORDER BY AGENT_NAME");
		}
		return jdbcTemplate.query(
				"SELECT IFNULL(EMAIL_ID,''),IFNULL(AGENT_NAME,''),ROUND(SUM(IFNULL(PRODUCTIVITY_HRS,0)),2),ROUND(SUM(IFNULL(IDLE_HRS,0)),2),IFNULL(SHIFT_DETAILS,''),IFNULL(PROJECT_ID,''),IFNULL(PROJECT_ID,''),IFNULL(LOCATION,'') FROM DAY_MASTER WHERE HCM_SUPERVISOR='"
						+ managerName + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate + "' "
						+ filterCriteria.toString() + " ORDER BY AGENT_NAME",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setEmailId(resultset.getString(1));
						e.setName(resultset.getString(2));

						e.setProductiveHours(resultset.getString(3));
						e.setIdleHours(resultset.getString(4));
						e.setShiftTimings(resultset.getString(5));
						e.setProjectId(resultset.getString(6));
						e.setLocation(resultset.getString(7));
						return e;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#FetchAgentsTransacation(java.lang.String,
	 * java.lang.String, java.lang.String) This method will fetch Agent's
	 * Transaction
	 */
	public List<Agent> fetchAgentsTransacation(String emailId, String loginTime, String logOutTime) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(
					"SELECT IFNULL(DATE_FORMAT(FROM_TIME, '%d %b %Y %T'),''),IFNULL(DATE_FORMAT(TO_TIME, '%d %b %Y %T'),''),TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME),WEBSITE_USED FROM DAY_DETAIL WHERE EMAIL_ID='"
							+ emailId + "' AND FROM_TIME >='" + loginTime + "' AND FROM_TIME <='" + logOutTime + "'");
		}
		return jdbcTemplate.query(
				"SELECT IFNULL(DATE_FORMAT(FROM_TIME, '%d %b %Y %T'),''),IFNULL(DATE_FORMAT(TO_TIME, '%d %b %Y %T'),''),TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME),WEBSITE_USED FROM DAY_DETAIL WHERE EMAIL_ID='"
						+ emailId + "' AND FROM_TIME >='" + loginTime + "' AND FROM_TIME <='" + logOutTime + "'",
				new RowMapper<Agent>() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
					 * ResultSet, int) Mapping of return values from database
					 */
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent agent = new Agent();
						String seconds = rs.getString(3);
						float minutes = (Float.parseFloat(seconds) / 60);

						agent.setFromDate(rs.getString(1));
						agent.setToDate(rs.getString(2));
						agent.setIdleHours(String.valueOf(minutes));
						agent.setWebsitesVisited(rs.getString(4));

						return agent;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#FetchAgentsLoginLogoutTime(java.lang.
	 * String, java.lang.String) This method will fetch agent login and logout
	 * time.
	 */
	public List<Agent> fetchAgentsLoginLogoutTime(String emailid, String date) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("SELECT IFNULL(LOGIN_TIME,''),IFNULL(LOGOUT_TIME,'') FROM DAY_MASTER WHERE EMAIL_ID='" + emailid
					+ "' AND DATE= '" + date + "'");
		}
		return jdbcTemplate.query("SELECT IFNULL(LOGIN_TIME,''),IFNULL(LOGOUT_TIME,'') FROM DAY_MASTER WHERE EMAIL_ID='"
				+ emailid + "' AND DATE = '" + date + "'", new RowMapper<Agent>() {
					public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setLoginTime(resultset.getString(1));
						e.setLogoutTime(resultset.getString(2));

						return e;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#FetchAgentsProjectId(java.lang.String)
	 * This method will Fetch Agent's Project Id
	 */
	public List<Agent> fetchAgentsProjectId(String managerName) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("SELECT DISTINCT IFNULL(PROJECT_ID,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='" + managerName
					+ "' ORDER BY PROJECT_ID");
		}
		return jdbcTemplate.query("SELECT DISTINCT IFNULL(PROJECT_ID,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='"
				+ managerName + "' ORDER BY PROJECT_ID", new RowMapper<Agent>() {
					public Agent mapRow(ResultSet reultset, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setProjectId(reultset.getString(1));

						return e;
					}
				});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#FetchAgentsLocation(java.lang.String)
	 * This method will fetch Agent's Location
	 */
	public List<Agent> fetchAgentsLocation(String managerName) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("SELECT DISTINCT IFNULL(LOCATION,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='" + managerName
					+ "' ORDER BY LOCATION");
		}
		return jdbcTemplate.query("SELECT DISTINCT IFNULL(LOCATION,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='"
				+ managerName + "' ORDER BY LOCATION", new RowMapper<Agent>() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
					 * ResultSet, int)
					 */
					public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Agent agent = new Agent();

						agent.setProjectId(resultset.getString(1));

						return agent;
					}
				});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#FetchAgentsShiftTimings(java.lang.String)
	 * This method will fetch agents shift timings
	 */
	public List<Agent> fetchAgentsShiftTimings(String managerName) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("SELECT DISTINCT IFNULL(SHIFT_TIMINGS,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='"
					+ managerName + "' ORDER BY SHIFT_TIMINGS");
		}
		return jdbcTemplate.query("SELECT DISTINCT IFNULL(SHIFT_TIMINGS,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='"
				+ managerName + "' ORDER BY SHIFT_TIMINGS", new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setProjectId(rs.getString(1));

						return e;
					}
				});

	}
}

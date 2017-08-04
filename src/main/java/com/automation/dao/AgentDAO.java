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
	private final static Logger logger = Logger.getLogger(AgentDAO.class);
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
		System.out.println(AppConstants.CLASS + "-AgentDAO ::" + AppConstants.METHOD + "-storeAgentData()");

		// Query...
		String sql = "SELECT AGENT_NAME,SHIFT_TIMINGS FROM AGENT_MASTER";

		if (null == jdbcTemplate) {
			System.out.println("Could not connect to database !!!");
		}

		// Executing the query.
		jdbcTemplate.query(sql, new ResultSetExtractor<List<Agent>>() {
			public List<Agent> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Agent> list = new ArrayList<Agent>();
				Agent agent;
				while (rs.next()) {
					agent = new Agent();
					agent.setName(rs.getString("AGENT_NAME"));
					agent.setShiftTimings(rs.getString("SHIFT_TIMINGS"));
					list.add(agent);
				}
				System.out.println(list);
				return list;
			}
		});
		return "Successfully";
	}
 
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#readChromeTempAgentTransactions(java.lang.String)
	 * This method will fetch Agent Transactions From Chrome Temp Table
	 */
	public List<Agent> readChromeTempAgentTransactions(String emailid) {
		logger.info("inside readChromeTempAgentTransactions()");

		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select * from CHROME_TEMP_DETAILS where EMAIL_ID='" + emailid.trim() + "' ORDER BY FROM_TIME";
		logger.info("query==" + "select * from CHROME_TEMP_DETAILS where EMAIL_ID='" + emailid.trim()
				+ "' ORDER BY FROM_TIME");
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(rs.getString(1));
				e.setName(rs.getString(2));
				e.setFromDate(rs.getString(3));
				e.setToDate(rs.getString(4));
				e.setWebsitesVisited(rs.getString(5));
				e.setActivityCode(rs.getString(6));

				return e;
			}
		});
	}

 
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#readChromeExceptionAgentTransactions(java.lang.String)
	 * This method will fetch Agent Transactions From Chrome Exception Table
	 */
	public List<Agent> readChromeExceptionAgentTransactions(String emailid) {
		logger.info("inside readChromeExceptionAgentTransactions()");

		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select * from CHROME_EXCEPTION_DETAILS where EMAIL_ID='" + emailid.trim()
				+ "' ORDER BY FROM_TIME";
		logger.info("query==" + query);
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(rs.getString(1));
				e.setName(rs.getString(2));
				e.setFromDate(rs.getString(3));
				e.setToDate(rs.getString(4));
				e.setWebsitesVisited(rs.getString(5));
				e.setActivityCode(rs.getString(6));

				return e;
			}
		});
	}

 
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#readChromeTempAgentIds()
	 *This method will fetch Agent Email Ids From Chrome Temp Table
	 */
	public List<Agent> readChromeTempAgentIds() {
		logger.info("inside readChromeTempAgentIds()");
 
		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select DISTINCT EMAIL_ID from CHROME_TEMP_DETAILS  ORDER BY EMAIL_ID";
		logger.info("query==" + query);
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(rs.getString(1));

				return e;
			}
		});
	}

	///
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#readChromeExceptionAgentIds()
	 *This method will fetch Agent Email Ids From Chrome Exception Table
	 */
	public List<Agent> readChromeExceptionAgentIds() {
		logger.info("inside readChromeExceptionAgentIds()");

		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select DISTINCT E.EMAIL_ID from CHROME_EXCEPTION_DETAILS E,AGENT_MASTER A WHERE A.EMAIL_ID=E.EMAIL_ID ORDER BY E.EMAIL_ID";
		logger.info("query==" + query);
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(rs.getString(1));

				return e;
			}
		});
	}

 
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#readAgentDetailsFromAgentMaster(java.lang.String)
	 *This method will fetch Agent details From Agent Master
	 */
	public List<Agent> readAgentDetailsFromAgentMaster(String emailId) {
		logger.info("inside readAgentDetailsFromAgentMaster()");
		logger.info("query=="
				+ "select AGENT_NAME,SHIFT_TIMINGS,LOCATION,HCM_SUPERVISOR,PROJECT_ID from AGENT_MASTER WHERE EMAIL_ID='"
				+ emailId + "'");
		/*
		 * This is commented for multiple records for same agent in Agent Master
		 * //String loginTimeSplit[] = loginTime.split(" "); //
		 * "select AGENT_NAME,SHIFT_TIMINGS,LOCATION,HCM_SUPERVISOR,PROJECT_ID from AGENT_MASTER WHERE EMAIL_ID='"
		 * // + emailId + "' AND ALLOCATION_START_DT <= '" + loginTimeSplit[0] +
		 * "' AND ALLOCATION_END_DT >='" // + loginTimeSplit[0] + "'",
		 */
		return jdbcTemplate.query(
				"select IFNULL(AGENT_NAME,''),IFNULL(SHIFT_TIMINGS,''),IFNULL(LOCATION,''),IFNULL(HCM_SUPERVISOR,''),IFNULL(PROJECT_ID,'') from AGENT_MASTER WHERE EMAIL_ID='"
						+ emailId + "'",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();
						e.setName(rs.getString(1));
						e.setShiftTimings(rs.getString(2));
						e.setLocation(rs.getString(3));
						e.setHcmSupervisor(rs.getString(4));
						e.setProjectId(rs.getString(5));

						return e;
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
		logger.info("inside dataInsertionInDayMaster()");
		String query = "insert into DAY_MASTER(`DATE`, `EMAIL_ID`, `AGENT_NAME`,`SHIFT_DETAILS`,  `LOGIN_TIME`, `LOGOUT_TIME`,`PROD`,`IDLE`,`BREAK`,`MEALS`,`HUDDLE`,`WELLNESS_SUPPORT`,`COACHING`,`TEAM_MEETING`,`FB_TRAINING`,`NON_FB_TRAINING`,`LOCATION`,`HCM_SUPERVISOR`,`PROJECT_ID` ) values('"
				+ e.getDATE() + "','" + e.getEmailId() + "','" + e.getName() + "','" + e.getShiftTimings() + "','"
				+ e.getLoginTime() + "','" + e.getLogoutTime() + "'," + e.getActivityHrs() + ",'" + e.getLocation()
				+ "','" + e.getHcmSupervisor() + "','" + e.getProjectId() + "')";
		logger.info("query==" + query);
		return jdbcTemplate.update(query);
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#calculateTempActiviyHrs(com.automation.vo.Agent)
	 * This method will calculate Activity Hrs For Chrome Temp Table
	 */
	public List<Agent> calculateTempActiviyHrs(Agent e) {
		logger.info("inside calculateTempActiviyHrs()");

		logger.info("query=="
				+ "select SUM(TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME)),ACTIVITY_CODE FROM CHROME_TEMP_DETAILS WHERE EMAIL_ID='"
				+ e.getEmailId() + "'  AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='" + e.getToDate()
				+ "' GROUP BY ACTIVITY_CODE");
		/*
		 * This is commented for multiple records for same agent in Agent Master
		 * //String loginTimeSplit[] = loginTime.split(" "); //
		 * "select AGENT_NAME,SHIFT_TIMINGS,LOCATION,HCM_SUPERVISOR,PROJECT_ID from AGENT_MASTER WHERE EMAIL_ID='"
		 * // + emailId + "' AND ALLOCATION_START_DT <= '" + loginTimeSplit[0] +
		 * "' AND ALLOCATION_END_DT >='" // + loginTimeSplit[0] + "'",
		 */
		return jdbcTemplate.query(
				"select SUM(TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME)),ACTIVITY_CODE FROM CHROME_TEMP_DETAILS WHERE EMAIL_ID='"
						+ e.getEmailId() + "'  AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='"
						+ e.getToDate() + "' GROUP BY ACTIVITY_CODE",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();
						String seconds = rs.getString(1);
						float minutes = (Float.parseFloat(seconds) / 60);
						float hours = (minutes / 60);

						e.setActivityHrs(String.valueOf(hours));
						e.setActivityCode(rs.getString(2));

						return e;
					}
				});
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#calculateExceptionActiviyHrs(com.automation.vo.Agent)
	 * This method will calculate Activity Hrs For Chrome Exception Table
	 */
	public List<Agent> calculateExceptionActiviyHrs(Agent e) {
		logger.info("inside calculateExceptionActiviyHrs()");
		/*
		 * This is commented for multiple records for same agent in Agent Master
		 * //String loginTimeSplit[] = loginTime.split(" "); //
		 * "select AGENT_NAME,SHIFT_TIMINGS,LOCATION,HCM_SUPERVISOR,PROJECT_ID from AGENT_MASTER WHERE EMAIL_ID='"
		 * // + emailId + "' AND ALLOCATION_START_DT <= '" + loginTimeSplit[0] +
		 * "' AND ALLOCATION_END_DT >='" // + loginTimeSplit[0] + "'",
		 */
		logger.info("query=="
				+ "select SUM(TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME)),ACTIVITY_CODE FROM CHROME_EXCEPTION_DETAILS WHERE EMAIL_ID='"
				+ e.getEmailId() + "'  AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='" + e.getToDate()
				+ "' GROUP BY ACTIVITY_CODE");
		return jdbcTemplate.query(
				"select SUM(TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME)),ACTIVITY_CODE FROM CHROME_EXCEPTION_DETAILS WHERE EMAIL_ID='"
						+ e.getEmailId() + "'  AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='"
						+ e.getToDate() + "' GROUP BY ACTIVITY_CODE",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						String seconds = rs.getString(1);
						float minutes = (Float.parseFloat(seconds) / 60);
						float hours = (minutes / 60);

						e.setActivityHrs(String.valueOf(hours));
						e.setActivityCode(rs.getString(2));

						return e;
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
		logger.info("inside deleteFromChromeTempDetail()");
		String query = "DELETE FROM CHROME_TEMP_DETAILS " + "WHERE EMAIL_ID='" + e.getEmailId() + "'  AND FROM_TIME >='"
				+ e.getFromDate() + "' AND TO_TIME <='" + e.getToDate() + "'";
		logger.info("query==" + query);
		return jdbcTemplate.update(query);
	}

 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInException(com.automation.vo.
	 * Agent) This method will insert data in Chrome Exception Table.
	 */
	public int dataInsertionInException(Agent e) {
		logger.info("inside dataInsertionInException()");
		String query = "INSERT INTO `CHROME_EXCEPTION_DETAILS` (`EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE`) SELECT `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE` FROM `CHROME_TEMP_DETAILS`"
				+ " WHERE EMAIL_ID='" + e.getEmailId() + "' AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='"
				+ e.getToDate() + "'";
		logger.info("query==" + query);
		return jdbcTemplate.update(query);
	}

 
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#dataInsertionInDayDetailFromTempDetails(com.automation.vo.Agent)
	 * This method will move Transactions from Chrome Temp Table to Day Detail Table
	 */
	public int dataInsertionInDayDetailFromTempDetails(Agent e) {
		logger.info("inside dataInsertionInException()");
		String query = "INSERT INTO `DAY_DETAIL` (`EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE`) SELECT `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE` FROM `CHROME_TEMP_DETAILS`"
				+ " WHERE EMAIL_ID='" + e.getEmailId() + "' AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='"
				+ e.getToDate() + "'";
		logger.info("query==" + query);
		return jdbcTemplate.update(query);
	}

	 
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#dataInsertionInDayDetailFromExceptionDetails(com.automation.vo.Agent)
	 *  This method will move Transactions from Chrome Exception Table to Day Detail Table
	 */
	public int dataInsertionInDayDetailFromExceptionDetails(Agent e) {
		logger.info("inside dataInsertionInException()");
		String query = "INSERT INTO `DAY_DETAIL` (`EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE`) SELECT `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE` FROM `CHROME_EXCEPTION_DETAILS`"
				+ " WHERE EMAIL_ID='" + e.getEmailId() + "' AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='"
				+ e.getToDate() + "'";
		logger.info("query==" + query);
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#deleteFromChromeException(com.automation.vo
	 * .Agent) This method will delete data from Chrome Exception Table.
	 */
	public int deleteFromChromeException(Agent e) {
		logger.info("inside deleteFromChromeException()");
		String query = "DELETE FROM CHROME_EXCEPTION_DETAILS " + "WHERE EMAIL_ID='" + e.getEmailId()
				+ "'  AND FROM_TIME >='" + e.getFromDate() + "' AND TO_TIME <='" + e.getToDate() + "'";
		logger.info("query==" + query);
		return jdbcTemplate.update(query);
	}
 
 

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInChromeDetails(com.automation
	 * .vo.Agent) This method will insert data in Chrome Temp Detail
	 */
	public int dataInsertionInChromeDetails(Agent e) {
		logger.info("inside dataInsertionInChromeDetails()");
		String query = "insert into CHROME_TEMP_DETAILS( `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`,`ACTIVITY_CODE`) values("
				+ "'" + e.getEmailId() + "','" + e.getName() + "','" + e.getIdleFrom() + "','" + e.getIdleTo() + "','"
				+ e.getWebsitesVisited() + "','" + e.getActivityCode() + "')";
		logger.info("query==" + query);
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#idleInterval() This method will fetch
	 * Idle Interval
	 */
	public int idleInterval() {
		logger.info("inside idleInterval()");
		String query = "select IFNULL(INTERVAL_SECS,0)  from CHROME_IDLE_INTERVAL";
		logger.info("query==" + query);
		return jdbcTemplate.queryForInt(query);
	}
/////////////////////////////////////////UI FUNCTIONS//////////////////////////////////////////////////
	/**
	 * @param managerName
	 * @return This method will fetch agent under manager Name
	 */
	public List<Agent> FetchAgentsInfoDayWise(String emailId, String fromDate, String toDate) {
		logger.info(
				"SELECT IFNULL(DATE_FORMAT(DATE, '%d %b %Y'),''),ROUND(IFNULL(PRODUCTIVITY_HRS,0),2),ROUND(IFNULL(IDLE_HRS,0),2),IFNULL(DATE_FORMAT(LOGIN_TIME, '%d %b %Y %T'),''),IFNULL(DATE_FORMAT(LOGOUT_TIME, '%d %b %Y %T'),''),IFNULL(SHIFT_DETAILS,'') FROM DAY_MASTER WHERE EMAIL_ID='"
						+ emailId + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate + "' ORDER BY DATE");
		return jdbcTemplate.query(
				"SELECT IFNULL(DATE_FORMAT(DATE, '%d %b %Y'),''),ROUND(IFNULL(PRODUCTIVITY_HRS,0),2),ROUND(IFNULL(IDLE_HRS,0),2),IFNULL(DATE_FORMAT(LOGIN_TIME, '%d %b %Y %T'),''),IFNULL(DATE_FORMAT(LOGOUT_TIME, '%d %b %Y %T'),''),IFNULL(SHIFT_DETAILS,'') FROM DAY_MASTER WHERE EMAIL_ID='"
						+ emailId + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate + "' ORDER BY DATE",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setDATE(rs.getString(1));

						e.setProductiveHours(rs.getString(2));
						e.setIdleHours(rs.getString(3));
						e.setLoginTime(rs.getString(4));
						e.setLogoutTime(rs.getString(5));
						e.setShiftTimings(rs.getString(6));

						return e;
					}
				});
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#FetchAgentsInfoOverall(java.lang.String, java.lang.String, java.lang.String)
	 * This method will fetch agent's over all Information
	 */
	public List<Agent> FetchAgentsInfoOverall(String managerName, String fromDate, String toDate) {
		logger.info(
				"SELECT IFNULL(EMAIL_ID,''),IFNULL(AGENT_NAME,''),ROUND(SUM(IFNULL(PRODUCTIVITY_HRS,0)),2),ROUND(SUM(IFNULL(IDLE_HRS,0)),2),IFNULL(SHIFT_DETAILS,''),IFNULL(PROJECT_ID,''),IFNULL(LOCATION,'') FROM DAY_MASTER WHERE HCM_SUPERVISOR='"
						+ managerName + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate
						+ "' GROUP BY PROJECT_ID ORDER BY AGENT_NAME");
		return jdbcTemplate.query(
				"SELECT IFNULL(EMAIL_ID,''),IFNULL(AGENT_NAME,''),ROUND(SUM(IFNULL(PRODUCTIVITY_HRS,0)),2),ROUND(SUM(IFNULL(IDLE_HRS,0)),2),IFNULL(SHIFT_DETAILS,''),IFNULL(PROJECT_ID,''),IFNULL(LOCATION,'') FROM DAY_MASTER WHERE HCM_SUPERVISOR='"
						+ managerName + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate
						+ "' GROUP BY PROJECT_ID ORDER BY AGENT_NAME",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setEmailId(rs.getString(1));
						e.setName(rs.getString(2));

						e.setProductiveHours(rs.getString(3));
						e.setIdleHours(rs.getString(4));
						e.setShiftTimings(rs.getString(5));
						e.setProjectId(rs.getString(6));
						e.setLocation(rs.getString(7));

						return e;
					}
				});
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#FetchAgentsInfoFilterSpecific(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * This method will fetch agent Info filter Specific
	 */
	public List<Agent> FetchAgentsInfoFilterSpecific(String managerName, String fromDate, String toDate,
			String projectId, String Location, String ShiftTimings) {

		String filterCriteria = "";
		if (!projectId.trim().equalsIgnoreCase("")) {
			filterCriteria = filterCriteria + "AND PROJECT_ID='" + projectId + "'";
		}

		if (!Location.trim().equalsIgnoreCase("")) {
			filterCriteria = filterCriteria + " AND LOCATION='" + Location + "'";
		}

		if (!ShiftTimings.trim().equalsIgnoreCase("")) {
			filterCriteria = filterCriteria + " AND SHIFT_DETAILS='" + ShiftTimings + "'";
		}
		logger.info(
				"SELECT IFNULL(EMAIL_ID,''),IFNULL(AGENT_NAME,''),ROUND(SUM(IFNULL(PRODUCTIVITY_HRS,0)),2),ROUND(SUM(IFNULL(IDLE_HRS,0)),2),IFNULL(SHIFT_DETAILS,''),IFNULL(PROJECT_ID,''),IFNULL(LOCATION,'') FROM DAY_MASTER WHERE HCM_SUPERVISOR='"
						+ managerName + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate + "' " + filterCriteria
						+ " ORDER BY AGENT_NAME");
		return jdbcTemplate.query(
				"SELECT IFNULL(EMAIL_ID,''),IFNULL(AGENT_NAME,''),ROUND(SUM(IFNULL(PRODUCTIVITY_HRS,0)),2),ROUND(SUM(IFNULL(IDLE_HRS,0)),2),IFNULL(SHIFT_DETAILS,''),IFNULL(PROJECT_ID,''),IFNULL(PROJECT_ID,''),IFNULL(LOCATION,'') FROM DAY_MASTER WHERE HCM_SUPERVISOR='"
						+ managerName + "' AND DATE >= '" + fromDate + "' AND DATE <='" + toDate + "' " + filterCriteria
						+ " ORDER BY AGENT_NAME",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setEmailId(rs.getString(1));
						e.setName(rs.getString(2));

						e.setProductiveHours(rs.getString(3));
						e.setIdleHours(rs.getString(4));
						e.setShiftTimings(rs.getString(5));
						e.setProjectId(rs.getString(6));
						e.setLocation(rs.getString(7));
						return e;
					}
				});
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#FetchAgentsTransacation(java.lang.String, java.lang.String, java.lang.String)
	 * This method will fetch Agent's Transaction
	 */
	public List<Agent> FetchAgentsTransacation(String email_id, String loginTime, String logOutTime) {
		logger.info(
				"SELECT IFNULL(DATE_FORMAT(FROM_TIME, '%d %b %Y %T'),''),IFNULL(DATE_FORMAT(TO_TIME, '%d %b %Y %T'),''),TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME),WEBSITE_USED FROM DAY_DETAIL WHERE EMAIL_ID='"
						+ email_id + "' AND FROM_TIME >='" + loginTime + "' AND FROM_TIME <='" + logOutTime + "'");
		return jdbcTemplate.query(
				"SELECT IFNULL(DATE_FORMAT(FROM_TIME, '%d %b %Y %T'),''),IFNULL(DATE_FORMAT(TO_TIME, '%d %b %Y %T'),''),TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME),WEBSITE_USED FROM DAY_DETAIL WHERE EMAIL_ID='"
						+ email_id + "' AND FROM_TIME >='" + loginTime + "' AND FROM_TIME <='" + logOutTime + "'",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();
						String seconds = rs.getString(3);
						float minutes = (Float.parseFloat(seconds) / 60);

						e.setIdleFrom(rs.getString(1));
						e.setIdleTo(rs.getString(2));
						e.setIdleHours(String.valueOf(minutes));
						e.setWebsitesVisited(rs.getString(4));

						return e;
					}
				});
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#FetchAgentsLoginLogoutTime(java.lang.String, java.lang.String)
	 * This method will fetch agent login and logout time.
	 */
	public List<Agent> FetchAgentsLoginLogoutTime(String emailid, String date) {
		logger.info("SELECT IFNULL(LOGIN_TIME,''),IFNULL(LOGOUT_TIME,'') FROM DAY_MASTER WHERE EMAIL_ID='" + emailid
				+ "' AND DATE= '" + date + "'");
		return jdbcTemplate.query("SELECT IFNULL(LOGIN_TIME,''),IFNULL(LOGOUT_TIME,'') FROM DAY_MASTER WHERE EMAIL_ID='"
				+ emailid + "' AND DATE = '" + date + "'", new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setLoginTime(rs.getString(1));
						e.setLogoutTime(rs.getString(2));

						return e;
					}
				});
	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#FetchAgentsProjectId(java.lang.String)
	 * This method will Fetch Agent's Project Id  
	 */
	public List<Agent> FetchAgentsProjectId(String managerName) {
		logger.info("SELECT DISTINCT IFNULL(PROJECT_ID,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='" + managerName
				+ "' ORDER BY PROJECT_ID");
		return jdbcTemplate.query("SELECT DISTINCT IFNULL(PROJECT_ID,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='"
				+ managerName + "' ORDER BY PROJECT_ID", new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setProjectId(rs.getString(1));

						return e;
					}
				});

	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#FetchAgentsLocation(java.lang.String)
	 * This method will fetch Agent's Location
	 */
	public List<Agent> FetchAgentsLocation(String managerName) {
		logger.info("SELECT DISTINCT IFNULL(LOCATION,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='" + managerName
				+ "' ORDER BY LOCATION");
		return jdbcTemplate.query("SELECT DISTINCT IFNULL(LOCATION,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='"
				+ managerName + "' ORDER BY LOCATION", new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						e.setProjectId(rs.getString(1));

						return e;
					}
				});

	}

	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#FetchAgentsShiftTimings(java.lang.String)
	 * This method will fetch agents shift timings
	 */
	public List<Agent> FetchAgentsShiftTimings(String managerName) {
		logger.info("SELECT DISTINCT IFNULL(SHIFT_TIMINGS,'') FROM AGENT_MASTER WHERE HCM_SUPERVISOR='" + managerName
				+ "' ORDER BY SHIFT_TIMINGS");
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

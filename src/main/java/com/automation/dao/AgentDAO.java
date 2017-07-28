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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#readChromTempMasterTable() This method
	 * will read Chrome Temp Master.
	 */
	public List<Agent> readChromTempMasterTable() {
		logger.info("inside readChromTempMasterTable()");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		// String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <=
		// '"+ dateFormat.format(cal.getTime()) + " 12:00:00'";
		String query = "select * from CHROME_TEMP_MASTER WHERE LOGIN_TIME <= '" + dateFormat.format(cal.getTime())
				+ " 12:00:00'";
		return jdbcTemplate.query(query, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(rs.getString(1));
				e.setName(rs.getString(2));
				e.setProductiveHours(rs.getString(3));
				e.setLoginTime(rs.getString(4));
				e.setLogoutTime(rs.getString(5));

				return e;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#readChromTempTable() This method will
	 * read Chrome Temp Details.
	 */
	public List<Agent> readChromTempTable() {
		logger.info("inside readChromTempTable()");
		return jdbcTemplate.query("select * from CHROME_TEMP_DETAILS", new RowMapper<Agent>() {
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(rs.getString(1));
				e.setName(rs.getString(2));
				e.setIdleFrom(rs.getString(3));
				e.setIdleTo(rs.getString(4));
				e.setWebsitesVisited(rs.getString(5));

				return e;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#readChromExceptionTable() This method
	 * will read Chrome Exception Table.
	 */
	public List<Agent> readChromExceptionTable() {
		logger.info("inside readChromExceptionTable()");
		logger.info("===");
		return jdbcTemplate.query(
				"select EMAIL_ID,AGENT_NAME,PRODUCTIVITY_HRS,LOGIN_TIME,LOGOUT_TIME  from CHROME_EXCEPTION_DETAILS",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();
						e.setEmailId(rs.getString(1));
						e.setName(rs.getString(2));
						e.setProductiveHours(rs.getString(3));
						e.setLoginTime(rs.getString(4));
						e.setLogoutTime(rs.getString(5));

						return e;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#readAgentDetailsFromAgentMaster(java.lang.
	 * String) This method will fetch Agent Information from Agent Master
	 */
	public List<Agent> readAgentDetailsFromAgentMaster(String emailId, String loginTime) {
		logger.info("inside readAgentDetailsFromAgentMaster()");
		String loginTimeSplit[] = loginTime.split(" ");

		return jdbcTemplate.query(
				"select AGENT_NAME,SHIFT_TIMINGS,LOCATION,HCM_SUPERVISOR,PROJECT_ID from AGENT_MASTER WHERE EMAIL_ID='"
						+ emailId + "' AND ALLOCATION_START_DT <= '" + loginTimeSplit[0] + "' AND ALLOCATION_END_DT >='"
						+ loginTimeSplit[0] + "'",
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
		String query = "insert into DAY_MASTER(`DATE`, `EMAIL_ID`, `AGENT_NAME`,`SHIFT_DETAILS`,  `LOGIN_TIME`, `LOGOUT_TIME`,`PRODUCTIVITY_HRS`,`LOCATION`,`HCM_SUPERVISOR`,`PROJECT_ID` ) values('"
				+ e.getDATE() + "','" + e.getEmailId() + "','" + e.getName() + "','" + e.getShiftTimings() + "','"
				+ e.getLoginTime() + "','" + e.getLogoutTime() + "'," + e.getProductiveHours() + ",'" + e.getLocation()
				+ "','" + e.getHcmSupervisor() + "','" + e.getProjectId() + "')";
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#CheckLoginEntry(com.automation.vo.Agent)
	 * This method will check whether agent Logged in already for the day.
	 */
	public List<Agent> CheckLoginEntry(Agent e) {
		logger.info("inside CheckLoginEntry()");
		return jdbcTemplate.query(
				"SELECT LOGIN_TIME,PRODUCTIVITY_HRS FROM chrome_temp_master WHERE EMAIL_ID='" + e.getEmailId()
						+ "' AND TIMESTAMPDIFF(MINUTE,LOGOUT_TIME,'" + e.getLoginTime()
						+ "') <= 240 && TIMESTAMPDIFF(MINUTE,LOGOUT_TIME,'" + e.getLoginTime() + "') >= 0",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						String seconds = rs.getString(2);

						e.setProductiveHours(String.valueOf(seconds));
						e.setLoginTime(rs.getString(1));

						return e;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#CheckInChromeMaster(java.lang.String,
	 * java.lang.String) This method will fetch Login Time and Prod Hrs from
	 * Chrome Temp Master
	 */
	public List<Agent> CheckInChromeMaster(String emailId, String loginTime) {
		logger.info("inside CheckInChromeMaster()");
		return jdbcTemplate.query("SELECT LOGIN_TIME,PRODUCTIVITY_HRS FROM CHROME_TEMP_MASTER WHERE EMAIL_ID='"
				+ emailId + "' AND LOGIN_TIME='" + loginTime + "'", new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						String seconds = rs.getString(2);

						e.setProductiveHours(String.valueOf(seconds));

						return e;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInDayDetail(com.automation.vo.
	 * Agent) This method will insert data in to Day Detail.
	 */
	public int dataInsertionInDayDetail(Agent e) {
		logger.info("inside dataInsertionInDayDetail()");
		String query = "INSERT INTO DAY_DETAIL (`EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`)"
				+ "SELECT `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`" + "FROM CHROME_TEMP_DETAILS "
				+ "WHERE EMAIL_ID='" + e.getEmailId() + "' AND TO_TIME <= '" + e.getLogoutTime() + "'";

		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#totalAgentCountInDayMaster(java.lang.
	 * String, java.lang.String) This method will check Agent Already present in
	 * agent master.
	 */
	public int totalAgentCountInDayMaster(String emailId, String Date) {
		logger.info("inside totalAgentCountInDayMaster()");
		String sql = "select count(*) from DAY_MASTER WHERE EMAIL_ID='" + emailId + "' AND date='" + Date + "'";

		return jdbcTemplate.queryForInt(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#deleteFromChromeTempDetail(com.automation.
	 * vo.Agent) This method will delete dayta from Chrome Temp Detail.
	 */
	public int deleteFromChromeTempDetail(Agent e) {
		logger.info("inside deleteFromChromeTempDetail()");
		String query = "DELETE FROM CHROME_TEMP_DETAILS " + "WHERE EMAIL_ID='" + e.getEmailId() + "' AND TO_TIME <= ('"
				+ e.getLogoutTime() + "' + INTERVAL 1 HOUR)";
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#deleteFromChromeTempMaster(com.automation.
	 * vo.Agent) This method will delete data from Chrome Temp Master.
	 */
	public int deleteFromChromeTempMaster(Agent e) {
		logger.info("inside deleteFromChromeTempMaster()");
		String query = "DELETE FROM CHROME_TEMP_MASTER WHERE EMAIL_ID='" + e.getEmailId() + "' AND LOGIN_TIME='"
				+ e.getLoginTime() + "'";

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
		String query = "insert into CHROME_EXCEPTION_DETAILS( `EMAIL_ID`, `AGENT_NAME`,  `LOGIN_TIME`, `LOGOUT_TIME`,`PRODUCTIVITY_HRS`,`ERROR_DESC` ) values("
				+ "'" + e.getEmailId() + "','" + e.getName() + "','" + e.getLoginTime() + "','" + e.getLogoutTime()
				+ "'," + e.getProductiveHours() + ",'" + e.getErrorDesc() + "')";
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

		String query = "DELETE FROM CHROME_EXCEPTION_DETAILS WHERE EMAIL_ID='" + e.getEmailId() + "' AND LOGIN_TIME='"
				+ e.getLoginTime() + "'";
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#CalculateIdleHrs(com.automation.vo.Agent)
	 * This method will calculate Idle Hours.
	 */
	public List<Agent> CalculateIdleHrs(Agent e) {
		logger.info("inside CalculateIdleHrs()");
		return jdbcTemplate.query(
				"SELECT sum(TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME)) FROM DAY_DETAIL WHERE EMAIL_ID='" + e.getEmailId()
						+ "' AND FROM_TIME >='" + e.getLoginTime() + "' AND FROM_TIME <='" + e.getLogoutTime() + "'",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();

						String seconds = rs.getString(1);
						float minutes = (Float.parseFloat(seconds) / 60);
						float hours = (minutes / 60);

						e.setIdleHours(String.valueOf(hours));

						return e;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#updateIdleHrsInDayMaster(com.automation.vo.
	 * Agent) This method will update the Idle Hrs.
	 */
	public int updateIdleHrsInDayMaster(Agent e) {
		logger.info("inside updateIdleHrsInDayMaster()");
		String query = "UPDATE DAY_MASTER SET IDLE_HRS=" + e.getIdleHours() + " WHERE EMAIL_ID='" + e.getEmailId()
				+ "' AND DATE='" + e.getDATE() + "'";

		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#totalAgentCountInChromeMater(java.lang.
	 * String, java.lang.String) This method will check agent login Time present
	 * in CHROME TEMP master
	 */
	public int totalAgentCountInChromeMater(String emailId, String logindate) {
		logger.info("inside totalAgentCountInChromeMater()");
		String sql = "select count(*) from CHROME_TEMP_MASTER WHERE EMAIL_ID='" + emailId + "' AND LOGIN_TIME='"
				+ logindate + "'";

		return jdbcTemplate.queryForInt(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInChromeMater(com.automation.
	 * vo.Agent) This method will insert data in Chrome Temp Master
	 */
	public int dataInsertionInChromeMater(Agent e) {
		logger.info("inside dataInsertionInChromeMater()");
		String query = "insert into CHROME_TEMP_MASTER( `EMAIL_ID`, `AGENT_NAME`,  `LOGIN_TIME`, `LOGOUT_TIME`,`PRODUCTIVITY_HRS` ) values("
				+ "'" + e.getEmailId() + "','" + e.getName() + "','" + e.getLoginTime() + "','" + e.getLogoutTime()
				+ "'," + e.getProductiveHours() + ")";
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataUpdateInChromeMater(com.automation.vo.
	 * Agent) This method will update data in Chrome Temp Master
	 */
	public int dataUpdateInChromeMater(Agent e) {
		logger.info("inside dataUpdateInChromeMater()");
		String query = "UPDATE CHROME_TEMP_MASTER SET LOGOUT_TIME='" + e.getLogoutTime() + "',PRODUCTIVITY_HRS="
				+ e.getProductiveHours() + "   WHERE EMAIL_ID='" + e.getEmailId() + "' AND LOGIN_TIME='"
				+ e.getLoginTime() + "'";

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
		String query = "insert into CHROME_TEMP_DETAILS( `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`) values("
				+ "'" + e.getEmailId() + "','" + e.getName() + "','" + e.getIdleFrom() + "','" + e.getIdleTo() + "','"
				+ e.getWebsitesVisited() + "')";
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
		String sql = "select IFNULL(INTERVAL_SECS,0)  from CHROME_IDLE_INTERVAL";

		return jdbcTemplate.queryForInt(sql);
	}

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

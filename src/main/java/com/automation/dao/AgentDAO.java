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

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

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
	////////////////////////////////////

	public List<Agent> readChromTempMasterTable() {

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

	//////////////// Read Data From Chrom Temporary Table//////////////
	public List<Agent> readChromTempTable() {
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

	//////////////// Read Data From Chrome Exception Table//////////////
	public List<Agent> readChromExceptionTable() {

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

	//////////////// Read Data From Agent Master Table//////////////
	public List<Agent> readAgentDetailsFromAgentMaster(String emailId) {
		return jdbcTemplate.query("select AGENT_NAME,SHIFT_TIMINGS from AGENT_MASTER WHERE EMAIL_ID='" + emailId + "'",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();
						e.setName(rs.getString(1));
						e.setShiftTimings(rs.getString(2));
						return e;
					}
				});
	}

	//////////////// Data Insertion In Day Master Table//////////////
	public int dataInsertionInDayMaster(Agent e) {
		String query = "insert into DAY_MASTER(`DATE`, `EMAIL_ID`, `AGENT_NAME`,`SHIFT_DETAILS`,  `LOGIN_TIME`, `LOGOUT_TIME`,`PRODUCTIVITY_HRS` ) values('"
				+ e.getDATE() + "','" + e.getEmailId() + "','" + e.getName() + "','" + e.getShiftTimings() + "','"
				+ e.getLoginTime() + "','" + e.getLogoutTime() + "'," + e.getProductiveHours() + ")";
		return jdbcTemplate.update(query);
	}

	//////////////// Data Insertion In Day Detail Table//////////////
	public int dataInsertionInDayDetail(Agent e) {
		String query = "INSERT INTO DAY_DETAIL (`EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`)"
				+ "SELECT `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`" + "FROM CHROME_TEMP_DETAILS "
				+ "WHERE EMAIL_ID='" + e.getEmailId() + "' AND TO_TIME <= '" + e.getLogoutTime() + "'";

		return jdbcTemplate.update(query);
	}

	//////////////// Check Agent Present in Agent Master Table//////////////
	public int totalAgentCountInDayMaster(String emailId, String Date) {
		String sql = "select count(*) from DAY_MASTER WHERE EMAIL_ID='" + emailId + "' AND date='" + Date + "'";

		return jdbcTemplate.queryForInt(sql);
	}

	//////////////// Data Deletion in Chrome Temporary Table//////////////
	public int deleteFromChromeTempDetail(Agent e) {
		String query = "DELETE FROM CHROME_TEMP_DETAILS " + "WHERE EMAIL_ID='" + e.getEmailId() + "' AND TO_TIME <= ('"
				+ e.getLogoutTime() + "' + INTERVAL 1 HOUR)";
		return jdbcTemplate.update(query);
	}
	////

	public int deleteFromChromeTempMaster(Agent e) {
		String query = "DELETE FROM CHROME_TEMP_MASTER WHERE EMAIL_ID='" + e.getEmailId() + "' AND LOGIN_TIME='"
				+ e.getLoginTime() + "'";

		return jdbcTemplate.update(query);
	}

	//////////////// Data Insertion in Chrome Exception Table//////////////
	public int dataInsertionInException(Agent e) {
		String query = "insert into CHROME_EXCEPTION_DETAILS( `EMAIL_ID`, `AGENT_NAME`,  `LOGIN_TIME`, `LOGOUT_TIME`,`PRODUCTIVITY_HRS`,`ERROR_DESC` ) values("
				+ "'" + e.getEmailId() + "','" + e.getName() + "','" + e.getLoginTime() + "','" + e.getLogoutTime()
				+ "'," + e.getProductiveHours() + ",'" + e.getErrorDesc() + "')";
		return jdbcTemplate.update(query);
	}

	//////////////// Data Deletion in Chrome Exception Table//////////////
	public int deleteFromChromeException(Agent e) {

		String query = "DELETE FROM CHROME_EXCEPTION_DETAILS WHERE EMAIL_ID='" + e.getEmailId() + "' AND LOGIN_TIME='"
				+ e.getLoginTime() + "'";
		return jdbcTemplate.update(query);
	}

	////////////////////////////// Idle Hrs Calculation//////////////

	public List<Agent> CalculateIdleHrs(Agent e) {
		return jdbcTemplate.query(
				"SELECT sum(TIMESTAMPDIFF(SECOND,FROM_TIME,TO_TIME)) FROM DAY_DETAIL WHERE EMAIL_ID='" + e.getEmailId()
						+ "' AND FROM_TIME >='" + e.getLoginTime() + "' AND FROM_TIME <='" + e.getLogoutTime() + "'",
				new RowMapper<Agent>() {
					public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
						Agent e = new Agent();
						
						String seconds=rs.getString(1);
						float minutes=(Float.parseFloat(seconds)/60);
						float hours = (minutes / 60);					 
						
						e.setIdleHours( String.valueOf(hours));

						return e;
					}
				});
	}

	//////////////////////////////// Idle Hrs Updation////////////////////

	public int updateIdleHrsInDayMaster(Agent e) {
		String query = "UPDATE DAY_MASTER SET IDLE_HRS=" + e.getIdleHours() + " WHERE EMAIL_ID='" + e.getEmailId()
				+ "' AND DATE='" + e.getDATE() + "'";

		return jdbcTemplate.update(query);
	}
	//////////////////////////////////////////// REST CALL FOR UPDATING
	//////////////////////////////////////////// PRODUCTIVITY HOURS//////

	//////////////// Check Agent Present in Agent Master Table//////////////
	public int totalAgentCountInChromeMater(String emailId, String logindate) {
		String sql = "select count(*) from CHROME_TEMP_MASTER WHERE EMAIL_ID='" + emailId + "' AND LOGIN_TIME='"
				+ logindate + "'";

		return jdbcTemplate.queryForInt(sql);
	}

	public int dataInsertionInChromeMater(Agent e) {
		String query = "insert into CHROME_TEMP_MASTER( `EMAIL_ID`, `AGENT_NAME`,  `LOGIN_TIME`, `LOGOUT_TIME`,`PRODUCTIVITY_HRS` ) values("
				+ "'" + e.getEmailId() + "','" + e.getName() + "','" + e.getLoginTime() + "','" + e.getLogoutTime()
				+ "'," + e.getProductiveHours() + ")";
		return jdbcTemplate.update(query);
	}

	public int dataUpdateInChromeMater(Agent e) {
		String query = "UPDATE CHROME_TEMP_MASTER SET LOGOUT_TIME='" + e.getLogoutTime() + "',PRODUCTIVITY_HRS="
				+ e.getProductiveHours() + "   WHERE EMAIL_ID='" + e.getEmailId() + "' AND LOGIN_TIME='"
				+ e.getLoginTime() + "'";

		return jdbcTemplate.update(query);
	}

	public int dataInsertionInChromeDetails(Agent e) {
		String query = "insert into CHROME_TEMP_DETAILS( `EMAIL_ID`,`AGENT_NAME`,`FROM_TIME`,`TO_TIME`,`WEBSITE_USED`) values("
				+ "'" + e.getEmailId() + "','" + e.getName() + "','" + e.getIdleFrom() + "','" + e.getIdleTo() + "','"
				+ e.getWebsitesVisited() + "')";
		return jdbcTemplate.update(query);
	}
}
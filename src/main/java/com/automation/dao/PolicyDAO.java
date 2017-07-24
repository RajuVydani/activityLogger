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
import com.automation.idao.IPolicyDAO;
import com.automation.services.TrackerService;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;
import com.automation.vo.Policy;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class PolicyDAO implements IPolicyDAO {
	private final static Logger logger = Logger.getLogger(PolicyDAO.class);
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getAgentData(Policy agent) throws Exception {
		System.out.println(AppConstants.CLASS + "-AgentDAO ::" + AppConstants.METHOD + "-storeAgentData()");

		// Query...
		String sql = "SELECT AGENT_NAME,SHIFT_TIMINGS FROM AGENT_MASTER";

		if (null == jdbcTemplate) {
			System.out.println("Could not connect to database !!!");
		}

		// Executing the query.
		jdbcTemplate.query(sql, new ResultSetExtractor<List<Policy>>() {
			public List<Policy> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Policy> list = new ArrayList<Policy>();
				Policy agent;
				while (rs.next()) {

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
	 * @see com.automation.idao.IPolicyDAO#readPolicyFlag(java.lang.String) This
	 * method will raed policy flag in Agent Master
	 */
	public List<Policy> readPolicyFlag(String emailId) {
		logger.info("inside readPolicyFlag()");

		String query = "select IFNULL(POLICY_UPDATE,'') from AGENT_MASTER WHERE EMAIL_ID = '" + emailId + "'";

		return jdbcTemplate.query(query, new RowMapper<Policy>() {
			public Policy mapRow(ResultSet rs, int rownumber) throws SQLException {
				Policy e = new Policy();
				e.setPolicyFlag(rs.getString(1));
				return e;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IPolicyDAO#readPolicyDetails() This method will
	 * raed latest policy details
	 */
	public List<Policy> readPolicyDetails() {
		logger.info("inside readPolicyDetails()");

		String query = "select IFNULL(POLICY_CONTENT,''),IFNULL(POLICY_TAGGING,''),IFNULL(POLICY_UPDATED_ON,'') from POLICIES ORDER BY POLICY_UPDATED_ON DESC LIMIT 1";

		return jdbcTemplate.query(query, new RowMapper<Policy>() {
			public Policy mapRow(ResultSet rs, int rownumber) throws SQLException {
				Policy e = new Policy();

				e.setPolicyContent(rs.getString(1));
				e.setPolicyTagging(rs.getString(2));
				e.setPolicyUpdatedOn(rs.getString(3));
				return e;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IPolicyDAO#updatePolicyFlag(com.automation.vo.Policy)
	 * This method will update policy flag in Agent Matser
	 */
	public int updatePolicyFlag(Policy e) {
		logger.info("inside updatePolicyFlag()");
		String query = "UPDATE AGENT_MASTER SET POLICY_UPDATE='false' WHERE EMAIL_ID='" + e.getEmailId() + "'";

		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IPolicyDAO#newPolicy(com.automation.model.Policy)
	 * This method will insert data in Policy Table
	 */
	public int newPolicy(com.automation.model.Policy e) {
		logger.info("inside new policy()");
		String query = "INSERT INTO POLICIES(`POLICY_CONTENT`,`POLICY_TAGGING`,`POLICY_UPDATED_ON`) VALUES('"
				+ e.getPolicyContent() + "','" + e.getPolicyTagging() + "','" + e.getPolicyUpdatedOn() + "')";
		return jdbcTemplate.update(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IPolicyDAO#updatePolicyFlagInAgentMaster() This
	 * method will update policy flag in Agent Matser
	 */
	public int updatePolicyFlagInAgentMaster() {
		logger.info("inside updatePolicyFlagInAgentMaster()");
		String query = "UPDATE AGENT_MASTER SET POLICY_UPDATE='true'";
		return jdbcTemplate.update(query);
	}

}
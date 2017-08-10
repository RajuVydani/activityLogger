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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.automation.idao.IAdminDAO;
import com.automation.idao.IAgentDAO;
import com.automation.model.Admin;
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
public class AdminDAO implements IAdminDAO {
	/**
	 * 
	 * 	AgentDAO()
	 */
	AdminDAO()
	{
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#getAgentData(com.automation.vo.Agent)
	 * This is Logger for printing logs
	 */
	private final static Logger LOGGER = Logger.getLogger(AdminDAO.class);
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

 
 
	public List<Admin> fetchUserTypelist() {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchPrevilegelist()");
		LOGGER.info("SELECT DISTINCT USER_TYPE FROM USER_PRIVILEGES ORDER BY USER_TYPE");
		  }
		return jdbcTemplate.query("SELECT DISTINCT USER_TYPE FROM USER_PRIVILEGES ORDER BY USER_TYPE", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setUserType(resultset.getString(1));
				 
					 

						return admin;
					}
				});

	}
	public List<Admin> fetchLocationlist() {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchLocationlist()");
		LOGGER.info("SELECT DISTINCT LOCATION_NAME FROM LOCATION ORDER BY LOCATION_NAME");
		  }
		return jdbcTemplate.query("SELECT DISTINCT LOCATION_NAME FROM LOCATION ORDER BY LOCATION_NAME", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setLocationName(resultset.getString(1));
				 
					 

						return admin;
					}
				});

	}
	
	public List<Admin> fetchProgramlist() {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchProgramlist()");
		LOGGER.info("SELECT DISTINCT PROGRAM_NAME FROM PROGRAM_MASTER ORDER BY PROGRAM_NAME");
		  }
		return jdbcTemplate.query("SELECT DISTINCT PROGRAM_NAME FROM PROGRAM_MASTER ORDER BY PROGRAM_NAME", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setProgramName(resultset.getString(1));
				 
					 

						return admin;
					}
				});

	}
	
	
	public List<Admin> fetchProjectlist() {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchProjectlist()");
		LOGGER.info("SELECT DISTINCT PROJECT_NAME FROM PROGRAM_MASTER ORDER BY PROJECT_NAME");
		  }
		return jdbcTemplate.query("SELECT DISTINCT PROJECT_NAME FROM PROGRAM_MASTER ORDER BY PROJECT_NAME", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setProjectName(resultset.getString(1));
				 
					 

						return admin;
					}
				});

	}
	
	
	public List<Admin> fetchSubProjectlist() {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchSubProjectlist()");
		LOGGER.info("SELECT DISTINCT SUB_PROJECT_NAME FROM PROGRAM_MASTER ORDER BY SUB_PROJECT_NAME");
		  }
		return jdbcTemplate.query("SELECT DISTINCT SUB_PROJECT_NAME FROM PROGRAM_MASTER ORDER BY SUB_PROJECT_NAME", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setSubProjectName(resultset.getString(1));
				 
					 

						return admin;
					}
				});

	}
	
	public int userInsertion(Admin admin) {
	 
		LOGGER.info("inside userInsertion()");
		String query = "insert into USER_DETAILS(`USER_ID`,`USER_NAME`, `PASSWORD`, `PRIVILEGES_ID`,`LOCATION_ID`,  `ACCESS_LEVEL`,`PROGRAM_ID`,`PROJECT_ID`,`SUB_PROJECT_ID`,`PASSWORD_FAILURE_COUNT`,`STATUS`) values('"
				+ admin.getUserId() + "','" +admin.getUsername() + "','" + admin.getPassword() + "','" + admin.getPriviledgeId()+ "','"
				+admin.getLocationId() + "','" +admin.getAccessLevel() + "','"+admin.getProgramId()+"','"+admin.getProjectId()+"','"+admin.getSubProjectId()+"',0,'Active')";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
	public List<Admin> fetchPrevilegeId(Admin admin) {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchPrevilegeId()");
		LOGGER.info("SELECT PRIVILEGES_ID FROM USER_PRIVILEGES WHERE USER_TYPE='"+admin.getUserType()+"'");
		  }
		return jdbcTemplate.query("SELECT PRIVILEGES_ID FROM USER_PRIVILEGES WHERE USER_TYPE='"+admin.getUserType()+"'", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setPriviledgeId(resultset.getString(1));
				 
					 

						return admin;
					}
				});

	}
	
	public int userUpdation(Admin admin) {
		 
		LOGGER.info("inside userInsertion()");
		String query = "UPDATE USER_DETAILS SET USER_NAME='" +admin.getUsername().trim() + "',PRIVILEGES_ID='"+ admin.getPriviledgeId().trim()+ "',LOCATION_ID='"
				+admin.getLocationId() + "' WHERE USER_ID='"+admin.getUserId().trim()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
 
	public int userDeletion(Admin admin) {
		 
		LOGGER.info("inside userInsertion()");
		String query = "DELETE FROM USER_DETAILS WHERE USER_ID='"+admin.getUserId().trim()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
	
	public int programTransactionDeletion(Admin admin) {
		 
		LOGGER.info("inside programTransactionDeletion()");
		String query = "DELETE FROM PROGRAM_TRANSACTION WHERE PROGRAM_MANAGER_ID='"+admin.getUserId().trim()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
	
	public int projectTransactionDeletion(Admin admin) {
		 
		LOGGER.info("inside projectTransactionDeletion()");
		String query = "DELETE FROM PROJECT_TRANSACTION WHERE PROJECT_MANAGER_ID='"+admin.getUserId().trim()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
	
	public int subProjectTransactionDeletion(Admin admin) {
		 
		LOGGER.info("inside subProjectTransactionDeletion()");
		String query = "DELETE FROM SUB_PROJECT_TRANSACTION WHERE SUPERVISOR_ID='"+admin.getUserId().trim()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
	public List<Admin> getUserType(Admin admin) {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchUserType()");
		LOGGER.info("SELECT U.USER_ID,U.USER_NAME,P.USER_TYPE,U.LOCATION_ID FROM USER_DETAILS U, USER_PRIVILEGES P WHERE U.PRIVILEGES_ID=P.PRIVILEGES_ID AND USER_ID='"+admin.getUserId().trim()+"'");
		  }
		return jdbcTemplate.query("SELECT USER_TYPE FROM USER_PRIVILEGES WHERE PRIVILEGES_ID='"+admin.getPriviledgeId().trim()+"'", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setUserType(resultset.getString(1));
				 
					 

						return admin;
					}
				});

	}
	
	public List<Admin> FetchEmailIds(String criteria) {
		String where="";
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside FetchEmailIds()");
				if(criteria.trim().equalsIgnoreCase("program"))
				{
					where=" ALLOCATION_LEVEL='program'";
				}
				if(criteria.trim().equalsIgnoreCase("project"))
				{
					where=" ALLOCATION_LEVEL='project'";
				}
				if(criteria.trim().equalsIgnoreCase("subproject"))
				{
					where=" ALLOCATION_LEVEL='subproject'";
				}
		LOGGER.info("SELECT USER_ID FROM USER_DETAILS WHERE "+where);
		  }
		return jdbcTemplate.query("SELECT USER_ID FROM USER_DETAILS WHERE "+where, new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setUserId(resultset.getString(1));
				 
					 

						return admin;
					}
				});

	}
	
	public List<Admin> getuserDetails() {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchPrevilegeId()");
		LOGGER.info("SELECT U.USER_ID,U.USER_NAME,P.USER_TYPE,U.LOCATION_ID,U.ACCESS_LEVEL,U.PROGRAM_ID,U.PROJECT_ID,U.SUB_PROJECT_ID FROM USER_DETAILS U, USER_PRIVILEGES P WHERE U.PRIVILEGES_ID=P.PRIVILEGES_ID");
		  }
		return jdbcTemplate.query("SELECT U.USER_ID,U.USER_NAME,P.USER_TYPE,U.LOCATION_ID,U.ACCESS_LEVEL,U.PROGRAM_ID,U.PROJECT_ID,U.SUB_PROJECT_ID FROM USER_DETAILS U, USER_PRIVILEGES P WHERE U.PRIVILEGES_ID=P.PRIVILEGES_ID", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setUserId(resultset.getString(1));
						admin.setUsername(resultset.getString(2));
						admin.setUserType(resultset.getString(3));
						admin.setLocationId(resultset.getString(4));
						admin.setAccessLevel(resultset.getString(5));
						admin.setProgramId(resultset.getString(6));
						admin.setProjectId(resultset.getString(7));
						admin.setSubProjectId(resultset.getString(8));
						return admin;
					}
				});

	}
	
	public List<Admin> getuserDetails(String emailId) {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside fetchPrevilegeId()");
		LOGGER.info("SELECT U.USER_ID,U.USER_NAME,P.USER_TYPE,U.LOCATION_ID FROM USER_DETAILS U, USER_PRIVILEGES P WHERE U.PRIVILEGES_ID=P.PRIVILEGES_ID AND USER_ID='"+emailId.trim()+"'");
		  }
		return jdbcTemplate.query("SELECT U.USER_ID,U.USER_NAME,P.USER_TYPE,U.LOCATION_ID FROM USER_DETAILS U, USER_PRIVILEGES P WHERE U.PRIVILEGES_ID=P.PRIVILEGES_ID AND USER_ID='"+emailId.trim()+"'", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setUserId(resultset.getString(1));
						admin.setUsername(resultset.getString(2));
						admin.setUserType(resultset.getString(3));
						admin.setLocationId(resultset.getString(4));
				  
						return admin;
					}
				});

	}
	
	public List<Admin> getProjectList(String programName) {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside getProjectList()");
		LOGGER.info("SELECT DISTINCT PROJECT_ID,PROGRAM_ID,PROGRAM_DESC FROM PROGRAM_MASTER WHERE PROGRAM_NAME='"+programName.trim()+"'");
		  }
		return jdbcTemplate.query("SELECT DISTINCT PROJECT_ID,PROGRAM_ID,PROGRAM_DESC  FROM PROGRAM_MASTER WHERE PROGRAM_NAME='"+programName.trim()+"'", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setProjectId(resultset.getString(1));
						admin.setProgramId(resultset.getString(2));
						admin.setProgramDesc(resultset.getString(3));
						 
				  
						return admin;
					}
				});

	}
	
	public List<Admin> getProjectDetails(String projectName) {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside getProjectDetails()");
		LOGGER.info("SELECT DISTINCT PROJECT_ID,PROJECT_DESC,LOCATION_ID FROM PROGRAM_MASTER WHERE PROJECT_NAME='"+projectName.trim()+"'");
		  }
		return jdbcTemplate.query("SELECT PROJECT_ID,PROJECT_DESC,LOCATION_ID FROM PROGRAM_MASTER WHERE PROJECT_NAME='"+projectName.trim()+"'", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setProjectId(resultset.getString(1));
						admin.setProjectDesc(resultset.getString(2));
						admin.setLocationId(resultset.getString(3));
				  
						return admin;
					}
				});

	}
	 
	public List<Admin> getSubProjectDetails(String subProjectName) {
		  if (LOGGER.isInfoEnabled()) {
				LOGGER.info("inside getSubProjectDetails()");
		LOGGER.info("SELECT DISTINCT PROJECT_ID,SUB_PROJECT_ID,SUB_PROJECT_DESC,LOCATION_ID FROM PROGRAM_MASTER WHERE SUB_PROJECT_NAME='"+subProjectName.trim()+"'");
		  }
		return jdbcTemplate.query("SELECT PROJECT_ID,SUB_PROJECT_ID,SUB_PROJECT_DESC,LOCATION_ID FROM PROGRAM_MASTER WHERE SUB_PROJECT_NAME='"+subProjectName.trim()+"'", new RowMapper<Admin>() {
					public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
						Admin admin = new Admin();
						admin.setProjectId(resultset.getString(1));
						admin.setSubProjectId(resultset.getString(2));
						admin.setSubProjectDesc(resultset.getString(3));
						admin.setLocationId(resultset.getString(4));
						 
				  
						return admin;
					}
				});

	}
	
	
	public int programInsertion(Admin admin) {
		 
		LOGGER.info("inside programInsertion()");
		String query = "insert into PROGRAM_TRANSACTION(`PROGRAM_ID`, `PROGRAM_NAME`, `PROGRAM_MANAGER_ID`,`PROGRAM_MANAGER_NAME`,`PROGRAM_DESC`,`PROJECT_ID`) values('"
				+ admin.getProgramId() + "','" +admin.getProgramName() + "','" + admin.getProgramManagerId() + "','" + admin.getProgramManagerName()+ "','"
				+admin.getProgramDesc() + "','" +admin.getProjectId()+ "')";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
	
	public int projectInsertion(Admin admin) {
		  
		LOGGER.info("inside projectInsertion()");
		String query = "insert into PROJECT_TRANSACTION( `PROJECT_ID`, `PROJECT_NAME`,`PROJECT_MANAGER_ID`,`PROJECT_MANAGER_NAME`,`PROJECT_DESC`,`LOCATION_ID`) values('"
				+ admin.getProjectId()+ "','" +admin.getProjectName() + "','" + admin.getProjectManagerId() + "','" + admin.getProjectManagerName() +"','"
				+admin.getProjectDesc() + "','" +admin.getLocationId()+ "')";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
	
	 
	public int subProjectInsertion(Admin admin) {
		  
		LOGGER.info("inside subProjectInsertion()");
		String query = "insert into SUB_PROJECT_TRANSACTION(  `SUB_PROJECT_ID`, `SUB_PROJECT_NAME`,`SUPERVISOR_ID`,`SUPERVISOR_NAME`,`PROJECT_ID`,`SUB_PROJECT_DESC`,`LOCATION_ID`) values('"
				+ admin.getSubProjectId()+ "','" +admin.getSubProjectName() + "','" + admin.getSupervisorId() + "','" + admin.getSupervisorName() +"','"+admin.getProjectId()+"','"
				+admin.getSubProjectDesc() + "','" +admin.getLocationId()+ "')";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.update(query);
	}
	
	public int checkUserIdExsist(Admin admin) {
		  
		LOGGER.info("inside checkUserIdExsist()");
		String query = "SELECT COUNT(*) FROM USER_DETAILS WHERE USER_ID='"+admin.getUserId()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.queryForInt(query);
	}
	
	public int checkProgramExsist(Admin admin) {
		  
		LOGGER.info("inside checkProgramExsist()");
		String query = "SELECT COUNT(*) FROM PROGRAM_TRANSACTION WHERE PROGRAM_NAME='"+admin.getProgramName()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.queryForInt(query);
	}
	
	public int checkProjectExsist(Admin admin) {
		  
		LOGGER.info("inside checkProjectExsist()");
		String query = "SELECT COUNT(*) FROM PROJECT_TRANSACTION WHERE PROJECT_NAME='"+admin.getProjectName()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.queryForInt(query);
	}
	
	public int checkSubProjectExsist(Admin admin) {
		  
		LOGGER.info("inside checkSubProjectExsist()");
		String query = "SELECT COUNT(*) FROM SUB_PROJECT_TRANSACTION WHERE SUB_PROJECT_NAME='"+admin.getProjectName()+"' AND SUPERVISOR_ID='"+admin.getUserId()+"'";
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("query==" + query);
		  }
		return jdbcTemplate.queryForInt(query);
	}
}

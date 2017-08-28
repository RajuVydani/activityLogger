package com.automation.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
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

	@Value("${select.PreviledgeId}")
	private String selectPreviledgeId;
	public List<Admin> getPreviledgeId(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getPreviledgeId()");
			
		}
		return jdbcTemplate.query(selectPreviledgeId,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getUserId()));
                
            }
        }, new RowMapper<Admin>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setPriviledgeId(resultset.getString(1));
				admin.setViewOnly(resultset.getString(2));
				admin.setPdfExport(resultset.getString(3));
				admin.setCsvExport(resultset.getString(4));
				admin.setExcelExport(resultset.getString(5));
				admin.setPrintOption(resultset.getString(6));
				

				return admin;
			}
		});
	}

	

	
	
	@Value("${select.HCMSupervisor}")
	private String selectHCMSupervisor;
	public List<Admin> getSupervisor(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getSupervisor()");
			
		}
		return jdbcTemplate.query(selectHCMSupervisor,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	 preparedStatement.setObject(1, admin.getSupervisorList());
           
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setUserId(resultset.getString(1));
				

				return admin;
			}
		});
	}

	
	@Value("${select.MntDataSubProLvl}")
	private String selectMntDataSubProLvl;
	public List<Admin> getMntDataSubProLvl(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getMntDataSubProLvl()");
			
		}
		return jdbcTemplate.query(selectMntDataSubProLvl,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getMonth()));
                preparedStatement.setInt(2, Integer.parseInt(admin.getYear()));
                preparedStatement.setObject(3, admin.getSupervisorList());
           
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setProdSum(resultset.getString(1));
				admin.setIdleSum(resultset.getString(2));
				admin.setBreakSum(resultset.getString(3));
				admin.setMealsSum(resultset.getString(4));
				admin.setHuddleSum(resultset.getString(5));
				admin.setFbTrainingSum(resultset.getString(6));
				admin.setTeamMeetingSum(resultset.getString(7));
				admin.setCoachingSum(resultset.getString(8));
				admin.setWelnessSupportSum(resultset.getString(9));
				admin.setProjectId(resultset.getString(10));
				admin.setSubProjectId(resultset.getString(11));
				admin.setProjectName(resultset.getString(12));
				admin.setSubProjectName(resultset.getString(13));
				admin.setLocationId(resultset.getString(14));
				
				

				return admin;
			}
		});
	}

	@Value("${select.MntCountSubProLvl}")
	private String selectMntCountSubProLvl;
	public List<Admin> getMntCountSubProLvl(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getMntCountSubProLvl()");
			
		}
		return jdbcTemplate.query(selectMntCountSubProLvl,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getMonth()));
                preparedStatement.setInt(2, Integer.parseInt(admin.getYear()));
                preparedStatement.setObject(3, admin.getSupervisorList());
               
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setAgentCount(resultset.getString(1));

				return admin;
			}
		});
	}
	

	@Value("${select.MntDataSpecificSubProLvl}")
	private String selectMntDataSpecificSubProLvl;
	public List<Admin> getMntDataSpecificSubProLvl(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getMntDataSpecificSubProLvl()");
			
		}
		return jdbcTemplate.query(selectMntDataSpecificSubProLvl,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getMonth()));
                preparedStatement.setInt(2, Integer.parseInt(admin.getYear()));
                preparedStatement.setInt(3, Integer.parseInt(admin.getSubProjectId()));
                preparedStatement.setString(4,admin.getLocationId());
           
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setProdSum(resultset.getString(1));
				admin.setIdleSum(resultset.getString(2));
				admin.setBreakSum(resultset.getString(3));
				admin.setMealsSum(resultset.getString(4));
				admin.setHuddleSum(resultset.getString(5));
				admin.setFbTrainingSum(resultset.getString(6));
				admin.setTeamMeetingSum(resultset.getString(7));
				admin.setCoachingSum(resultset.getString(8));
				admin.setWelnessSupportSum(resultset.getString(9));
				admin.setProjectId(resultset.getString(10));
				admin.setSubProjectId(resultset.getString(11));
				admin.setProjectName(resultset.getString(12));
				admin.setSubProjectName(resultset.getString(13));
				admin.setLocationId(resultset.getString(14));
				
				

				return admin;
			}
		});
	}

	@Value("${select.MntCountSpecificSubProLvl}")
	private String selectMntCountSpecificSubProLvl;
	public List<Admin> getMntCountSpecificSubProLvl(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getMntCountSpecificSubProLvl()");
			
		}
		return jdbcTemplate.query(selectMntCountSpecificSubProLvl,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getMonth()));
                preparedStatement.setInt(2, Integer.parseInt(admin.getYear()));
                preparedStatement.setInt(3, Integer.parseInt(admin.getSubProjectId()));
                preparedStatement.setString(4,admin.getLocationId());
               
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setAgentCount(resultset.getString(1));

				return admin;
			}
		});
	}

	
	

	@Value("${select.MntDataSpecificProLvl}")
	private String selectMntDataSpecificProLvl;
	public List<Admin> getMntDataSpecificProLvl(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getMntDataSpecificProLvl()");
			
		}
		return jdbcTemplate.query(selectMntDataSpecificProLvl,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getMonth()));
                preparedStatement.setInt(2, Integer.parseInt(admin.getYear()));
                preparedStatement.setInt(3, Integer.parseInt(admin.getProjectId()));
                preparedStatement.setString(4,admin.getLocationId());
           
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setProdSum(resultset.getString(1));
				admin.setIdleSum(resultset.getString(2));
				admin.setBreakSum(resultset.getString(3));
				admin.setMealsSum(resultset.getString(4));
				admin.setHuddleSum(resultset.getString(5));
				admin.setFbTrainingSum(resultset.getString(6));
				admin.setTeamMeetingSum(resultset.getString(7));
				admin.setCoachingSum(resultset.getString(8));
				admin.setWelnessSupportSum(resultset.getString(9));
				admin.setProjectId(resultset.getString(10));
				admin.setSubProjectId(resultset.getString(11));
				admin.setProjectName(resultset.getString(12));
				admin.setSubProjectName(resultset.getString(13));
				admin.setLocationId(resultset.getString(14));
				
				

				return admin;
			}
		});
	}

	@Value("${select.MntCountSpecificProLvl}")
	private String selectMntCountSpecificProLvl;
	public List<Admin> getMntCountSpecificProLvl(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getMntCountSpecificProLvl()");
			
		}
		return jdbcTemplate.query(selectMntCountSpecificProLvl,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getMonth()));
                preparedStatement.setInt(2, Integer.parseInt(admin.getYear()));
                preparedStatement.setInt(3, Integer.parseInt(admin.getSubProjectId()));
                preparedStatement.setString(4,admin.getLocationId());
               
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setAgentCount(resultset.getString(1));

				return admin;
			}
		});
	}

	
	@Value("${select.MntDataLoc}")
	private String selectMntDataLoc;
	public List<Admin> getMntDataLoc(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getMntDataLoc()");
			
		}
		return jdbcTemplate.query(selectMntDataLoc,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getMonth()));
                preparedStatement.setInt(2, Integer.parseInt(admin.getYear()));
            //    preparedStatement.setInt(3, Integer.parseInt(admin.getProjectId()));
                preparedStatement.setString(4,admin.getLocationId());
           
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setProdSum(resultset.getString(1));
				admin.setIdleSum(resultset.getString(2));
				admin.setBreakSum(resultset.getString(3));
				admin.setMealsSum(resultset.getString(4));
				admin.setHuddleSum(resultset.getString(5));
				admin.setFbTrainingSum(resultset.getString(6));
				admin.setTeamMeetingSum(resultset.getString(7));
				admin.setCoachingSum(resultset.getString(8));
				admin.setWelnessSupportSum(resultset.getString(9));
				admin.setProjectId(resultset.getString(10));
				admin.setSubProjectId(resultset.getString(11));
				admin.setProjectName(resultset.getString(12));
				admin.setSubProjectName(resultset.getString(13));
				admin.setLocationId(resultset.getString(14));
				
				

				return admin;
			}
		});
	}

	@Value("${select.MntCountLoc}")
	private String selectMntCountLoc;
	public List<Admin> getMntCountLoc(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getMntCountLoc()");
			
		}
		return jdbcTemplate.query(selectMntCountLoc,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getMonth()));
                preparedStatement.setInt(2, Integer.parseInt(admin.getYear()));
              //  preparedStatement.setInt(3, Integer.parseInt(admin.getSubProjectId()));
                preparedStatement.setString(4,admin.getLocationId());
               
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setAgentCount(resultset.getString(1));

				return admin;
			}
		});
	}
	
	@Value("${select.ProjectId}")
	private String selectProjectId;
	public List<Admin> getProjectId(Admin admin) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getProjectId()");
			
		}
		return jdbcTemplate.query(selectProjectId,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(admin.getProjectManagerId()));
              
               
            }
        }, new RowMapper<Admin>() {
			/*rs
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Admin mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Admin admin = new Admin();
				admin.setProgramId(resultset.getString(1));
				admin.setProgramName(resultset.getString(2));
				admin.setProjectId(resultset.getString(3));
				admin.setProjectName(resultset.getString(4));
				admin.setSubProjectId(resultset.getString(5));
				admin.setSubProjectName(resultset.getString(6));
				admin.setLocationId(resultset.getString(7));
		 

				return admin;
			}
		});
	}
}

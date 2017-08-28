package com.automation.dao;

import java.io.Serializable;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
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

	@Value("${select.Exception}")
	private String selectException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#readExceptionTable(java. lang.String)
	 * This method will fetch Agent Transactions From Chrome Exception Table
	 */
	public List<Agent> readExceptionTable(String emailid) {
		

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("calling readExceptionTable()");
			
		}
		
		
		
		return jdbcTemplate.query(selectException,
        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, emailid.trim());
            }
        }
        , new RowMapper<Agent>() {
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

	@Value("${select.Temporary}")
	private String selectTemporary;

	
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#readTemporaryTable()
	 * This method will fetch agent transactions from Chrome Temp Table
	 */
	public List<Agent> readTemporaryTable() {
		
	 

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("calling readTemporaryTable()");
			
		}
		return jdbcTemplate.query(selectTemporary, new RowMapper<Agent>() {
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

	@Value("${select.ExceptionAgentIds}")
	private String selectExceptionAgentIds;
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#readExceptionTableAgentIds() This
	 * method will fetch Agent Email Ids From Chrome Exception Table
	 */
	public List<Agent> readExceptionTableAgentIds() {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("calling readExceptionTableAgentIds()");
			
		}
		return jdbcTemplate.query(selectExceptionAgentIds, new RowMapper<Agent>() {
		
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(resultset.getString(1));

				return e;
			}
		});
	}

	@Value("${select.AgentMaster}")
	private String selectAgentMaster;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#readAgentDetails(java.lang. String)
	 * This method will fetch Agent details From Agent Master
	 */
	public List<Agent> readAgentDetails(String emailId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("calling readAgentDetails()");
			
		}
		return jdbcTemplate.query(selectAgentMaster,
		        new PreparedStatementSetter() {
		            public void setValues(PreparedStatement preparedStatement) throws SQLException {
		                preparedStatement.setString(1, emailId.trim());
		            }
		        }
		        , new RowMapper<Agent>() {
							/*
							 * (non-Javadoc)
							 * 
							 * @see
							 * org.springframework.jdbc.core.RowMapper#mapRow(
							 * java.sql. ResultSet, int)
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

	
	
	@Value("${insert.DayMaster}")
	private String insertDayMaster;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#dayMasterInsert(com.automation.vo.
	 * Agent) This method will insert data in Day Master
	 */
	public int dayMasterInsert(Agent e) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("calling dayMasterInsert()");
		}
		return jdbcTemplate.update(insertDayMaster,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setString(1, e.getDATE());
                preparedStatement.setString(2, e.getEmailId());
                preparedStatement.setString(3, e.getName());
                preparedStatement.setString(4, e.getShiftTimings());
                preparedStatement.setString(5, e.getLoginTime());
                preparedStatement.setString(6, e.getLogoutTime());
                preparedStatement.setString(7, e.getLocation());
                preparedStatement.setInt(8, Integer.parseInt(e.getHcmSupervisorId()));
                preparedStatement.setString(9, e.getHcmSupervisorName());
                preparedStatement.setInt(10, Integer.parseInt(e.getProjectId()));
                preparedStatement.setString(11, e.getBillable());
                preparedStatement.setString(12, e.getOnshoreOffshore());
                preparedStatement.setInt(13, Integer.parseInt(e.getAgentId()));
                preparedStatement.setInt(14, Integer.parseInt(e.getSubProjectId()));
                preparedStatement.setString(15, e.getProjectName());
                preparedStatement.setString(16, e.getSubProjectName());
                
                
                
                
            }
        });
	}

	@Value("${update.DayMaster}")
	private String updateDayMaster;
	
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#dayMasterUpdate(com.automation.vo.Agent)
	 * This method will update data Day Master table.
	 */
	public int dayMasterUpdate(Agent e) {
		
	 
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dayMasterUpdate()");
		}
		return jdbcTemplate.update(updateDayMaster,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setString(1, e.getLogoutTime());
            	
            	
            	preparedStatement.setString(1, e.getLogoutTime());
            	preparedStatement.setFloat(2, Float.parseFloat(e.getProdSum()));
            	preparedStatement.setFloat(3, Float.parseFloat(e.getIdleSum()));
            	preparedStatement.setFloat(4, Float.parseFloat(e.getBreakSum()));
            	preparedStatement.setFloat(5, Float.parseFloat(e.getMealsSum()));
            	preparedStatement.setFloat(6, Float.parseFloat(e.getHuddleSum()));
            	preparedStatement.setFloat(7, Float.parseFloat(e.getWelnessSupportSum()));
            	preparedStatement.setFloat(8, Float.parseFloat(e.getCoachingSum()));
            	preparedStatement.setFloat(9, Float.parseFloat(e.getTeamMeetingSum()));
            	preparedStatement.setFloat(10, Float.parseFloat(e.getFbTrainingSum()));
            	preparedStatement.setFloat(11, Float.parseFloat(e.getNonFbTrainingSum()));
            	preparedStatement.setString(12, e.getEmailId().trim());
                preparedStatement.setString(13, e.getDATE());
            
                
                
            }
        });
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#calculateTempActiviyHrs(com.automation.vo.
	 * Agent) This method will calculate Activity Hrs From Day detail Table
	 */
	
	@Value("${select.ActivityHours}")
	private String selectActivityHours;
	public List<Agent> activityHrsCalculation(Agent agent) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside calculateTempActiviyHrs()");

		}

		return jdbcTemplate
				.query(selectActivityHours,
		        new PreparedStatementSetter() {
		            public void setValues(PreparedStatement preparedStatement) throws SQLException {
		                preparedStatement.setString(1, agent.getEmailId().trim());
		                preparedStatement.setString(2, agent.getFromDate());
		                preparedStatement.setString(3, agent.getToDate());
		            }
		        }
		        , new RowMapper<Agent>() {
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
	 * @see com.automation.idao.IAgentDAO#temporaryTableDelete(com.automation.
	 * vo.Agent) 
	 * This method will delete data from Chrome Temp Detail.
	 */
	
	@Value("${delete.Temporary}")
	private String deleteTemporary;
	public int temporaryTableDelete(Agent e) {

		 
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside temporaryTableDelete()");
			
		}
		return jdbcTemplate.update(deleteTemporary,    new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            
            	
            	
            	preparedStatement.setString(1, e.getEmailId());
            	preparedStatement.setString(2, e.getFromDate());
            
                
                
            }
        });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#exceptionTableInsert(com.automation.vo.
	 * Agent) This method will insert data in Chrome Exception Table.
	 */
	
	@Value("${insert.Exception}")
	private String insertException;
	public int exceptionTableInsert(Agent agent) {

 
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside exceptionTableInsert()");
		

		}
		return jdbcTemplate.update(insertException,    new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            
            	preparedStatement.setString(1, agent.getEmailId());
            	preparedStatement.setString(2, agent.getFromDate());
            	preparedStatement.setString(3, agent.getToDate());
            	preparedStatement.setString(4, agent.getWebsitesVisited());
            	preparedStatement.setInt(5, Integer.parseInt(agent.getActivityCode()));
            	preparedStatement.setString(6, agent.getErrorDesc());
            
                
                
            }
        });
	}

	@Value("${insert.TemporaryBkp}")
	private String insertTemporaryBkp;
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#temporaryBkpTableInsert(com.automation.vo.Agent)
	 * This method will insert data in Chrome Temp Back Up Table.
	 */
	public int temporaryBkpTableInsert(Agent agent) {

 
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside temporaryBkpTableInsert()");
		

		}
		return jdbcTemplate.update(insertTemporaryBkp,    new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            
            	preparedStatement.setString(1, agent.getEmailId());
            	preparedStatement.setString(2, agent.getFromDate());
            	preparedStatement.setString(3, agent.getToDate());
            	preparedStatement.setString(4, agent.getWebsitesVisited());
            	preparedStatement.setInt(5, Integer.parseInt(agent.getActivityCode()));
            	
            
                
                
            }
        });
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dataInsertionInDayDetailFromTempDetails(com
	 * .automation.vo.Agent)
	 *  This method will insert data in Day Detail Table
	 */
	@Value("${insert.DayDetail}")
	private String insertDayDetail;
	public int dayDetailInsert(Agent agent) {


		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dayDetailInsert()");
			
		}

		return jdbcTemplate.update(insertDayDetail,    new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setString(1, agent.getDATE());
            	preparedStatement.setInt(2, agent.getRownum());
            	preparedStatement.setString(3, agent.getEmailId());
            	preparedStatement.setString(4, agent.getFromDate());
            	preparedStatement.setString(5, agent.getToDate());
            	preparedStatement.setString(6, agent.getWebsitesVisited());
            	preparedStatement.setInt(7, Integer.parseInt(agent.getActivityCode()));
            	
            
                
                
            }
        });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#fetchShiftDetails(com.automation.vo.Agent)
	 * This method will check Shift Timings
	 */
	@Value("${select.ShiftTimings}")
	private String selectShiftTimings;
	public List<Agent> fetchShiftDetails(Agent agent) {
		String location = agent.getLocation();


		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside fetchShiftDetails()");

	
		}
		return jdbcTemplate.query(selectShiftTimings,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, agent.getStartTime());
                preparedStatement.setString(2, agent.getStartTime());
                preparedStatement.setString(3, location);
                if(agent.getProjectId().trim().equalsIgnoreCase(""))
                {
                	preparedStatement.setInt(4, 0);
                }
                else
                {
                	preparedStatement.setInt(4, Integer.parseInt(agent.getProjectId()));	
                	
                }
                
                if(agent.getProjectId().trim().equalsIgnoreCase(""))
                {
                	preparedStatement.setInt(5, 0);
                }
                else
                {
                	
                	preparedStatement.setInt(5, Integer.parseInt(agent.getSubProjectId()));
                }
                
                
            }
        }, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent agent = new Agent();

				agent.setShiftFrom(resultset.getString(1));
				agent.setShiftTo(resultset.getString(2));

				return agent;
			}
		});
	}
	
	@Value("${select.ProjectDetails}")
	private String selectProjectDetails;
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#fetchProjectDetails(com.automation.vo.Agent)
	 * This method will fetch project details.
	 */
	public List<Agent> fetchProjectDetails(Agent agent) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside fetchProjectDetails()");

		
		}
		return jdbcTemplate.query(selectProjectDetails,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, agent.getEmailId().trim());
               
            }
        }, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent agent = new Agent();

				agent.setProjectId(resultset.getString(1));
				agent.setSubProjectId(resultset.getString(2));
				agent.setLocation(resultset.getString(3));

				return agent;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#exceptionTableDelete(com.automation.vo
	 * .Agent) 
	 * This method will delete data from Chrome Exception Table.
	 */
	@Value("${delete.Exception}")
	private String deleteException;
	public int exceptionTableDelete(Agent agent) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside exceptionTableDelete()");
			
		}
		return jdbcTemplate.update(deleteException,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, agent.getEmailId().trim());
                preparedStatement.setString(2, agent.getFromDate());
               
            }
        });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#TemporaryTableInsert(com.automation
	 * .vo.Agent) This method will insert data in Chrome Temp Detail
	 */
	@Value("${insert.Temporary}")
	private String insertTemporary;
	
	public int TemporaryTableInsert(Agent agent) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside TemporaryTableInsert()");
			

		}
		return jdbcTemplate.update(insertTemporary,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, agent.getEmailId().trim());
                preparedStatement.setString(2, agent.getFromDate());
                preparedStatement.setString(3, agent.getToDate());
                preparedStatement.setString(4, agent.getWebsitesVisited());
                preparedStatement.setInt(5, Integer.parseInt(agent.getActivityCode()));
               
            }
        });
	}

	@Value("${update.DayDetail}")
	private String updateDayDetail;
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#dayDetailUpdate(com.automation.vo.Agent)
	 * This method will update data in Day Detail table.
	 */
	public int dayDetailUpdate(Agent agent) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dayDetailUpdate()");
		

		}
		return jdbcTemplate.update(updateDayDetail,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, agent.getToDate());
                preparedStatement.setString(2, agent.getEmailId().trim());
                preparedStatement.setString(3, agent.getDATE());
                preparedStatement.setString(4, agent.getFromDate());
                preparedStatement.setInt(5, Integer.parseInt(agent.getActivityCode()));
               
            }
        });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#idleInterval() This method will fetch
	 * This method will fetch the idle interval.
	 */
	@Value("${select.IdleInterval}")
	private String selectIdleInterval;
	public List<Agent> idleInterval(Agent agent) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside idleInterval");
			
		}
		return jdbcTemplate.query(selectIdleInterval,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setInt(1, Integer.parseInt(agent.getProjectId()));
            	preparedStatement.setInt(2, Integer.parseInt(agent.getSubProjectId()));
                preparedStatement.setString(3, agent.getLocation());
             
               
            }
        }, new RowMapper<Agent>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.
			 * ResultSet, int)
			 */
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent agent = new Agent();

				agent.setIdleInterval(resultset.getInt(1));

				return agent;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.automation.idao.IAgentDAO#dayDetailCount(com.automation.vo.Agent)
	 * This method will get the count in day detail table.
	 */
	
	@Value("${select.DayDetailCount}")
	private String selectDayDetailCount;
	
	public int dayDetailCount(Agent agent) {
	

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dayDetailCount()");
			
		}
		return jdbcTemplate.queryForInt(selectDayDetailCount,
				new Object[] { agent.getDATE(),agent.getEmailId().trim()});
		   
	}

	/**
	 * @param agent
	 * @return
	 */
	@Value("${select.DayDetailActivityCount}")
	private String selectDayDetailActivityCount;
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#dayDetailLastActivity(com.automation.vo.Agent)
	 * This method will fetch the last activity from Day Detail table.
	 */
	public int dayDetailLastActivity(Agent agent) {
		

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dayDetailLastActivity()");
			
		}
		return jdbcTemplate.queryForInt(selectDayDetailActivityCount,
				new Object[] { agent.getDATE(),agent.getEmailId().trim(),
						agent.getToDate(),agent.getActivityCode()});
		   
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#dayMasterCount(com.automation.
	 * vo.Agent)
	 * This method will get the count in day master table.
	 */
	@Value("${select.DayMasterCount}")
	private String selectDayMasterCount;
	
	public int dayMasterCount(Agent agent) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside dayMasterCount()");
			
		}
		return jdbcTemplate.queryForInt(selectDayMasterCount,
			 
                new Object[] { agent.getDATE(), agent.getEmailId().trim()});
	}
  

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#dayMasterPreviousDayLogout(com.
	 * automation.vo.Agent)
	 * This method will get the previous day logout Time.
	 */
	@Value("${select.DayMasterLogOut}")
	private String selectDayMasterLogOut;
	
	public List<Agent> dayMasterPreviousDayLogout(Agent agent) {

		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside dayMasterPreviousDayLogout()");
			
		}
		return jdbcTemplate.query(selectDayMasterLogOut,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setString(1, agent.getDATE());
                preparedStatement.setString(2, agent.getEmailId().trim());
           
             
               
            }
        }, new RowMapper<Agent>() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.automation.idao.IAgentDAO#getLoginTime(com.automation.vo. Agent)
	 * This method will get Login Time from Day Master table.
	 */
	@Value("${select.DayMasterLogIn}")
	private String selectDayMasterLogIn;
	public List<Agent> getLoginTime(Agent agent) {
		
		if (LOGGER.isInfoEnabled()) {

			LOGGER.info("inside getLoginTime()");
			
		}
		return jdbcTemplate.query(selectDayMasterLogIn,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	preparedStatement.setString(1, agent.getDATE());
                preparedStatement.setString(2, agent.getEmailId().trim());
           
             
               
            }
        }, new RowMapper<Agent>() {
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

	@Value("${select.MonthMasterCount}")
	private String selectMonthMasterCount;
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#monthMasterCount(com.automation.vo.Agent)
	 * This method will get the count from month master.
	 */
	public int monthMasterCount(Agent agent) {

	
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside monthMasterCount()");
			
		}
		return jdbcTemplate.queryForInt(selectMonthMasterCount,
				new Object[] { agent.getAgentId(), agent.getMonth(),agent.getYear().trim()});
		   
	}

	@Value("${select.DayMaster}")
	private String selectDayMaster;
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#readDayMaster(com.automation.vo.Agent)
	 * This method will fetch data from day master.
	 */
	public List<Agent> readDayMaster(Agent agent) {
		

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside readDayMaster()");
		}
		return jdbcTemplate.query(selectDayMaster,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	
                preparedStatement.setInt(1,Integer.parseInt(agent.getMonth()));
                preparedStatement.setInt(2,Integer.parseInt(agent.getYear()));
           
             
               
            }
        }, new RowMapper<Agent>() {
			public Agent mapRow(ResultSet resultset, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setEmailId(resultset.getString(1));
				e.setAgentId(resultset.getString(2));
				e.setName(resultset.getString(3));
				e.setShiftTimings(resultset.getString(4));
				e.setProjectId(resultset.getString(5));
				e.setProjectName(resultset.getString(6));
				e.setSubProjectId(resultset.getString(7));
				e.setSubProjectName(resultset.getString(8));
				e.setLocation(resultset.getString(9));
				e.setBillable(resultset.getString(10));
				e.setOnshoreOffshore(resultset.getString(11));
				e.setHcmSupervisorId(resultset.getString(12));
				e.setHcmSupervisorName(resultset.getString(13));
				e.setProdSum(resultset.getString(14));
				e.setIdleSum(resultset.getString(15));
				e.setProdAvg(resultset.getString(16));
				e.setIdleAvg(resultset.getString(17));
				e.setBreakSum(resultset.getString(18));
				e.setMealsSum(resultset.getString(19));
				e.setHuddleSum(resultset.getString(20));
				e.setWelnessSupportSum(resultset.getString(21));
				e.setCoachingSum(resultset.getString(22));
				e.setTeamMeetingSum(resultset.getString(23));
				e.setFbTrainingSum(resultset.getString(24));
				e.setNonFbTrainingSum(resultset.getString(25));
				e.setMonth(resultset.getString(26));
				e.setYear(resultset.getString(27));

				return e;
			}
		});
	}
	@Value("${insert.MonthMaster}")
	private String insertMonthMaster;
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#monthMasterInsert(com.automation.vo.Agent)
	 * This method will insert data in month master.
	 */
	public int monthMasterInsert(Agent agent) {


		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside monthMasterInsert()");
			
		}

		return jdbcTemplate.update(insertMonthMaster,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	
                preparedStatement.setInt(1,Integer.parseInt(agent.getMonth()));
                preparedStatement.setInt(2,Integer.parseInt(agent.getYear()));
                preparedStatement.setString(3, agent.getEmailId().trim());
                preparedStatement.setInt(4, Integer.parseInt(agent.getAgentId()));
                preparedStatement.setString(5, agent.getName());
                preparedStatement.setString(6, agent.getShiftTimings());
                preparedStatement.setInt(7, Integer.parseInt(agent.getProjectId()));
                preparedStatement.setString(8, agent.getProjectName());
                preparedStatement.setInt(9, Integer.parseInt(agent.getSubProjectId()));
                preparedStatement.setString(10, agent.getSubProjectName());
                preparedStatement.setString(11, agent.getLocation());
                preparedStatement.setString(12, agent.getBillable());
                preparedStatement.setString(13, agent.getOnshoreOffshore());
                preparedStatement.setInt(14, Integer.parseInt(agent.getHcmSupervisorId()));
                preparedStatement.setString(15, agent.getHcmSupervisorName());
                preparedStatement.setFloat(16, Float.parseFloat(agent.getProdSum()));
                preparedStatement.setFloat(17, Float.parseFloat(agent.getIdleSum()));
                preparedStatement.setFloat(18, Float.parseFloat(agent.getProdAvg()));
                preparedStatement.setFloat(19, Float.parseFloat(agent.getIdleAvg()));
                preparedStatement.setFloat(20, Float.parseFloat(agent.getBreakSum()));
                preparedStatement.setFloat(21, Float.parseFloat(agent.getMealsSum()));
                preparedStatement.setFloat(22, Float.parseFloat(agent.getHuddleSum()));
                preparedStatement.setFloat(23, Float.parseFloat(agent.getWelnessSupportSum()));
                preparedStatement.setFloat(24, Float.parseFloat(agent.getCoachingSum()));
                preparedStatement.setFloat(25, Float.parseFloat(agent.getTeamMeetingSum()));
                preparedStatement.setFloat(26, Float.parseFloat(agent.getFbTrainingSum()));
                preparedStatement.setFloat(27, Float.parseFloat(agent.getNonFbTrainingSum()));
                
  
               
            }
        });
	}

	@Value("${update.MonthMaster}")
	private String updateMonthMaster;
	/* (non-Javadoc)
	 * @see com.automation.idao.IAgentDAO#monthMasterUpdate(com.automation.vo.Agent)
	 * This method will update month master table.
	 */
	public int monthMasterUpdate(Agent agent) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("inside monthMasterUpdate()");
			
		}

		return jdbcTemplate.update(updateMonthMaster,
		        new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            	
                preparedStatement.setFloat(1, Float.parseFloat(agent.getProdSum()));
                preparedStatement.setFloat(2, Float.parseFloat(agent.getIdleSum()));
                preparedStatement.setFloat(3, Float.parseFloat(agent.getProdAvg()));
                preparedStatement.setFloat(4, Float.parseFloat(agent.getIdleAvg()));
                preparedStatement.setFloat(5, Float.parseFloat(agent.getBreakSum()));
                preparedStatement.setFloat(6, Float.parseFloat(agent.getMealsSum()));
                preparedStatement.setFloat(7, Float.parseFloat(agent.getHuddleSum()));
                preparedStatement.setFloat(8, Float.parseFloat(agent.getWelnessSupportSum()));
                preparedStatement.setFloat(9, Float.parseFloat(agent.getCoachingSum()));
                preparedStatement.setFloat(10, Float.parseFloat(agent.getTeamMeetingSum()));
                preparedStatement.setFloat(11, Float.parseFloat(agent.getFbTrainingSum()));
                preparedStatement.setFloat(12, Float.parseFloat(agent.getNonFbTrainingSum()));
                preparedStatement.setInt(13, Integer.parseInt(agent.getAgentId()));
                preparedStatement.setInt(14,Integer.parseInt(agent.getMonth()));
                preparedStatement.setInt(15,Integer.parseInt(agent.getYear()));
                
                
               
            }
        });
	}

}

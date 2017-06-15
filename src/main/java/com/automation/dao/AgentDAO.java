package com.automation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.automation.idao.IAgentDAO;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;

public class AgentDAO implements IAgentDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getAgentData(Agent agent) throws Exception{
		System.out.println(AppConstants.CLASS + "-AgentDAO ::" + AppConstants.METHOD + "-storeAgentData()");
		
		//Query...
		String sql = "SELECT NAME FROM FIRSTTABLE";		
		
		if(null == jdbcTemplate) { 
			System.out.println("Could not connect to database !!!"); 
		}
		
		//Executing the query.
		jdbcTemplate.query(sql,new ResultSetExtractor<List<Agent>>() {  
		    public List<Agent> extractData(ResultSet rs) throws SQLException, DataAccessException {
		    	List<Agent>list = new ArrayList<Agent>();
		    	Agent agent;
		        while(rs.next()){  
		        	agent=new Agent();  
		        	agent.setName(rs.getString("NAME"));  
		        	list.add(agent);  
		        }
		        System.out.println(list);
		        return list;  
		    }  
		});
		return "Successfully";
	}
}
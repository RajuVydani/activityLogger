package com.automation.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.automation.exceptions.CustomException;
import com.automation.exceptions.ValidationException;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;
//Test url
//http://localhost:8082/TimeTracker/rest/agent/rajuV@cognizant.com
@Path("/agent")
public class TrackerService {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Hello : " + msg;
		return Response.status(200).entity(output).build();

	}
	//http://localhost:8082/TimeTracker/rest/agent/data
	@GET
	@Path("/data")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAgentData() {		
		String output = "Data";
		System.out.println(AppConstants.METHOD + "-getAgentData()");
		Agent agent = new Agent();
		agent.setName("Raju");
		agent.setEmailId("raju@gmail.com");
		
		return Response.status(200).entity(agent).build();
	}
	
	/**
	 * Stores Agent idle hours from extension.
	 * agentName, EmailId, (IdleHours)From, To, WebsitesUsed 
	 * sampleUrl :: http://localhost:8082/TimeTracker/rest/agent/
	 * @return 
	 * @throws ValidationException 
	 * @throws CustomException 
	 */
	@POST
	@Path("/{agentName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response storeAgentData(@PathParam("agentName") String agentName) throws ValidationException, CustomException  {
		System.out.println(AppConstants.METHOD + "-storeAgentData()");
		
		//Validations.
		if(null == agentName || "undefined".equalsIgnoreCase(agentName)) {				
			throw new ValidationException("Validation Exception :: Agent name is null");
		}
		
		try {
			if("error".equalsIgnoreCase(agentName)) {
				throw new NullPointerException();
			}
		} catch (Exception e) {		
			throw new CustomException();
		}
		return Response.status(200).entity(agentName).build();
		
	}

}

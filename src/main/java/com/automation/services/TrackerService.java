package com.automation.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.automation.dao.AgentDAO;
import com.automation.exceptions.CustomException;
import com.automation.exceptions.ValidationException;
import com.automation.idao.IAgentDAO;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;

//Test url
//http://localhost:8082/TimeTracker/rest/agent/rajuV@cognizant.com
@Path("/agent")
public class TrackerService {

	private IAgentDAO agentDAO;

	public void setAgentDAO(IAgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Hello : " + msg;
		return Response.status(200).entity(output).build();

	}

	// http://localhost:8082/TimeTracker/rest/agent/data
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

	@POST
	@Path("/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response storeData(Agent jsonagent) {
		System.out.println(AppConstants.METHOD + "-getAgentData()*************");
		System.out.println(jsonagent.getName());
		String jsonagentName=jsonagent.getName();
		String serviceStatus = "failure";
		try {
	
		String serviceName = jsonagent.getServicename();
		String agentName = jsonagent.getName();
		String agentEmailId = jsonagent.getEmailId();
		String productivityHours = jsonagent.getProductiveHours();
		String loginTime = jsonagent.getLoginTime();
		String logoutTime = jsonagent.getLogoutTime();
		String idleTimings = jsonagent.getIdleTimings();

		// Validations.
		if (null == serviceName || "undefined".equalsIgnoreCase(serviceName)
				|| "".trim().equalsIgnoreCase(serviceName)) {
			throw new ValidationException("Validation Exception :: Service name is empty");
		}
		if (null == agentName || "undefined".equalsIgnoreCase(agentName) || "".trim().equalsIgnoreCase(agentName)) {
			throw new ValidationException("Validation Exception :: Agent name is empty");
		}
		if (null == agentEmailId || "undefined".equalsIgnoreCase(agentEmailId)
				|| "".trim().equalsIgnoreCase(agentEmailId)) {
			throw new ValidationException("Validation Exception :: Agent Email is empty");
		}
		if (null == productivityHours || "undefined".equalsIgnoreCase(productivityHours)
				|| "".trim().equalsIgnoreCase(productivityHours)) {
			throw new ValidationException("Validation Exception :: Productivity Hours is empty");
		}
		if (null == loginTime || "undefined".equalsIgnoreCase(loginTime) || "".trim().equalsIgnoreCase(loginTime)) {
			throw new ValidationException("Validation Exception :: Login Time is empty");
		}
		if (null == logoutTime || "undefined".equalsIgnoreCase(logoutTime) || "".trim().equalsIgnoreCase(logoutTime)) {
			throw new ValidationException("Validation Exception :: Logout Time is empty");
		}
		if (null == idleTimings || "undefined".equalsIgnoreCase(idleTimings)) {
			throw new ValidationException("Validation Exception ::IdleTimingsl is null");
		}
	
			// if("error".equalsIgnoreCase(agentName)) {
			// throw new NullPointerException();
			// }
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
			if (serviceName.trim().equalsIgnoreCase("storeagentdata")) {
				AgentDAO dao = (AgentDAO) context.getBean("agentDAO");
				Agent agent = new Agent();
				agent.setEmailId(agentEmailId);
				agent.setName(agentName);
				String seconds = productivityHours;
				float minutes = (Float.parseFloat(seconds) / 60);
				float hours = (minutes / 60);
				agent.setProductiveHours(String.valueOf(hours));
				agent.setLoginTime(loginTime);
				agent.setLogoutTime(logoutTime);
				int count = dao.totalAgentCountInChromeMater(agentEmailId, loginTime);
				int status;
				if (count == 0) {
					status = dao.dataInsertionInChromeMater(agent);
				} else {

					status = dao.dataUpdateInChromeMater(agent);
				}

				if (status >= 0) {

					if (!idleTimings.trim().equalsIgnoreCase("")) {
						System.out.println("idle==============" + idleTimings);
						String splitline[] = idleTimings.split("&&");
						System.out.println("length of idle==============" + splitline.length);
						for (int i = 0; i < splitline.length; i++) {
							System.out.println(splitline[i] + "==============" + i);
							String splitfeilds[] = splitline[i].split("@@");
							System.out.println(splitfeilds.length + "-length===");
							String fromTime = splitfeilds[0];
							String toTime = splitfeilds[1];
							String websitesVisited = splitfeilds[2];
							String webistedvisited_updated = "";
							String websitevistedsplit[] = websitesVisited.split(",");
							for (int j = 0; j < websitevistedsplit.length; j++) {
								if (!websitevistedsplit[j].trim().equalsIgnoreCase("")) {
									if (webistedvisited_updated.trim().equalsIgnoreCase("")) {

										webistedvisited_updated = websitevistedsplit[j].trim();
									} else {

										webistedvisited_updated = webistedvisited_updated + ","
												+ websitevistedsplit[j].trim();
									}

								}
							}

							Agent updateagent = new Agent();
							updateagent.setEmailId(agentEmailId);
							updateagent.setName(agentName);
							updateagent.setIdleFrom(fromTime);
							updateagent.setIdleTo(toTime);
							updateagent.setWebsitesVisited(webistedvisited_updated);
							int updateStatus = dao.dataInsertionInChromeDetails(updateagent);
							if (updateStatus >= 0) {

							}

						}
					}
				}
				serviceStatus = "success";
			}

			if (serviceName.trim().equalsIgnoreCase("storeagentidledata")) {
				AgentDAO dao = (AgentDAO) context.getBean("agentDAO");
				Agent agent = new Agent();
				agent.setEmailId(agentEmailId);
				agent.setName(agentName);
				String seconds = productivityHours;
				float minutes = (Float.parseFloat(seconds) / 60);
				agent.setProductiveHours(String.valueOf(minutes));
				agent.setLoginTime(loginTime);
				agent.setLogoutTime(logoutTime);
				int count = dao.totalAgentCountInChromeMater(agentEmailId, loginTime);
				int status = 0;
				if (count == 1) {
					status = dao.dataUpdateInChromeMater(agent);
				}

				if (status == 1) {

					if (!idleTimings.trim().equalsIgnoreCase("")) {
						System.out.println("idle==============" + idleTimings);
						String splitline[] = idleTimings.split("&&");
						System.out.println("length of idle==============" + splitline.length);
						for (int i = 0; i < splitline.length; i++) {
							System.out.println(splitline[i] + "==============" + i);
							String splitfeilds[] = splitline[i].split("@@");
							System.out.println(splitfeilds.length + "-length===");
							String fromTime = splitfeilds[0];
							String toTime = splitfeilds[1];
							String websitesVisited = splitfeilds[2];
							String webistedvisited_updated = "";
							String websitevistedsplit[] = websitesVisited.split(",");
							for (int j = 0; j < websitevistedsplit.length; j++) {
								if (!websitevistedsplit[j].trim().equalsIgnoreCase("")) {
									if (webistedvisited_updated.trim().equalsIgnoreCase("")) {

										webistedvisited_updated = websitevistedsplit[j].trim();
									} else {

										webistedvisited_updated = webistedvisited_updated + ","
												+ websitevistedsplit[j].trim();
									}

								}
							}

							Agent updateagent = new Agent();
							updateagent.setEmailId(agentEmailId);
							updateagent.setName(agentName);
							updateagent.setIdleFrom(fromTime);
							updateagent.setIdleTo(toTime);
							updateagent.setWebsitesVisited(webistedvisited_updated);
							int updateStatus = dao.dataInsertionInChromeDetails(updateagent);
							if (updateStatus >= 0) {

							}

						}
					}
				}
				serviceStatus = "success";
			}

			return Response.status(200).entity(agentName + " Stored " + serviceStatus).build();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw new CustomException();
			} catch (CustomException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return Response.status(200).entity(jsonagentName + " Stored " + serviceStatus).build();
	}

	 

}

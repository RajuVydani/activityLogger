package com.automation.services;

import java.util.List;

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

		return Response.status(200).entity("success").build();
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
		Agent responseagent=new Agent();
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
			   String responseLoginTime="";
			   int responseUpdateSeconds=0;
			if (serviceName.trim().equalsIgnoreCase("storeagentdata")) {
				
				AgentDAO dao = (AgentDAO) context.getBean("agentDAO");
				Agent agent = new Agent();
				//remove white space from email id
				agentEmailId=agentEmailId.replaceAll("\\s+","");
				agent.setEmailId(agentEmailId);
				agent.setName(agentName);
				String seconds = productivityHours;
			  
				agent.setProductiveHours(String.valueOf(seconds));
				agent.setLoginTime(loginTime);
				agent.setLogoutTime(logoutTime);
				
				List<Agent> chromemasterlist=	 dao.CheckInChromeMaster(agentEmailId, loginTime);
			 
			 String prodHrsChromeMaster="";
			 int count=0;
				for (Agent e : chromemasterlist) {
					prodHrsChromeMaster=e.getProductiveHours();
					
					count=1;	
				}
			 
			
				if (count == 0) {
 
			
					
					Agent agentdet = new Agent();
					agentdet.setEmailId(agentEmailId);
					agentdet.setLoginTime(loginTime);
					List<Agent> chromtemlist = dao.CheckLoginEntry(agentdet);
                    String exist="";
					for (Agent e : chromtemlist) {
						responseLoginTime=e.getLoginTime();
						exist="yes";
						Agent updateagent = new Agent();
						updateagent.setEmailId(agentEmailId);
						updateagent.setName(agentName);
						String updateseconds = productivityHours;
						int totalseconds = ((Integer.parseInt(updateseconds) + Integer.parseInt(e.getProductiveHours())));
						System.out.println("updateseconds1"+updateseconds);
						System.out.println("updatesecond2"+e.getProductiveHours());
						System.out.println("updatesecond3"+totalseconds);
						updateagent.setProductiveHours(String.valueOf(totalseconds));
						updateagent.setLoginTime(e.getLoginTime());
						updateagent.setLogoutTime(logoutTime);
						responseagent.setLoginTime(responseLoginTime);
					 dao.dataUpdateInChromeMater(updateagent);
					}
if(!exist.trim().equalsIgnoreCase("yes"))
{
	responseLoginTime=loginTime;
	responseagent.setLoginTime(responseLoginTime);
					  dao.dataInsertionInChromeMater(agent);
					
}
 

					
					
					
					
				} else {
					
					Agent updateagent = new Agent();
					updateagent.setEmailId(agentEmailId);
					updateagent.setName(agentName);
					String updateseconds = prodHrsChromeMaster;
					int totalseconds = ((Integer.parseInt(updateseconds) + Integer.parseInt(productivityHours)));
					System.out.println("updateseconds4"+updateseconds);
					System.out.println("updatesecond5"+productivityHours);
					System.out.println("updatesecond6"+totalseconds);					
					updateagent.setProductiveHours(String.valueOf(totalseconds));
					updateagent.setLoginTime(loginTime);
					updateagent.setLogoutTime(logoutTime);
				 dao.dataUpdateInChromeMater(updateagent);
				 responseLoginTime=loginTime;
				 responseagent.setLoginTime(responseLoginTime);
				}
				
				
				
				
				
				
				
				
		 

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
		
				serviceStatus = "success";
				
				
				responseUpdateSeconds=dao.idleInterval();
				responseagent.setIdleInterval(responseUpdateSeconds);
				
				
			}


			return Response.status(200).entity(responseagent).build();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw new CustomException();
			} catch (CustomException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return Response.status(300).entity("failure").build();
	}

	 

}

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

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.automation.dao.AgentDAO;
import com.automation.dao.PolicyDAO;
import com.automation.exceptions.CustomException;
import com.automation.exceptions.ValidationException;
import com.automation.idao.IAgentDAO;
import com.automation.idao.IPolicyDAO;
import com.automation.model.Admin;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;
import com.automation.vo.Policy;

//Test url
//http://localhost:8082/TimeTracker/rest/agent/rajuV@cognizant.com
/**
 * @author 597125
 *
 */
/**
 * @author 597125
 *
 */
@Path("/agent")
public class TrackerService {
	private final static Logger logger = Logger.getLogger(TrackerService.class);
	private IAgentDAO agentDAO;
	private IPolicyDAO policyDAO;

	public void setPolicyDAO(IPolicyDAO policyDAO) {
		this.policyDAO = policyDAO;
	}

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
	/**
	 * @return
	 */
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

	/**
	 * This method will update Agent Login Time ,Logout Time,Productivity
	 * Hours,Idle Hours and Idle Timings
	 * 
	 * @param jsonagent
	 * @return
	 */
	/**
	 * @param jsonagent
	 * @return
	 * Service For Updating data from Chrome extension to database
	 */
	@POST
	@Path("/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response storeData(Agent jsonagent) {
		System.out.println(AppConstants.METHOD + "-getAgentData()*************");
 
		String serviceStatus = "failure";
		Agent responseagent = new Agent();
		try {

			String serviceName = jsonagent.getServicename();
 
			String agentEmailId = jsonagent.getEmailId();
			String Timings = jsonagent.getActivityTimings();

			logger.info("Service Name :" + serviceName);
			logger.info("Email Id=== :" + agentEmailId);
			// Validations.
			if (null == serviceName || "undefined".equalsIgnoreCase(serviceName)
					|| "".trim().equalsIgnoreCase(serviceName)) {
				throw new ValidationException("Validation Exception :: Service name is empty");
			}
		 
			if (null == agentEmailId || "undefined".equalsIgnoreCase(agentEmailId)
					|| "".trim().equalsIgnoreCase(agentEmailId)) {
				throw new ValidationException("Validation Exception :: Agent Email is empty");
			}

			if (null == Timings || "undefined".equalsIgnoreCase(Timings)) {
				throw new ValidationException("Validation Exception ::Timings is null");
			}

			// if("error".equalsIgnoreCase(agentName)) {
			// throw new NullPointerException();
			// }

			int responseUpdateSeconds = 90;
			if (serviceName.trim().equalsIgnoreCase("storeagentdata")) {

				Agent agent = new Agent();
				// remove white space from email id
				agentEmailId = agentEmailId.replaceAll("\\s+", "");
				agent.setEmailId(agentEmailId);
 

				if (!Timings.trim().equalsIgnoreCase("")) {

					String splitline[] = Timings.split("&&");

					for (int i = 0; i < splitline.length; i++) {

						String splitfeilds[] = splitline[i].split("@@");

						String fromTime = splitfeilds[0];
						String toTime = splitfeilds[1];
						String websitesVisited = splitfeilds[2];
						String activityCode = splitfeilds[4];
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
 
						updateagent.setFromDate(fromTime);
						updateagent.setToDate(toTime);
						updateagent.setWebsitesVisited(webistedvisited_updated);
						updateagent.setActivityCode(activityCode);
						int insertStatus=agentDAO.dataInsertionInChromeDetails(updateagent);
						logger.info("No Of Rows Inserted : "+insertStatus);

					}
				}

				serviceStatus = "success";
				String projectId="";
				String subProjectId="";
				String locationId="";
				
			  List<Agent> fetchProjectDetails=agentDAO.fetchProjectDetails(jsonagent);
				for (Agent projectlist : fetchProjectDetails) {
					
				 projectId=projectlist.getProjectId();
				  subProjectId=projectlist.getSubProjectId();
					  locationId=projectlist.getLocation();
				}
	
				if(projectId.trim().equalsIgnoreCase("") || subProjectId.trim().equalsIgnoreCase("")
						|| locationId.trim().equalsIgnoreCase("") || projectId == null || subProjectId == null || locationId == null)
				{
					responseUpdateSeconds=90;
				}
				else
				{
					Agent idleIntervalInput=new Agent();
					idleIntervalInput.setProjectId(projectId);
					idleIntervalInput.setSubProjectId(subProjectId);
					idleIntervalInput.setLocation(locationId);
					 List<Agent> fetchidleInterval=agentDAO.idleInterval(idleIntervalInput);
						for (Agent fetchidleIntervallist : fetchidleInterval) {
							
							responseUpdateSeconds = fetchidleIntervallist.getIdleInterval();
						}
			
				}
				responseagent.setIdleInterval(responseUpdateSeconds);

			}

			logger.info("Request Processed Successfully");
			return Response.status(200).entity(responseagent).build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured  while procession request:" + jsonagent + " Exception details :" + e);

			try {
				throw new CustomException();
			} catch (CustomException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("Exception occured  while procession request:" + jsonagent + " Exception details :" + e1);
			}
		}

		return Response.status(300).entity("failure").build();
	}

	/**
	 * This method will check for latest policy and update policy flag
	 * 
	 * @param jsonagent
	 * @return
	 */
	@POST
	@Path("/policy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response policyupdate(Policy jsonagent) {

		String serviceStatus = "failure";
		Policy responseagent = new Policy();
		try {

			String serviceName = jsonagent.getServicename();

			String agentEmailId = jsonagent.getEmailId();
			String policyFlag = jsonagent.getPolicyFlag();

			// Validations.
			if (null == serviceName || "undefined".equalsIgnoreCase(serviceName)
					|| "".trim().equalsIgnoreCase(serviceName)) {
				throw new ValidationException("Validation Exception :: Service name is empty");
			}

			if (null == agentEmailId || "undefined".equalsIgnoreCase(agentEmailId)
					|| "".trim().equalsIgnoreCase(agentEmailId)) {
				throw new ValidationException("Validation Exception :: Agent Email is empty");
			}

			if (null == policyFlag || "undefined".equalsIgnoreCase(policyFlag)) {
				throw new ValidationException("Validation Exception ::Policy Flag is null");
			}

			// if("error".equalsIgnoreCase(agentName)) {
			// throw new NullPointerException();
			// }
			logger.info("Service Name :" + serviceName);
			logger.info("Email Id     :" + agentEmailId);
			if (serviceName.trim().equalsIgnoreCase("policyaccess")) {

				Policy policy = new Policy();
				// remove white space from email id
				agentEmailId = agentEmailId.replaceAll("\\s+", "");
				policy.setEmailId(agentEmailId);
				List<Policy> policylist = policyDAO.readPolicyFlag(agentEmailId);

				List<Policy> policydetails = policyDAO.readPolicyDetails();

				for (Policy e : policydetails) {
					responseagent.setEmailId(agentEmailId);
					responseagent.setPolicyContent(e.getPolicyContent());
					responseagent.setPolicyTagging(e.getPolicyTagging());
					responseagent.setPolicyUpdatedOn(e.getPolicyUpdatedOn());
					logger.info("policy Content :" + (e.getPolicyContent()));
					logger.info("policy Tagging :" + (e.getPolicyTagging()));
					logger.info("policy Updated On :" + (e.getPolicyUpdatedOn()));
				}

			}
			if (serviceName.trim().equalsIgnoreCase("policyupdate")) {

				Policy policy = new Policy();
				// remove white space from email id
				agentEmailId = agentEmailId.replaceAll("\\s+", "");
				policy.setEmailId(agentEmailId);
				List<Policy> policylist = policyDAO.readPolicyFlag(agentEmailId);

				String policyflag = "";

				for (Policy e : policylist) {
					policyflag = e.getPolicyFlag();
					logger.info("policyflag :" + policyflag);

				}

				if (policyflag.trim().equalsIgnoreCase("true")) {

					List<Policy> policydetails = policyDAO.readPolicyDetails();

					for (Policy e : policydetails) {
						responseagent.setEmailId(agentEmailId);
						responseagent.setPolicyContent(e.getPolicyContent());
						responseagent.setPolicyTagging(e.getPolicyTagging());
						responseagent.setPolicyUpdatedOn(e.getPolicyUpdatedOn());
						logger.info("policy Content :" + (e.getPolicyContent()));
						logger.info("policy Tagging :" + (e.getPolicyTagging()));
						logger.info("policy Updated On :" + (e.getPolicyUpdatedOn()));
					}

				}
			}
			if (serviceName.trim().equalsIgnoreCase("policyupdateflag")) {

				Policy policy = new Policy();
				// remove white space from email id
				agentEmailId = agentEmailId.replaceAll("\\s+", "");
				policy.setEmailId(agentEmailId);
				int status = policyDAO.updatePolicyFlag(policy);

			}
			logger.info("Request Processed Successfully");
			return Response.status(200).entity(responseagent).build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured  while procession request:" + jsonagent + " Exception details :" + e);
			try {
				throw new CustomException();
			} catch (CustomException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception occured  while procession request:" + jsonagent + " Exception details :" + e1);
				e1.printStackTrace();
			}
		}

		return Response.status(300).entity("failure").build();
	}

}

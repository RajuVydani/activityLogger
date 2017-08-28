package com.automation.services;

import java.util.Calendar;
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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.automation.dao.AgentDAO;
import com.automation.exceptions.CustomException;
import com.automation.exceptions.ValidationException;
import com.automation.idao.IAdminDAO;
import com.automation.idao.IAgentDAO;
import com.automation.model.Admin;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;
import com.automation.vo.ServiceResonse;

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
@Path("/dashboard")
public class DashboardServices {
	private final static Logger logger = Logger.getLogger(DashboardServices.class);
	
	private IAdminDAO adminDAO;


	public void setAdminDAO(IAdminDAO adminDAO) {
		this.adminDAO = adminDAO;
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
	@Path("/login/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response dashboardData(@PathParam("userId") String userId) {
		System.out.println("calling dashboardData()...");
	
		try
		{
		
			logger.info("userId===:" + userId);
			if (null == userId || "undefined".equalsIgnoreCase(userId)
					|| "".trim().equalsIgnoreCase(userId)) {
				throw new ValidationException("Validation Exception :: User Id is empty");
			}
			
			Admin userInput=new Admin();
			userInput.setUserId(userId);
			
			List<Admin> previledgeDetails=adminDAO.getPreviledgeId(userInput);
			int prviledgeId=0;
			String viewOnly="";
			String pdfExport="";
			String csvExport="";
			String excelExport="";
			String printOption="";

			for (Admin previledgeList : previledgeDetails) {
				prviledgeId=Integer.parseInt(previledgeList.getPriviledgeId());
				viewOnly=previledgeList.getViewOnly();
				pdfExport=previledgeList.getPdfExport();
				csvExport=previledgeList.getCsvExport();
				excelExport=previledgeList.getExcelExport();
				printOption=previledgeList.getPrintOption();
				
			}
			
			Calendar now = Calendar.getInstance();
			int currentyear = now.get(Calendar.YEAR);
			int currentmonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
			
			int previousYear=0;
			int previousMonth=0;
			String supevisorList = "";
			if(currentmonth == 1)
			{
				previousMonth=12;
				previousYear=(currentmonth - 1);
			}
			else
			{
				
				previousMonth=(currentmonth - 1);
				previousYear=(currentmonth - 1);	
				
			}
			
			logger.info("prviledgeId :" + prviledgeId);	
			if(prviledgeId == 10 || prviledgeId == 20 || prviledgeId == 30)
			{
			if(prviledgeId == 10)
			{
				supevisorList=userId;	
				
			}
			if(prviledgeId == 20)
			{
				Admin teamManagerInput=new Admin();
				teamManagerInput.setSupervisorList(userId);
				List<Admin> supervisorDetails=adminDAO.getSupervisor(teamManagerInput);
				
				for (Admin supervisorList : supervisorDetails) {
					if(supevisorList.trim().equalsIgnoreCase(""))
					{
						supevisorList=supervisorList.getUserId();
					}
					else
					{
						supevisorList=supevisorList+","+supervisorList.getUserId();
					}
					
				}
			 	
				
			}
			if(prviledgeId == 30)
			{
				
				Admin serviceDelMngrInput=new Admin();
				serviceDelMngrInput.setSupervisorList(userId);
				String teamMngrList="";
				List<Admin> teamManagerDetails=adminDAO.getSupervisor(serviceDelMngrInput);
				for (Admin teamManagerList : teamManagerDetails) {
					if(teamMngrList.trim().equalsIgnoreCase(""))
					{
						teamMngrList=teamManagerList.getUserId();
					}
					else
					{
						teamMngrList=supevisorList+","+teamManagerList.getUserId();
					}
					
				}
				
				Admin teamManagerInput=new Admin();
				teamManagerInput.setSupervisorList(teamMngrList);
				List<Admin> supervisorDetails=adminDAO.getSupervisor(teamManagerInput);
				
				for (Admin supervisorList : supervisorDetails) {
					if(supevisorList.trim().equalsIgnoreCase(""))
					{
						supevisorList=supervisorList.getUserId();
					}
					else
					{
						supevisorList=supevisorList+","+supervisorList.getUserId();
					}
					
				}
			 	
				
			}
				logger.info("Current Month :" + currentmonth);
				logger.info("Current Year  :" + currentyear);
			Admin currentMonthInput=new Admin();
			currentMonthInput.setSupervisorList(userId);
			currentMonthInput.setMonth(String.valueOf(currentmonth));
			currentMonthInput.setYear(String.valueOf(currentyear));
		
			List<Admin> currentMonthDetails=adminDAO.getMntDataSubProLvl(currentMonthInput);
			
			for (Admin currentMonthList : currentMonthDetails) {
			
				
				logger.info("Current Month Prod   :" + currentMonthList.getProdSum());
				logger.info("current Month Idle   :" + currentMonthList.getIdleSum());
				logger.info("Current Month break  :" + currentMonthList.getBreakSum());
                logger.info("Current Month Huddle :" + currentMonthList.getHuddleSum());
				logger.info("Current Month Fb Training :" + currentMonthList.getFbTrainingSum());
				logger.info("Current Month Team Meeting  :" + currentMonthList.getTeamMeetingSum());
				logger.info("Current Month Coaching:" + currentMonthList.getCoachingSum());
				logger.info("Current Month Welness Support  :" + currentMonthList.getWelnessSupportSum());
				logger.info("Current Month Project Id :" + currentMonthList.getProjectId());
				logger.info("Current Month Sub Project Id  :" + currentMonthList.getSubProjectId());
				logger.info("Current Month Project Name :" + currentMonthList.getProjectName());
				logger.info("Current Month Sub Project Name  :" + currentMonthList.getSubProjectName());
				logger.info("Current Month Location Id  :" + currentMonthList.getLocationId());
		
			}
			
			
List<Admin> currentMonthCountDetails=adminDAO.getMntCountSubProLvl(currentMonthInput);
			
			for (Admin currentMonthCountList : currentMonthCountDetails) {
			
				
				logger.info("Current Month Agent Count   :" + currentMonthCountList.getAgentCount());
		
			}
		
			logger.info("Previous Month :" + previousMonth);
			logger.info("Previous Year  :" + previousYear);
		Admin previousMonthInput=new Admin();
		previousMonthInput.setSupervisorList(userId);
		previousMonthInput.setMonth(String.valueOf(previousMonth));
		previousMonthInput.setYear(String.valueOf(previousYear));
	
		List<Admin> previousMonthDetails=adminDAO.getMntDataSubProLvl(previousMonthInput);
		
		for (Admin previousMonthList : previousMonthDetails) {
		
			
			logger.info("Previous Month Prod   :" + previousMonthList.getProdSum());
			logger.info("Previous Month Idle   :" + previousMonthList.getIdleSum());
			logger.info("Previous Month break  :" + previousMonthList.getBreakSum());
            logger.info("Previous Month Huddle :" + previousMonthList.getHuddleSum());
			logger.info("Previous Month Fb Training :" + previousMonthList.getFbTrainingSum());
			logger.info("Previous Month Team Meeting  :" + previousMonthList.getTeamMeetingSum());
			logger.info("Previous Month Coaching:" + previousMonthList.getCoachingSum());
			logger.info("Previous Month Welness Support  :" + previousMonthList.getWelnessSupportSum());
			logger.info("Previous Month Project Id :" + previousMonthList.getProjectId());
			logger.info("Previous Month Sub Project Id  :" + previousMonthList.getSubProjectId());
            logger.info("Previous Month Project Name :" + previousMonthList.getProjectName());
			logger.info("Previous Month Sub Project Name  :" + previousMonthList.getSubProjectName());
			logger.info("Previous Month Location Id  :" + previousMonthList.getLocationId());
	
		}
		
		
List<Admin> previousMonthCountDetails=adminDAO.getMntCountSubProLvl(previousMonthInput);
		
		for (Admin previousMonthCountList : previousMonthCountDetails) {
		
			
			logger.info("Previous Month Agent Count   :" + previousMonthCountList.getAgentCount());
	
		}
			}
		 
			
		/*	if(prviledgeId == 40)
			{
			
				Admin projectManagerInput=new Admin();
				projectManagerInput.setProgramManagerId(userId);
				List<Admin> projectDetails=adminDAO.getProjectId(projectManagerInput);
				
				for (Admin projectList : projectDetails) {
					if(supevisorList.trim().equalsIgnoreCase(""))
					{
						supevisorList=supervisorList.getUserId();
					}
					else
					{
						supevisorList=supevisorList+","+supervisorList.getUserId();
					}
					
				}	
				
			}*/
			
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured  while procession request: Exception details :" + e);

			try {
				throw new CustomException();
			} catch (CustomException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("Exception occured  while procession request: Exception details :" + e1);
			}		
		}
		
		
		
		

		return Response.status(200).entity("success").build();
	}
	
	
	
	

	// http://localhost:8082/TimeTracker/rest/agent/data
		/**
		 * @return
		 */
		@GET
		@Path("/subproject/{userId}/{subProjectId}/{locationId}/")
		@Produces(MediaType.APPLICATION_JSON)
		
		public Response dashboardSubProjectData(@PathParam("userId") String userId,
				
				@PathParam("subProjectId") String subProjectId,
				@PathParam("locationId") String locationId) {
			System.out.println("calling dashboardSubProjectData()...");
		
			try
			{
			
				logger.info("userId===:" + userId);
				if (null == userId || "undefined".equalsIgnoreCase(userId)
						|| "".trim().equalsIgnoreCase(userId)) {
					throw new ValidationException("Validation Exception :: User Id is empty");
				}
				
				if (null == subProjectId || "undefined".equalsIgnoreCase(subProjectId)
						|| "".trim().equalsIgnoreCase(subProjectId)) {
					throw new ValidationException("Validation Exception :: Sub ProjectId is empty");
				}
				if (null == locationId || "undefined".equalsIgnoreCase(locationId)
						|| "".trim().equalsIgnoreCase(locationId)) {
					throw new ValidationException("Validation Exception :: Location Id is empty");
				}
				
				Admin userInput=new Admin();
				userInput.setUserId(userId);
				
				List<Admin> previledgeDetails=adminDAO.getPreviledgeId(userInput);
				int prviledgeId=0;
				String viewOnly="";
				String pdfExport="";
				String csvExport="";
				String excelExport="";
				String printOption="";

				for (Admin previledgeList : previledgeDetails) {
					prviledgeId=Integer.parseInt(previledgeList.getPriviledgeId());
					viewOnly=previledgeList.getViewOnly();
					pdfExport=previledgeList.getPdfExport();
					csvExport=previledgeList.getCsvExport();
					excelExport=previledgeList.getExcelExport();
					printOption=previledgeList.getPrintOption();
					
				}
				
				Calendar now = Calendar.getInstance();
				int currentyear = now.get(Calendar.YEAR);
				int currentmonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
				
				int previousYear=0;
				int previousMonth=0;
				String supevisorList = "";
				if(currentmonth == 1)
				{
					previousMonth=12;
					previousYear=(currentmonth - 1);
				}
				else
				{
					
					previousMonth=(currentmonth - 1);
					previousYear=(currentmonth - 1);	
					
				}
				
				logger.info("prviledgeId :" + prviledgeId);	
				
					logger.info("Current Month :" + currentmonth);
					logger.info("Current Year  :" + currentyear);
				Admin currentMonthInput=new Admin();
				
				currentMonthInput.setMonth(String.valueOf(currentmonth));
				currentMonthInput.setYear(String.valueOf(currentyear));
				currentMonthInput.setSubProjectId(subProjectId);
				currentMonthInput.setLocationId(locationId);
			
				List<Admin> currentMonthDetails=adminDAO.getMntDataSpecificSubProLvl(currentMonthInput);
				
				for (Admin currentMonthList : currentMonthDetails) {
				
					
					logger.info("Current Month Prod   :" + currentMonthList.getProdSum());
					logger.info("current Month Idle   :" + currentMonthList.getIdleSum());
					logger.info("Current Month break  :" + currentMonthList.getBreakSum());
	                logger.info("Current Month Huddle :" + currentMonthList.getHuddleSum());
					logger.info("Current Month Fb Training :" + currentMonthList.getFbTrainingSum());
					logger.info("Current Month Team Meeting  :" + currentMonthList.getTeamMeetingSum());
					logger.info("Current Month Coaching:" + currentMonthList.getCoachingSum());
					logger.info("Current Month Welness Support  :" + currentMonthList.getWelnessSupportSum());
					logger.info("Current Month Project Id :" + currentMonthList.getProjectId());
					logger.info("Current Month Sub Project Id  :" + currentMonthList.getSubProjectId());
					logger.info("Current Month Project Name :" + currentMonthList.getProjectName());
					logger.info("Current Month Sub Project Name  :" + currentMonthList.getSubProjectName());
					logger.info("Current Month Location Id  :" + currentMonthList.getLocationId());
			
				}
				
				
	List<Admin> currentMonthCountDetails=adminDAO.getMntCountSpecificSubProLvl(currentMonthInput);
				
				for (Admin currentMonthCountList : currentMonthCountDetails) {
				
					
					logger.info("Current Month Agent Count   :" + currentMonthCountList.getAgentCount());
			
				}
			
				logger.info("Previous Month :" + previousMonth);
				logger.info("Previous Year  :" + previousYear);
			Admin previousMonthInput=new Admin();
			previousMonthInput.setSupervisorList(userId);
			previousMonthInput.setMonth(String.valueOf(previousMonth));
			previousMonthInput.setYear(String.valueOf(previousYear));
			previousMonthInput.setSubProjectId(subProjectId);
			previousMonthInput.setLocationId(locationId);
			
			
			List<Admin> previousMonthDetails=adminDAO.getMntCountSpecificSubProLvl(previousMonthInput);
			
			for (Admin previousMonthList : previousMonthDetails) {
			
				
				logger.info("Previous Month Prod   :" + previousMonthList.getProdSum());
				logger.info("Previous Month Idle   :" + previousMonthList.getIdleSum());
				logger.info("Previous Month break  :" + previousMonthList.getBreakSum());
	            logger.info("Previous Month Huddle :" + previousMonthList.getHuddleSum());
				logger.info("Previous Month Fb Training :" + previousMonthList.getFbTrainingSum());
				logger.info("Previous Month Team Meeting  :" + previousMonthList.getTeamMeetingSum());
				logger.info("Previous Month Coaching:" + previousMonthList.getCoachingSum());
				logger.info("Previous Month Welness Support  :" + previousMonthList.getWelnessSupportSum());
				logger.info("Previous Month Project Id :" + previousMonthList.getProjectId());
				logger.info("Previous Month Sub Project Id  :" + previousMonthList.getSubProjectId());
	            logger.info("Previous Month Project Name :" + previousMonthList.getProjectName());
				logger.info("Previous Month Sub Project Name  :" + previousMonthList.getSubProjectName());
				logger.info("Previous Month Location Id  :" + previousMonthList.getLocationId());
		
			}
			
			
	List<Admin> previousMonthCountDetails=adminDAO.getMntCountSpecificSubProLvl(previousMonthInput);
			
			for (Admin previousMonthCountList : previousMonthCountDetails) {
			
				
				logger.info("Previous Month Agent Count   :" + previousMonthCountList.getAgentCount());
		
			}
				
				
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("Exception occured  while procession request: Exception details :" + e);

				try {
					throw new CustomException();
				} catch (CustomException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					logger.error("Exception occured  while procession request: Exception details :" + e1);
				}		
			}
			
			
	

			return Response.status(200).entity("success").build();
		}

		
		
		
		// http://localhost:8082/TimeTracker/rest/agent/data
			/**
			 * @return
			 */
			@GET
			@Path("/project/{userId}/{projectId}/{locationId}/")
			@Produces(MediaType.APPLICATION_JSON)
			
			public Response dashboardProjectData(@PathParam("userId") String userId,
					
					@PathParam("projectId") String projectId,
					@PathParam("locationId") String locationId) {
				System.out.println("calling dashboardProjectData()...");
			
				try
				{
				
					logger.info("userId===:" + userId);
					if (null == userId || "undefined".equalsIgnoreCase(userId)
							|| "".trim().equalsIgnoreCase(userId)) {
						throw new ValidationException("Validation Exception :: User Id is empty");
					}
					
					if (null == projectId || "undefined".equalsIgnoreCase(projectId)
							|| "".trim().equalsIgnoreCase(projectId)) {
						throw new ValidationException("Validation Exception :: ProjectId is empty");
					}
					if (null == locationId || "undefined".equalsIgnoreCase(locationId)
							|| "".trim().equalsIgnoreCase(locationId)) {
						throw new ValidationException("Validation Exception :: Location Id is empty");
					}
					
					Admin userInput=new Admin();
					userInput.setUserId(userId);
					
					List<Admin> previledgeDetails=adminDAO.getPreviledgeId(userInput);
					int prviledgeId=0;
					String viewOnly="";
					String pdfExport="";
					String csvExport="";
					String excelExport="";
					String printOption="";

					for (Admin previledgeList : previledgeDetails) {
						prviledgeId=Integer.parseInt(previledgeList.getPriviledgeId());
						viewOnly=previledgeList.getViewOnly();
						pdfExport=previledgeList.getPdfExport();
						csvExport=previledgeList.getCsvExport();
						excelExport=previledgeList.getExcelExport();
						printOption=previledgeList.getPrintOption();
						
					}
					
					Calendar now = Calendar.getInstance();
					int currentyear = now.get(Calendar.YEAR);
					int currentmonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
					
					int previousYear=0;
					int previousMonth=0;
					String supevisorList = "";
					if(currentmonth == 1)
					{
						previousMonth=12;
						previousYear=(currentmonth - 1);
					}
					else
					{
						
						previousMonth=(currentmonth - 1);
						previousYear=(currentmonth - 1);	
						
					}
					
					logger.info("prviledgeId :" + prviledgeId);	
					
						logger.info("Current Month :" + currentmonth);
						logger.info("Current Year  :" + currentyear);
					Admin currentMonthInput=new Admin();
					
					currentMonthInput.setMonth(String.valueOf(currentmonth));
					currentMonthInput.setYear(String.valueOf(currentyear));
					currentMonthInput.setProjectId(projectId);
					currentMonthInput.setLocationId(locationId);
				
					List<Admin> currentMonthDetails=adminDAO.getMntDataSpecificProLvl(currentMonthInput);
					
					for (Admin currentMonthList : currentMonthDetails) {
					
						
						logger.info("Current Month Prod   :" + currentMonthList.getProdSum());
						logger.info("current Month Idle   :" + currentMonthList.getIdleSum());
						logger.info("Current Month break  :" + currentMonthList.getBreakSum());
		                logger.info("Current Month Huddle :" + currentMonthList.getHuddleSum());
						logger.info("Current Month Fb Training :" + currentMonthList.getFbTrainingSum());
						logger.info("Current Month Team Meeting  :" + currentMonthList.getTeamMeetingSum());
						logger.info("Current Month Coaching:" + currentMonthList.getCoachingSum());
						logger.info("Current Month Welness Support  :" + currentMonthList.getWelnessSupportSum());
						logger.info("Current Month Project Id :" + currentMonthList.getProjectId());
						logger.info("Current Month Sub Project Id  :" + currentMonthList.getSubProjectId());
						logger.info("Current Month Project Name :" + currentMonthList.getProjectName());
						logger.info("Current Month Sub Project Name  :" + currentMonthList.getSubProjectName());
						logger.info("Current Month Location Id  :" + currentMonthList.getLocationId());
				
					}
					
					
		List<Admin> currentMonthCountDetails=adminDAO.getMntCountSpecificProLvl(currentMonthInput);
					
					for (Admin currentMonthCountList : currentMonthCountDetails) {
					
						
						logger.info("Current Month Agent Count   :" + currentMonthCountList.getAgentCount());
				
					}
				
					logger.info("Previous Month :" + previousMonth);
					logger.info("Previous Year  :" + previousYear);
				Admin previousMonthInput=new Admin();
				previousMonthInput.setSupervisorList(userId);
				previousMonthInput.setMonth(String.valueOf(previousMonth));
				previousMonthInput.setYear(String.valueOf(previousYear));
				previousMonthInput.setProjectId(projectId);
				previousMonthInput.setLocationId(locationId);
				
				
				List<Admin> previousMonthDetails=adminDAO.getMntCountSpecificProLvl(previousMonthInput);
				
				for (Admin previousMonthList : previousMonthDetails) {
				
					
					logger.info("Previous Month Prod   :" + previousMonthList.getProdSum());
					logger.info("Previous Month Idle   :" + previousMonthList.getIdleSum());
					logger.info("Previous Month break  :" + previousMonthList.getBreakSum());
		            logger.info("Previous Month Huddle :" + previousMonthList.getHuddleSum());
					logger.info("Previous Month Fb Training :" + previousMonthList.getFbTrainingSum());
					logger.info("Previous Month Team Meeting  :" + previousMonthList.getTeamMeetingSum());
					logger.info("Previous Month Coaching:" + previousMonthList.getCoachingSum());
					logger.info("Previous Month Welness Support  :" + previousMonthList.getWelnessSupportSum());
					logger.info("Previous Month Project Id :" + previousMonthList.getProjectId());
					logger.info("Previous Month Sub Project Id  :" + previousMonthList.getSubProjectId());
		            logger.info("Previous Month Project Name :" + previousMonthList.getProjectName());
					logger.info("Previous Month Sub Project Name  :" + previousMonthList.getSubProjectName());
					logger.info("Previous Month Location Id  :" + previousMonthList.getLocationId());
			
				}
				
				
		List<Admin> previousMonthCountDetails=adminDAO.getMntCountSpecificSubProLvl(previousMonthInput);
				
				for (Admin previousMonthCountList : previousMonthCountDetails) {
				
					
					logger.info("Previous Month Agent Count   :" + previousMonthCountList.getAgentCount());
			
				}
					
					
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error("Exception occured  while procession request: Exception details :" + e);

					try {
						throw new CustomException();
					} catch (CustomException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						logger.error("Exception occured  while procession request: Exception details :" + e1);
					}		
				}
				
				
		

				return Response.status(200).entity("success").build();
			}
	
			// http://localhost:8082/TimeTracker/rest/agent/data
			/**
			 * @return
			 */
			@GET
			@Path("/location/{userId}/{locationId}/")
			@Produces(MediaType.APPLICATION_JSON)
			
			public Response dashboardLocationData(@PathParam("userId") String userId,
					
					
					@PathParam("locationId") String locationId) {
				System.out.println("calling dashboardLocationData()...");
			
				try
				{
				
					logger.info("userId===:" + userId);
					if (null == userId || "undefined".equalsIgnoreCase(userId)
							|| "".trim().equalsIgnoreCase(userId)) {
						throw new ValidationException("Validation Exception :: User Id is empty");
					}
					
					
					if (null == locationId || "undefined".equalsIgnoreCase(locationId)
							|| "".trim().equalsIgnoreCase(locationId)) {
						throw new ValidationException("Validation Exception :: Location Id is empty");
					}
					
					Admin userInput=new Admin();
					userInput.setUserId(userId);
					
					List<Admin> previledgeDetails=adminDAO.getPreviledgeId(userInput);
					int prviledgeId=0;
					String viewOnly="";
					String pdfExport="";
					String csvExport="";
					String excelExport="";
					String printOption="";

					for (Admin previledgeList : previledgeDetails) {
						prviledgeId=Integer.parseInt(previledgeList.getPriviledgeId());
						viewOnly=previledgeList.getViewOnly();
						pdfExport=previledgeList.getPdfExport();
						csvExport=previledgeList.getCsvExport();
						excelExport=previledgeList.getExcelExport();
						printOption=previledgeList.getPrintOption();
						
					}
					
					Calendar now = Calendar.getInstance();
					int currentyear = now.get(Calendar.YEAR);
					int currentmonth = now.get(Calendar.MONTH) + 1; // Note: zero based!
					
					int previousYear=0;
					int previousMonth=0;
					String supevisorList = "";
					if(currentmonth == 1)
					{
						previousMonth=12;
						previousYear=(currentmonth - 1);
					}
					else
					{
						
						previousMonth=(currentmonth - 1);
						previousYear=(currentmonth - 1);	
						
					}
					
					logger.info("prviledgeId :" + prviledgeId);	
					
						logger.info("Current Month :" + currentmonth);
						logger.info("Current Year  :" + currentyear);
					Admin currentMonthInput=new Admin();
					
					currentMonthInput.setMonth(String.valueOf(currentmonth));
					currentMonthInput.setYear(String.valueOf(currentyear));
					
					currentMonthInput.setLocationId(locationId);
				
					List<Admin> currentMonthDetails=adminDAO.getMntDataLoc(currentMonthInput);
					
					for (Admin currentMonthList : currentMonthDetails) {
					
						
						logger.info("Current Month Prod   :" + currentMonthList.getProdSum());
						logger.info("current Month Idle   :" + currentMonthList.getIdleSum());
						logger.info("Current Month break  :" + currentMonthList.getBreakSum());
		                logger.info("Current Month Huddle :" + currentMonthList.getHuddleSum());
						logger.info("Current Month Fb Training :" + currentMonthList.getFbTrainingSum());
						logger.info("Current Month Team Meeting  :" + currentMonthList.getTeamMeetingSum());
						logger.info("Current Month Coaching:" + currentMonthList.getCoachingSum());
						logger.info("Current Month Welness Support  :" + currentMonthList.getWelnessSupportSum());
						logger.info("Current Month Project Id :" + currentMonthList.getProjectId());
						logger.info("Current Month Sub Project Id  :" + currentMonthList.getSubProjectId());
						logger.info("Current Month Project Name :" + currentMonthList.getProjectName());
						logger.info("Current Month Sub Project Name  :" + currentMonthList.getSubProjectName());
						logger.info("Current Month Location Id  :" + currentMonthList.getLocationId());
				
					}
					
					
		List<Admin> currentMonthCountDetails=adminDAO.getMntCountSpecificProLvl(currentMonthInput);
					
					for (Admin currentMonthCountList : currentMonthCountDetails) {
					
						
						logger.info("Current Month Agent Count   :" + currentMonthCountList.getAgentCount());
				
					}
				
					logger.info("Previous Month :" + previousMonth);
					logger.info("Previous Year  :" + previousYear);
				Admin previousMonthInput=new Admin();
				previousMonthInput.setSupervisorList(userId);
				previousMonthInput.setMonth(String.valueOf(previousMonth));
				previousMonthInput.setYear(String.valueOf(previousYear));
				
				previousMonthInput.setLocationId(locationId);
				
				
				List<Admin> previousMonthDetails=adminDAO.getMntCountSpecificProLvl(previousMonthInput);
				
				for (Admin previousMonthList : previousMonthDetails) {
				
					
					logger.info("Previous Month Prod   :" + previousMonthList.getProdSum());
					logger.info("Previous Month Idle   :" + previousMonthList.getIdleSum());
					logger.info("Previous Month break  :" + previousMonthList.getBreakSum());
		            logger.info("Previous Month Huddle :" + previousMonthList.getHuddleSum());
					logger.info("Previous Month Fb Training :" + previousMonthList.getFbTrainingSum());
					logger.info("Previous Month Team Meeting  :" + previousMonthList.getTeamMeetingSum());
					logger.info("Previous Month Coaching:" + previousMonthList.getCoachingSum());
					logger.info("Previous Month Welness Support  :" + previousMonthList.getWelnessSupportSum());
					logger.info("Previous Month Project Id :" + previousMonthList.getProjectId());
					logger.info("Previous Month Sub Project Id  :" + previousMonthList.getSubProjectId());
		            logger.info("Previous Month Project Name :" + previousMonthList.getProjectName());
					logger.info("Previous Month Sub Project Name  :" + previousMonthList.getSubProjectName());
					logger.info("Previous Month Location Id  :" + previousMonthList.getLocationId());
			
				}
				
				
		List<Admin> previousMonthCountDetails=adminDAO.getMntCountLoc(previousMonthInput);
				
				for (Admin previousMonthCountList : previousMonthCountDetails) {
				
					
					logger.info("Previous Month Agent Count   :" + previousMonthCountList.getAgentCount());
			
				}
					
					
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.error("Exception occured  while procession request: Exception details :" + e);

					try {
						throw new CustomException();
					} catch (CustomException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						logger.error("Exception occured  while procession request: Exception details :" + e1);
					}		
				}
				
				
		

				return Response.status(200).entity("success").build();
			}
		

}

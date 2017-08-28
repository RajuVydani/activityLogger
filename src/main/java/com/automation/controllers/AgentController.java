///////////////////////////////////////////////////
//HEADER
///////////////////////////////////////////////////

package com.automation.controllers;

import com.automation.idao.IAgentDAO;
import com.automation.model.Login;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;
import com.automation.vo.Dashboard;
import com.automation.vo.Metrics;
import com.automation.vo.ActivityCodes;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class AgentController {

	@Autowired
	private IAgentDAO agentDAO;

	private static final Logger LOGGER = Logger.getLogger(AgentController.class);
	
	@RequestMapping("/result")
	public @ResponseBody Dashboard getDashboard() {
		
		ActivityCodes aCodes_current = new ActivityCodes();
		aCodes_current.setBreaks("30");
		aCodes_current.setMeals("50");
		aCodes_current.setCoaching("20");
		aCodes_current.setHuddle("90");
		aCodes_current.setFb_training("10");
		aCodes_current.setNon_fb_training("40");
		aCodes_current.setTeam_meeting("30");
		aCodes_current.setWellness_support("10");
		aCodes_current.setTotal_shrinkage("120");
		
		ActivityCodes aCodes_past = new ActivityCodes();
		aCodes_past.setBreaks("30");
		aCodes_past.setMeals("50");
		aCodes_past.setCoaching("20");
		aCodes_past.setHuddle("90");
		aCodes_past.setFb_training("10");
		aCodes_past.setNon_fb_training("40");
		aCodes_past.setTeam_meeting("30");
		aCodes_past.setWellness_support("10");
		aCodes_past.setTotal_shrinkage("220");
		
		
		Metrics lMonth = new Metrics();
		lMonth.setProductivity("100");
		lMonth.setProdPercent("98");
		lMonth.setShrinkage(aCodes_current);
		lMonth.setHeadCount("250");
		
		Metrics cMonth = new Metrics();
		cMonth.setProductivity("90");
		cMonth.setProdPercent("72");
		cMonth.setShrinkage(aCodes_past);
		cMonth.setHeadCount("120");
		
		Dashboard dashboard = new Dashboard();
		dashboard.setHierarchy(Arrays.asList(new String [] {"PAGES", "COMMERCE", "VIDEOS"}));
		dashboard.setLocations(Arrays.asList(new String [] {"HYD", "CHN", "PHX"}));
		dashboard.setPastMonth(lMonth);
		dashboard.setCurrentMonth(cMonth);
		
		return dashboard;
	}

	/**
	 * Displays Policy update page. New policy updates will be feeded in to the
	 * system.
	 * 
	 * @return policy update page
	 * @throws Exception
	 */
	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public ModelAndView policy() throws Exception {
		LOGGER.info("In policy()....");
		ModelAndView model = new ModelAndView("policyupdate");
		model.addObject("policyUpdateActive", AppConstants.active);
		return model;
		// Policy Update Page
	}

	/**
	 * Displays Agent Dashboard after successful logging in.
	 * 
	 * @param login
	 * @return Dashboard Page
	 * @throws Exception
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ModelAndView authenticate(@ModelAttribute("loginParam") Login login) throws Exception {
		LOGGER.info("In authenticate()....");

		String managerName = login.getUsername();
		List<Agent> managerProjectDetails = agentDAO.fetchAgentsProjectId(managerName.trim());

		List<String> projectlist = new ArrayList<String>();

		for (Agent e : managerProjectDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				projectlist.add(e.getProjectId().trim());
			}
		}
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("projectlist" + projectlist);
		  }
		List<Agent> locationDetails = agentDAO.fetchAgentsLocation(managerName);

		List<String> locationlist = new ArrayList<String>();

		for (Agent e : locationDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				locationlist.add(e.getProjectId().trim());
			}
		}
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("locationlist" + locationlist);
		  }
		List<Agent> shiftDetails = agentDAO.fetchAgentsShiftTimings(managerName);

		List<String> shiftTimingslist = new ArrayList<String>();

		for (Agent e : shiftDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				shiftTimingslist.add(e.getProjectId().trim());
			}
		}
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("shiftTimingslist" + shiftTimingslist);
		  }
		ModelAndView modelAndView = new ModelAndView("dashboard");
		modelAndView.addObject("projectlist", projectlist);
		modelAndView.addObject("locationlist", locationlist);
		modelAndView.addObject("shiftTimingslist", shiftTimingslist);

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("ManagerName", managerName.trim());
		model.put("FilterMessage", "");

		modelAndView.addObject("displayList", model);

		return modelAndView;
	}

	/**
	 * Displays Agent Dashboard after successful logging in.
	 * 
	 * @param login
	 * @return Dashboard Page
	 * @throws Exception
	 */
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public ModelAndView filter(@ModelAttribute("loginParam") Agent agent) throws Exception {
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("In filter()....");
		LOGGER.info("fromDate==" + agent.getFromDate());
		LOGGER.info("toDate==" + agent.getToDate());
		LOGGER.info("Manger Name==" + agent.getHcmSupervisorName());
		LOGGER.info("Project Id==" + agent.getProjectId());
		LOGGER.info("Location==" + agent.getLocation());
		LOGGER.info("Shift Details==" + agent.getShiftTimings());
		  }
		String managerName = agent.getHcmSupervisorName();
		String projectId = agent.getProjectId();
	    String location = agent.getLocation();
		String shiftTimings = agent.getShiftTimings();

		Calendar now = Calendar.getInstance();

		int iYear = now.get(Calendar.YEAR);
		int iMonth = now.get(Calendar.MONTH); // 1 (months begin with 0)
		int iDay = now.get(Calendar.DAY_OF_MONTH);

		// Create a calendar object and set year and month
		Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

		// Get the number of days in that month
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String updatedMonth = "";
		String firstDate = "";
		String lastDate = "";

		if (((iMonth + 1) < 10)) {
			updatedMonth = "0" + String.valueOf((iMonth + 1));

		} else {
			updatedMonth = String.valueOf((iMonth + 1));

		}

		if ((iDay < 10)) {
			firstDate = "0" + String.valueOf(iDay);

		} else {
			firstDate = String.valueOf(iDay);

		}

		if (((daysInMonth) < 10)) {
			lastDate = "0" + String.valueOf(daysInMonth);

		} else {
			lastDate = String.valueOf(daysInMonth);

		}

		String fromDate = agent.getFromDate();
		String toDate = agent.getToDate();
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("fromDate" + fromDate);
		LOGGER.info("toDate" + toDate);
		  }
		String[] fromDatesplit = fromDate.split("-");
		String[] toDatesplit = toDate.split("-");
		String fromDateFormatted = fromDatesplit[2] + "/" + fromDatesplit[1] + "/" + fromDatesplit[0];
		String toDateFormatted = toDatesplit[2] + "/" + toDatesplit[1] + "/" + toDatesplit[0];
		ModelAndView modelAndView = new ModelAndView("dashboard");
		// modelAndView.addObject("agentDayWiseList",
		// agentDAO.FetchAgentsInfoDayWise(managerName.trim(),fromDate,toDate));
		List<Agent> agentOverAlldetails;
		if (projectId.trim().equalsIgnoreCase("") && location.trim().equalsIgnoreCase("")
				&& shiftTimings.trim().equalsIgnoreCase("")) {
			agentOverAlldetails = agentDAO.fetchAgentsInfoOverall(managerName.trim(), fromDate, toDate);
		} else {
			Agent agentInput=new Agent();
			agentInput.setHcmSupervisorName(managerName.trim());
			agentInput.setFromDate(fromDate);
			agentInput.setToDate(toDate);
			agentInput.setProjectId(projectId.trim());
			agentInput.setLocation(location.trim());
			agentInput.setShiftTimings(shiftTimings.trim());
			agentOverAlldetails = agentDAO.fetchAgentsInfoFilterSpecific(agentInput);
		}

		String defaultFromDate = "";
		String defaultToDate = "";

		if (!fromDate.trim().equalsIgnoreCase("")) {
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			// Convert String to Date
			Date date = sdf.parse(fromDateFormatted);
			{
				  if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Date is:" + date.toString());
				  }
			}
			defaultFromDate = new SimpleDateFormat("dd MMM yyyy").format(date);

		}
		if (!toDate.trim().equalsIgnoreCase("")) {
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(toDateFormatted);
			  if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Date is:" + date.toString());
			  }
			defaultToDate = new SimpleDateFormat("dd MMM yyyy").format(date);
		}
		List<Agent> managerProjectDetails = agentDAO.fetchAgentsProjectId(managerName.trim());

		List<String> projectlist = new ArrayList<String>();

		for (Agent e : managerProjectDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				projectlist.add(e.getProjectId().trim());
			}
		}
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("projectlist" + projectlist);
		  }

		List<Agent> locationDetails = agentDAO.fetchAgentsLocation(managerName);

		List<String> locationlist = new ArrayList<String>();

		for (Agent e : locationDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				locationlist.add(e.getProjectId().trim());
			}
		}
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("locationlist" + locationlist);
		  }
		List<Agent> shiftDetails = agentDAO.fetchAgentsShiftTimings(managerName);

		List<String> shiftTimingslist = new ArrayList<String>();

		for (Agent e : shiftDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				shiftTimingslist.add(e.getProjectId().trim());
			}
		}
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("shiftTimingslist" + shiftTimingslist);
		  }
		Map<String, Object> model = new HashMap<String, Object>();
		String filerMessage = "Agent Details From " + defaultFromDate + " To " + defaultToDate;
		/*
		 * if(!projectId.trim().equalsIgnoreCase("")) {
		 * filerMessage=filerMessage+"<BR> Project ID - "+projectId; }
		 * if(!location.trim().equalsIgnoreCase("")) {
		 * filerMessage=filerMessage+"<BR> Location - "+location; }
		 * if(!ShiftTimings.trim().equalsIgnoreCase("")) {
		 * filerMessage=filerMessage+"<BR>  Shift Timings - "+ShiftTimings; }
		 */

		model.put("DefaultFromDate", defaultFromDate);
		model.put("DefaultToDate", defaultToDate);
		model.put("ManagerName", managerName.trim());
		model.put("FilterMessage", filerMessage);
		modelAndView.addObject("agentOverAllList", agentOverAlldetails);
		modelAndView.addObject("displayList", model);
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("FilterMessage" + filerMessage);
		  }
		modelAndView.addObject("projectlist", projectlist);
		modelAndView.addObject("locationlist", locationlist);
		modelAndView.addObject("shiftTimingslist", shiftTimingslist);
		return modelAndView;
	}

	/**
	 * @param date
	 * @return
	 * Date Format conversion
	 */
	String dateFormatconversion(String date) {

		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// Convert String to Date
		Date formattedDate = null;
		try {
			formattedDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formattedDate.toString();

	}

	/**
	 * @param date
	 * @return
	 * Date Format Conversion
	 */
	String dateFormatRevert(String date) {

		DateFormat formatter = null;
		Date convertedDate = null;
		String formatDate = "";

		// Convert String to Date

		try {

			// Creating SimpleDateFormat with yyyyMMdd format e.g."20110914"

			formatter = new SimpleDateFormat("dd MMM yyyy");
			convertedDate = (Date) formatter.parse(date);
			LOGGER.info("Date from yyyyMMdd String in Java : " + convertedDate);
			formatDate = new SimpleDateFormat("yyyy-MM-dd").format(convertedDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatDate;

	}

	@RequestMapping(value = "/daytransaction", method = RequestMethod.GET)
	public ModelAndView daytransacion(@RequestParam("id") String emailId,
			@RequestParam("fromdate") String fromDateFormatted, @RequestParam("todate") String toDateFormatted)
			throws Exception {
		
		emailId = URLDecoder.decode(emailId, "UTF-8");
		fromDateFormatted = URLDecoder.decode(fromDateFormatted, "UTF-8");
		toDateFormatted = URLDecoder.decode(toDateFormatted, "UTF-8");
		  if (LOGGER.isInfoEnabled()) {
	    LOGGER.info("In daytransacion()...." + emailId);
		LOGGER.info("deocded email" + URLDecoder.decode(emailId, "UTF-8"));
		LOGGER.info("decoded from date " + URLDecoder.decode(fromDateFormatted, "UTF-8"));
		LOGGER.info("decoded To date " + URLDecoder.decode(toDateFormatted, "UTF-8"));

		LOGGER.info("agentDAO" + agentDAO);
		LOGGER.info("emailId" + emailId);
		
		  }
		int count = 0;


		List<Agent> agentdaywisedetails = agentDAO.fetchAgentsInfoDayWise(emailId.trim(),
				dateFormatRevert(fromDateFormatted), dateFormatRevert(toDateFormatted));
		count = 0;
	 
		String loginDate = "";

		String defaultFromDate = "";
		String defaultToDate = "";
		String defaultCurrentDate = "";
		String defaultAgentEmailId = "";

		if (!emailId.trim().equalsIgnoreCase("")) {
			defaultAgentEmailId = emailId;
		}
		if (!fromDateFormatted.trim().equalsIgnoreCase("")) {

			defaultFromDate = fromDateFormatted;

		}
		if (!toDateFormatted.trim().equalsIgnoreCase("")) {

			defaultToDate = toDateFormatted;
		}

		if (!loginDate.trim().equalsIgnoreCase("")) {
			String[] splitdate = loginDate.split("-");
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(splitdate[2] + "/" + splitdate[1] + "/" + splitdate[0]);
			  if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Date is:" + date.toString());
			  }
			defaultCurrentDate = new SimpleDateFormat("dd MMM yyyy").format(date);
		}

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("DefaultFromDate", fromDateFormatted);
		model.put("DefaultToDate", toDateFormatted);

		model.put("DefaultAgentEmailId", defaultAgentEmailId);
		ModelAndView modelAndView = new ModelAndView("daytransaction");
		modelAndView.addObject("displayList", model);

		modelAndView.addObject("agentDayWiseList", agentdaywisedetails);

		return modelAndView;
	}

	@RequestMapping(value = "/idletransaction", method = RequestMethod.GET)
	public ModelAndView ildetransacion(@RequestParam("id") String emailId, @RequestParam("date") String transactiondate,
			@RequestParam("fromdate") String fromDateFormatted, @RequestParam("todate") String toDateFormatted)
			throws Exception {

		emailId = URLDecoder.decode(emailId, "UTF-8");
		fromDateFormatted = URLDecoder.decode(fromDateFormatted, "UTF-8");
		toDateFormatted = URLDecoder.decode(toDateFormatted, "UTF-8");
		transactiondate = URLDecoder.decode(transactiondate, "UTF-8");
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("deocded email" + URLDecoder.decode(emailId, "UTF-8"));
		LOGGER.info("decoded from date " + URLDecoder.decode(fromDateFormatted, "UTF-8"));
		LOGGER.info("decoded To date " + URLDecoder.decode(toDateFormatted, "UTF-8"));
		LOGGER.info("decoded transactiondate" + URLDecoder.decode(transactiondate, "UTF-8"));
		  }
		List<Agent> agenttransactiondetails = agentDAO.fetchAgentsLoginLogoutTime(emailId.trim(),
				dateFormatRevert(transactiondate));
		ModelAndView modelAndView = new ModelAndView("idletransaction");
		String loginTime = "";
		String logoutTime = "";
		String date = "";
		for (Agent e : agenttransactiondetails) {

			date = e.getDATE();
			loginTime = e.getLoginTime();
			logoutTime = e.getLogoutTime();

		}

	 
 
		String defaultAgentEmailId = "";

		if (!emailId.trim().equalsIgnoreCase("")) {
			defaultAgentEmailId = emailId;
		}
		  if (LOGGER.isInfoEnabled()) {
		LOGGER.info("email id" + defaultAgentEmailId);
		  }
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("DefaultAgentEmailId", emailId.trim());
		model.put("DefaultFromDate", fromDateFormatted);
		model.put("DefaultToDate", toDateFormatted);
		model.put("DefaultCurrentDate", transactiondate);

		modelAndView.addObject("displayList", model);

		modelAndView.addObject("agentTransactionList",
				agentDAO.fetchAgentsTransacation(emailId.trim(), loginTime, logoutTime));

		return modelAndView;
		// Dashboard Page
	}

}

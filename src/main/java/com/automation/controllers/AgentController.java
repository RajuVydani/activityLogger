///////////////////////////////////////////////////
//HEADER
///////////////////////////////////////////////////

package com.automation.controllers;

import com.automation.idao.IAgentDAO;
import com.automation.model.Login;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AgentController {

	@Autowired
	private IAgentDAO agentDAO;

	private static final Logger logger = Logger.getLogger(AgentController.class);

	/**
	 * Displays Policy update page. New policy updates will be feeded in to the
	 * system.
	 * 
	 * @return policy update page
	 * @throws Exception
	 */
	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public ModelAndView policy() throws Exception {
		logger.info("In policy()....");
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
		logger.info("In authenticate()....");

		String managerName = login.getUsername();
		List<Agent> managerProjectDetails = agentDAO.FetchAgentsProjectId(managerName.trim());

		List<String> projectlist = new ArrayList<String>();

		for (Agent e : managerProjectDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				projectlist.add(e.getProjectId().trim());
			}
		}
		logger.info("projectlist" + projectlist);

		List<Agent> locationDetails = agentDAO.FetchAgentsLocation(managerName);

		List<String> locationlist = new ArrayList<String>();

		for (Agent e : locationDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				locationlist.add(e.getProjectId().trim());
			}
		}

		logger.info("locationlist" + locationlist);

		List<Agent> ShiftDetails = agentDAO.FetchAgentsShiftTimings(managerName);

		List<String> shiftTimingslist = new ArrayList<String>();

		for (Agent e : ShiftDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				shiftTimingslist.add(e.getProjectId().trim());
			}
		}

		logger.info("shiftTimingslist" + shiftTimingslist);

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
		logger.info("In filter()....");
		logger.info("fromDate==" + agent.getFromDate());
		logger.info("toDate==" + agent.getToDate());
		logger.info("Manger Name==" + agent.getHcmSupervisor());
		logger.info("Project Id==" + agent.getProjectId());
		logger.info("Location==" + agent.getLocation());
		logger.info("Shift Details==" + agent.getShiftTimings());
		String managerName = agent.getHcmSupervisor();
		String projectId = agent.getProjectId();
	    String location = agent.getLocation();
		String ShiftTimings = agent.getShiftTimings();

		Calendar now = Calendar.getInstance();

		int iYear = now.get(Calendar.YEAR);
		int iMonth = now.get(Calendar.MONTH); // 1 (months begin with 0)
		int iDay = now.get(Calendar.DAY_OF_MONTH);

		// Create a calendar object and set year and month
		Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

		// Get the number of days in that month
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String updated_month = "";
		String firstDate = "";
		String lastDate = "";

		if (((iMonth + 1) < 10)) {
			updated_month = "0" + String.valueOf((iMonth + 1));

		} else {
			updated_month = String.valueOf((iMonth + 1));

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
		logger.info("fromDate" + fromDate);
		logger.info("toDate" + toDate);
		String[] fromDatesplit = fromDate.split("-");
		String[] toDatesplit = toDate.split("-");
		String fromDateFormatted = fromDatesplit[2] + "/" + fromDatesplit[1] + "/" + fromDatesplit[0];
		String toDateFormatted = toDatesplit[2] + "/" + toDatesplit[1] + "/" + toDatesplit[0];
		ModelAndView modelAndView = new ModelAndView("dashboard");
		// modelAndView.addObject("agentDayWiseList",
		// agentDAO.FetchAgentsInfoDayWise(managerName.trim(),fromDate,toDate));
		List<Agent> agentOverAlldetails;
		if (projectId.trim().equalsIgnoreCase("") && location.trim().equalsIgnoreCase("")
				&& ShiftTimings.trim().equalsIgnoreCase("")) {
			agentOverAlldetails = agentDAO.FetchAgentsInfoOverall(managerName.trim(), fromDate, toDate);
		} else {
			agentOverAlldetails = agentDAO.FetchAgentsInfoFilterSpecific(managerName.trim(), fromDate, toDate,
					projectId.trim(), location.trim(), ShiftTimings.trim());
		}

		String DefaultFromDate = "";
		String DefaultToDate = "";

		if (!fromDate.trim().equalsIgnoreCase("")) {
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			// Convert String to Date
			Date date = sdf.parse(fromDateFormatted);
			logger.info("Date is:" + date.toString());
			DefaultFromDate = new SimpleDateFormat("dd MMM yyyy").format(date);

		}
		if (!toDate.trim().equalsIgnoreCase("")) {
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(toDateFormatted);
			logger.info("Date is:" + date.toString());
			DefaultToDate = new SimpleDateFormat("dd MMM yyyy").format(date);
		}
		List<Agent> managerProjectDetails = agentDAO.FetchAgentsProjectId(managerName.trim());

		List<String> projectlist = new ArrayList<String>();

		for (Agent e : managerProjectDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				projectlist.add(e.getProjectId().trim());
			}
		}
		logger.info("projectlist" + projectlist);

		List<Agent> locationDetails = agentDAO.FetchAgentsLocation(managerName);

		List<String> locationlist = new ArrayList<String>();

		for (Agent e : locationDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				locationlist.add(e.getProjectId().trim());
			}
		}

		logger.info("locationlist" + locationlist);

		List<Agent> ShiftDetails = agentDAO.FetchAgentsShiftTimings(managerName);

		List<String> shiftTimingslist = new ArrayList<String>();

		for (Agent e : ShiftDetails) {

			if (!e.getProjectId().trim().equalsIgnoreCase("")) {
				shiftTimingslist.add(e.getProjectId().trim());
			}
		}

		logger.info("shiftTimingslist" + shiftTimingslist);
		Map<String, Object> model = new HashMap<String, Object>();
		String filerMessage = "Agent Details From " + DefaultFromDate + " To " + DefaultToDate;
		/*
		 * if(!projectId.trim().equalsIgnoreCase("")) {
		 * filerMessage=filerMessage+"<BR> Project ID - "+projectId; }
		 * if(!location.trim().equalsIgnoreCase("")) {
		 * filerMessage=filerMessage+"<BR> Location - "+location; }
		 * if(!ShiftTimings.trim().equalsIgnoreCase("")) {
		 * filerMessage=filerMessage+"<BR>  Shift Timings - "+ShiftTimings; }
		 */

		model.put("DefaultFromDate", DefaultFromDate);
		model.put("DefaultToDate", DefaultToDate);
		model.put("ManagerName", managerName.trim());
		model.put("FilterMessage", filerMessage);
		modelAndView.addObject("agentOverAllList", agentOverAlldetails);
		modelAndView.addObject("displayList", model);

		logger.info("FilterMessage" + filerMessage);
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
		Date Formatteddate = null;
		try {
			Formatteddate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Formatteddate.toString();

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
			System.out.println("Date from yyyyMMdd String in Java : " + convertedDate);
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
		logger.info("In daytransacion()...." + emailId);
		emailId = URLDecoder.decode(emailId, "UTF-8");
		fromDateFormatted = URLDecoder.decode(fromDateFormatted, "UTF-8");
		toDateFormatted = URLDecoder.decode(toDateFormatted, "UTF-8");

		logger.info("deocded email" + URLDecoder.decode(emailId, "UTF-8"));
		logger.info("decoded from date " + URLDecoder.decode(fromDateFormatted, "UTF-8"));
		logger.info("decoded To date " + URLDecoder.decode(toDateFormatted, "UTF-8"));

		int count = 0;

		logger.info("agentDAO" + agentDAO);
		logger.info("emailId" + emailId);

		List<Agent> agentdaywisedetails = agentDAO.FetchAgentsInfoDayWise(emailId.trim(),
				dateFormatRevert(fromDateFormatted), dateFormatRevert(toDateFormatted));
		count = 0;
		String loginTime = "";
		String logoutTime = "";
		String DATE = "";

		String DefaultFromDate = "";
		String DefaultToDate = "";
		String DefaultCurrentDate = "";
		String DefaultAgentEmailId = "";

		if (!emailId.trim().equalsIgnoreCase("")) {
			DefaultAgentEmailId = emailId;
		}
		if (!fromDateFormatted.trim().equalsIgnoreCase("")) {

			DefaultFromDate = fromDateFormatted;

		}
		if (!toDateFormatted.trim().equalsIgnoreCase("")) {

			DefaultToDate = toDateFormatted;
		}

		if (!DATE.trim().equalsIgnoreCase("")) {
			String[] splitdate = DATE.split("-");
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(splitdate[2] + "/" + splitdate[1] + "/" + splitdate[0]);
			logger.info("Date is:" + date.toString());
			DefaultCurrentDate = new SimpleDateFormat("dd MMM yyyy").format(date);
		}

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("DefaultFromDate", fromDateFormatted);
		model.put("DefaultToDate", toDateFormatted);

		model.put("DefaultAgentEmailId", DefaultAgentEmailId);
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

		logger.info("deocded email" + URLDecoder.decode(emailId, "UTF-8"));
		logger.info("decoded from date " + URLDecoder.decode(fromDateFormatted, "UTF-8"));
		logger.info("decoded To date " + URLDecoder.decode(toDateFormatted, "UTF-8"));
		logger.info("decoded transactiondate" + URLDecoder.decode(transactiondate, "UTF-8"));

		List<Agent> agenttransactiondetails = agentDAO.FetchAgentsLoginLogoutTime(emailId.trim(),
				dateFormatRevert(transactiondate));
		ModelAndView modelAndView = new ModelAndView("idletransaction");
		String loginTime = "";
		String logoutTime = "";
		String DATE = "";
		for (Agent e : agenttransactiondetails) {

			DATE = e.getDATE();
			loginTime = e.getLoginTime();
			logoutTime = e.getLogoutTime();

		}

		String DefaultAgentName = "";
		String DefaultFromDate = "";
		String DefaultToDate = "";
		String DefaultCurrentDate = "";
		String DefaultAgentEmailId = "";

		if (!emailId.trim().equalsIgnoreCase("")) {
			DefaultAgentEmailId = emailId;
		}

		logger.info("email id" + DefaultAgentEmailId);
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("DefaultAgentEmailId", emailId.trim());
		model.put("DefaultFromDate", fromDateFormatted);
		model.put("DefaultToDate", toDateFormatted);
		model.put("DefaultCurrentDate", transactiondate);

		modelAndView.addObject("displayList", model);

		modelAndView.addObject("agentTransactionList",
				agentDAO.FetchAgentsTransacation(emailId.trim(), loginTime, logoutTime));

		return modelAndView;
		// Dashboard Page
	}

}

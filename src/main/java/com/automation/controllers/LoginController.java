package com.automation.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.automation.idao.IAgentDAO;
import com.automation.model.Login;
import com.automation.util.AppConstants;
import com.automation.vo.Agent;

/**
 * @author Raju Vydani _581345 LoginController
 */
@Controller
public class LoginController {

	private final static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private IAgentDAO agentDAO;

	/**
	 * Displays login page.
	 * 
	 * @param model
	 * @return login page
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(ModelMap model) throws Exception {
		logger.info("In login()....");
		Login login2 = new Login();
		login2.setUsername("Enter Username");
		login2.setPassword("Enter Password");
		model.addAttribute("loginAtt", login2);
		// Login Page
		return new ModelAndView("login");
		// dashboard
		/*
		 * model.addAttribute("dashboardActive", AppConstants.active); return
		 * new ModelAndView("dashboard");
		 */
	}

}

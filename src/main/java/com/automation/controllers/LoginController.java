package com.automation.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.automation.model.Login;
import com.automation.util.AppConstants;

/**
 * @author Raju Vydani _581345
 * LoginController
 */
@Controller
public class LoginController {

	private final static Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * Displays login page.
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
		//Login Page
		return new ModelAndView("login");
		//dashboard
		/*model.addAttribute("dashboardActive", AppConstants.active);
		return new ModelAndView("dashboard");*/
	}
	
	/**
	 * Displays Agent Dashboard after successful logging in.
	 * @param login
	 * @return Dashboard Page
	 * @throws Exception
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ModelAndView authenticate(@ModelAttribute("loginParam") Login login) throws Exception {
		logger.info("In authenticate()....");
		ModelAndView model = new ModelAndView("dashboard");
		model.addObject("dashboardActive", AppConstants.active);
		return model;
	}
	
	/**
	 * Display Agents dashboard which has all the agents data.
	 * @return Dashboard Page
	 * @throws Exception
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() throws Exception {
		logger.info("In dashboard()....");
		ModelAndView model = new ModelAndView("dashboard");
		model.addObject("dashboardActive", AppConstants.active);
		return model;
	}
}

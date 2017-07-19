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

@Controller
public class LoginController {

	private final static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(ModelMap model) throws Exception {
		logger.info("In login()....");
		Login login2 = new Login();
		login2.setUsername("Enter Username");
		login2.setPassword("Enter Password");
		model.addAttribute("loginAtt", login2);
		return new ModelAndView("login");
		// Login Page
	}
	/*
	 * @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	 * public ModelAndView authenticate(@ModelAttribute("loginParam") Login
	 * login) throws Exception { logger.info("In authenticate()....");
	 * //logger.info("loggedIn user::" + login.getUsername());
	 * //model.addAttribute("login", new Login()); return new
	 * ModelAndView("dashboard"); //Dashboard Page }
	 */

}

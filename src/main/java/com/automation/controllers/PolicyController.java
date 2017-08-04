package com.automation.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.automation.idao.IPolicyDAO;
import com.automation.model.Login;
import com.automation.model.Policy;
import com.automation.util.AppConstants;

@Controller
public class PolicyController {

	private final static Logger logger = Logger.getLogger(PolicyController.class);
	@Autowired
	private IPolicyDAO policyDAO;

	/*
	 * @RequestMapping(value = "/", method = RequestMethod.GET) public
	 * ModelAndView login(ModelMap model) throws Exception {
	 * logger.info("In login()...."); Login login2 = new Login();
	 * login2.setUsername("Enter Username");
	 * login2.setPassword("Enter Password"); model.addAttribute("loginAtt",
	 * login2); return new ModelAndView("login"); // Login Page }
	 */

	/*
	 * @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	 * public ModelAndView authenticate(@ModelAttribute("loginParam") Login
	 * login) throws Exception { logger.info("In authenticate()...."); //
	 * logger.info("loggedIn user::" + login.getUsername()); //
	 * model.addAttribute("login", new Login()); return new
	 * ModelAndView("policyupdate"); // Dashboard Page }
	 */

	/**
	 * @param policy
	 * @param model
	 * @return Policy Update Page
	 * @throws Exception
	 */
	@RequestMapping(value = "/policyupdate", method = RequestMethod.POST)
	public ModelAndView policyupdate(@ModelAttribute("policyParam") Policy policy, ModelMap model) throws Exception {
		logger.info("In policyupdate()....");

		int policyInsertStatus = policyDAO.newPolicy(policy);
		Policy policystatus = new Policy();
		if (policyInsertStatus == 1) {
			int policyFlagStatus = policyDAO.updatePolicyFlagInAgentMaster();
			if (policyFlagStatus >= 0) {
				policystatus.setPolicyUpdateStatus("success");

			} else {
				policystatus.setPolicyUpdateStatus("failure");
			}

		} else {

			policystatus.setPolicyUpdateStatus("failure");

		}
		model.addAttribute("policyStatus", policystatus);
		return new ModelAndView("policyupdatestatus");
		// Dashboard Page
	}
}

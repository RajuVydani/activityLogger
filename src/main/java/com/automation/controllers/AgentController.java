package com.automation.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.automation.util.AppConstants;

/**
 * @author Raju Vydani _581345
 *	Agent Controller
 */
@Controller
public class AgentController {
	
	private final static Logger logger = Logger.getLogger(AgentController.class);
	
	/**
	 * Displays Policy update page.
	 * New policy updates will be feeded in to the system.
	 * @return policy update page
	 * @throws Exception
	 */
	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public ModelAndView policy() throws Exception {
		logger.info("In policy()....");
		ModelAndView model = new ModelAndView("policyupdate");
		model.addObject("policyUpdateActive", AppConstants.active);
		return model;
		//Policy Update Page
	}
}

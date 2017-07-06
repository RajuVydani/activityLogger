package com.automation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.automation.vo.Agent;

@Controller
public class AgentController {
	
	@RequestMapping("/agent/getData.htm")
	public ModelAndView getData(@ModelAttribute("Agent") Agent agent) throws Exception {
 
		return new ModelAndView("agentPage", "msg","getData() method");
 
	}

}

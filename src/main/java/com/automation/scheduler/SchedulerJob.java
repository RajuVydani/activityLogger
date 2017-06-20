package com.automation.scheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.automation.dao.AgentDAO;
 
import com.automation.vo.Agent;

public class SchedulerJob implements Job {


	 
	
	private final static Logger logger = Logger.getLogger(SchedulerJob.class);
	
	static{
		init();
	}
	
 
	
	/**
	 * method to init log4j configurations
	 */
	private static void init() {
		DOMConfigurator.configure("log4j.xml");
	}

	 

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("Quartz 2 example");
		 scheduler();

	}
	public static void main(String[] args) {

		if(logger.isDebugEnabled()){
			logger.debug("getWelcome is executed!");
		}
	}
public void scheduler()
{
	
	if(logger.isDebugEnabled()){
		logger.debug("getWelcome is executed=======Scheduler!");
	}

 
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

	 

	try {

		logger.info("Reading Temporary Table");
		AgentDAO dao = (AgentDAO) context.getBean("agentDAO");
		/////////////////////////READING CHROME TEMPORARY TABLE///////////////////		
		List<Agent> chromtemlist = dao.readChromTempTable();

		for (Agent e : chromtemlist) {
			logger.info(e.getEmailId());
			logger.info(e.getName());
			logger.info(e.getIdleFrom());
			logger.info(e.getIdleTo());
			logger.info(e.getWebsitesVisited());
			List<Agent> agentdetails = dao.readAgentDetailsFromAgentMaster(e.getEmailId());
			String agentName = "";
			String ShiftTimings = "";
			for (Agent e1 : agentdetails) {
				agentName = e1.getName();
				ShiftTimings = e1.getShiftTimings();

			}

			String errorDesc = "";
			if (agentName.trim().equalsIgnoreCase("")) {
				errorDesc = "Agent Name";

			}

			if (ShiftTimings.trim().equalsIgnoreCase("")) {
				if (errorDesc.trim().equalsIgnoreCase("")) {
					errorDesc = "Shift Timings";
				} else {

					errorDesc = errorDesc + ",Shift Timings";
				}

			}
			logger.info("errorDesc" + errorDesc);
			if (errorDesc.trim().equalsIgnoreCase("")) {

				int count = dao.totalAgentCountInDayMaster(e.getEmailId(), "2016-06-18");
				logger.info("count quwery====" + count);
				if (count == 0) {

					Agent dataInsert = new Agent();
					dataInsert.setDATE("2016-06-18");
					dataInsert.setEmailId(e.getEmailId());
					dataInsert.setShiftTimings(ShiftTimings);
					dataInsert.setName(agentName);
					int status = dao.dataInsertionInDayMaster(dataInsert);

				}

				Agent dataInsert = new Agent();
				dataInsert.setDATE("2016-06-18");
				dataInsert.setEmailId(e.getEmailId());
				dataInsert.setName(agentName);
				dataInsert.setShiftTimings(ShiftTimings);
				dataInsert.setIdleFrom(e.getIdleFrom());
				dataInsert.setIdleTo(e.getIdleTo());
				dataInsert.setWebsitesVisited(e.getWebsitesVisited());

				int status = dao.dataInsertionInDayDetail(dataInsert);

				logger.info("status===" + status);

				if (status == 1) {
					Agent dataDelete = new Agent();

					dataDelete.setEmailId(e.getEmailId());

					dataDelete.setIdleFrom(e.getIdleFrom());
					dao.deleteFromChromeTemp(dataDelete);
				}

			}

			else {
				Agent dataInsert = new Agent();
				 
				dataInsert.setEmailId(e.getEmailId());
				dataInsert.setName(e.getName());
	 
				dataInsert.setIdleFrom(e.getIdleFrom());
				dataInsert.setIdleTo(e.getIdleTo());
				dataInsert.setWebsitesVisited(e.getWebsitesVisited());
				dataInsert.setErrorDesc(errorDesc+" missing in Agent Master");
				int status = dao.dataInsertionInException(dataInsert);

				logger.info("status===" + status);

				if (status == 1) {
					Agent dataDelete = new Agent();

					dataDelete.setEmailId(e.getEmailId());

					dataDelete.setIdleFrom(e.getIdleFrom());
					dao.deleteFromChromeTemp(dataDelete);
				}
	 
			}
		}
		
		
		
		/////////////////////////READING CHROME EXCEPTION TABLE///////////////////
		
		List<Agent> chromeExceplist = dao.readChromExceptionTable();

		for (Agent e : chromeExceplist) {
			logger.info(e.getEmailId());
			logger.info(e.getName());
			logger.info(e.getIdleFrom());
			logger.info(e.getIdleTo());
			logger.info(e.getWebsitesVisited());
			List<Agent> agentdetails = dao.readAgentDetailsFromAgentMaster(e.getEmailId());
			String agentName = "";
			String ShiftTimings = "";
			for (Agent e1 : agentdetails) {
				agentName = e1.getName();
				ShiftTimings = e1.getShiftTimings();

			}

			String errorDesc = "";
			if (agentName.trim().equalsIgnoreCase("")) {
				errorDesc = "Agent Name";

			}

			if (ShiftTimings.trim().equalsIgnoreCase("")) {
				if (errorDesc.trim().equalsIgnoreCase("")) {
					errorDesc = "Shift Timings";
				} else {

					errorDesc = errorDesc + ",Shift Timings";
				}

			}
			logger.info("errorDesc" + errorDesc);
			if (errorDesc.trim().equalsIgnoreCase("")) {

				int count = dao.totalAgentCountInDayMaster(e.getEmailId(), "2016-06-18");
				logger.info("count quwery====" + count);
				if (count == 0) {

					Agent dataInsert = new Agent();
					dataInsert.setDATE("2016-06-18");
					dataInsert.setEmailId(e.getEmailId());
					dataInsert.setShiftTimings(ShiftTimings);
					dataInsert.setName(agentName);
					int status = dao.dataInsertionInDayMaster(dataInsert);

				}

				Agent dataInsert = new Agent();
				dataInsert.setDATE("2016-06-18");
				dataInsert.setEmailId(e.getEmailId());
				dataInsert.setName(agentName);
				dataInsert.setShiftTimings(ShiftTimings);
				dataInsert.setIdleFrom(e.getIdleFrom());
				dataInsert.setIdleTo(e.getIdleTo());
				dataInsert.setWebsitesVisited(e.getWebsitesVisited());

				int status = dao.dataInsertionInDayDetail(dataInsert);

				logger.info("status===" + status);

				if (status == 1) {
					Agent dataDelete = new Agent();

					dataDelete.setEmailId(e.getEmailId());

					dataDelete.setIdleFrom(e.getIdleFrom());
					dao.deleteFromChromeException(dataDelete);
				}

			}

			else {
			 
	 
			}
		}
		
		////////////////////////////// Update Idle Hrs//////////////////////
		
		List<Agent> idlehrslist = dao.CalculateIdleHrs();
		for (Agent e : idlehrslist) {
			Agent dataInsert = new Agent();
			dataInsert.setDATE("2016-06-18");
			dataInsert.setEmailId(e.getEmailId());
dataInsert.setIdleHours(e.getIdleHours());

			int status = dao.updateIdleHrsInDayDetail(dataInsert);
		
		
		}
		
		
		
		

	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error("Exception" + e);
		e.printStackTrace();
	}

	
}
}

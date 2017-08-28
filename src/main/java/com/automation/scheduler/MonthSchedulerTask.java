package com.automation.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.automation.dao.AgentDAO;
import com.automation.idao.IAgentDAO;
import com.automation.vo.Agent;

public class MonthSchedulerTask {
	@Autowired
	private IAgentDAO agentDAO;
	private final static Logger logger = Logger.getLogger(AgentDAO.class);

	public void scheduler() {

		try {
			logger.info("*******************************MONTH SCHEDULER STARTED*************************************");

			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
			int day = now.get(Calendar.DAY_OF_MONTH);
			logger.info("Current date = " + day);
			logger.info("Current Month = " + month);
			logger.info("Current Year = " + year);
			Agent dayMasterInput = new Agent();
			dayMasterInput.setMonth(String.valueOf(month));
			dayMasterInput.setYear(String.valueOf(year));

			List<Agent> dayMasterDetails = agentDAO.readDayMaster(dayMasterInput);

			for (Agent agentTransaction : dayMasterDetails) {

				int count = agentDAO.monthMasterCount(agentTransaction);
				if (count == 0) {

					int insertCount = agentDAO.monthMasterInsert(agentTransaction);
					if (insertCount >= 0) {
						logger.info("Data is Successfully inserted in Month Mater Table");
						logger.info("No Of Rows Inserted :" + insertCount);

					}
				} else {

					int updateCount = agentDAO.monthMasterUpdate(agentTransaction);
					if (updateCount >= 0) {
						logger.info("Data is Successfully updated in Month Mater Table");
						logger.info("No Of Rows updated :" + updateCount);

					}
				}

			}
			if (day <= 3) {

				if (month == 1) {
					month = 12;
					year = year - 1;
				} else {
					month = month - 1;
				}
				logger.info("Previous Month = " + month);
				logger.info(" Year = " + year);

				Agent dayMasterInputForPreviousMonth = new Agent();
				dayMasterInputForPreviousMonth.setMonth(String.valueOf(month));
				dayMasterInputForPreviousMonth.setYear(String.valueOf(year));

				List<Agent> dayMasterDetailsForPreviousMonth = agentDAO
						.readDayMaster(dayMasterInputForPreviousMonth);

				for (Agent agentTransactionForPreviousMonth : dayMasterDetailsForPreviousMonth) {

					int count = agentDAO.monthMasterCount(agentTransactionForPreviousMonth);
					if (count == 0) {

						int insertCount = agentDAO.monthMasterInsert(agentTransactionForPreviousMonth);
						if (insertCount >= 0) {
							logger.info("Data is Successfully inserted in Month Mater Table");
							logger.info("No Of Rows Inserted :" + insertCount);

						}
					} else {

						int updateCount = agentDAO.monthMasterUpdate(agentTransactionForPreviousMonth);
						if (updateCount >= 0) {
							logger.info("Data is Successfully updated in Month Mater Table");
							logger.info("No Of Rows updated :" + updateCount);

						}
					}

				}

			}

			logger.info(
					"*******************************MONTH SCHEDULER COMPLETED*************************************");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception Occured in Scheduler" + e);
			e.printStackTrace();
		}
	}

	/**
	 * @param dateStart
	 * @param dateStop
	 * @return Difference between two datetime
	 */

}
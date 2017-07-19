package com.automation.scheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.automation.dao.AgentDAO;
import com.automation.idao.IAgentDAO;
import com.automation.idao.IPolicyDAO;
import com.automation.vo.Agent;

public class SchedulerTask {
	@Autowired
	private IAgentDAO agentDAO;
 
 
	private final static Logger logger = Logger.getLogger(SchedulerTask.class);
	public void scheduler() {

	 

		try {

			logger.info("Reading Temporary Table");
		 
			///////////////////////// READING CHROME TEMPORARY
			///////////////////////// TABLE///////////////////
			List<Agent> chromtemlist = agentDAO.readChromTempMasterTable();

			for (Agent e : chromtemlist) {

				List<Agent> agentdetails = agentDAO.readAgentDetailsFromAgentMaster(e.getEmailId().replaceAll("\\s+",""));
				String agentName = "";
				String ShiftTimings = "";
				logger.info("Email ID :"+e.getEmailId().replaceAll("\\s+",""));
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
			
				if (errorDesc.trim().equalsIgnoreCase("")) {

					String LoginDate[] = e.getLoginTime().split(" ");
					int count = agentDAO.totalAgentCountInDayMaster(e.getEmailId(), LoginDate[0]);
			 
					if (count == 0) {

						Agent dataInsert = new Agent();
						dataInsert.setDATE(LoginDate[0]);
						dataInsert.setEmailId(e.getEmailId());
						dataInsert.setShiftTimings(ShiftTimings);
						dataInsert.setName(agentName);
						dataInsert.setLoginTime(e.getLoginTime());
						dataInsert.setLogoutTime(e.getLogoutTime());
						
						String seconds=e.getProductiveHours();
						float minutes=(Float.parseFloat(seconds)/60);
						float hours = (minutes / 60);					 
						
						dataInsert.setProductiveHours( String.valueOf(hours));

						int status = agentDAO.dataInsertionInDayMaster(dataInsert);
						if (status >= 0) {
							Agent dataDelete = new Agent();
							dataDelete.setEmailId(e.getEmailId());
							dataDelete.setLoginTime(e.getLoginTime());

							int deleteStatus = agentDAO.deleteFromChromeTempMaster(dataDelete);
							if (deleteStatus >= 0) {

								Agent dataInsertInDayDetail = new Agent();
								dataInsertInDayDetail.setEmailId(e.getEmailId());
								dataInsertInDayDetail.setLogoutTime(e.getLogoutTime());

								int dataInsertInDayDetailStatus = agentDAO.dataInsertionInDayDetail(dataInsertInDayDetail);
								if (dataInsertInDayDetailStatus >= 0) {

									Agent dataDeletefromChromTempDetails = new Agent();

									int dataDeletionInDayDetailStatus = agentDAO
											.deleteFromChromeTempDetail(dataInsertInDayDetail);
									if (dataDeletionInDayDetailStatus >= 0) {

										Agent idleHrsCalculation = new Agent();
										idleHrsCalculation.setEmailId(e.getEmailId());
										idleHrsCalculation.setLogoutTime(e.getLogoutTime());
										idleHrsCalculation.setLoginTime(e.getLoginTime());
										List<Agent> idlehrslist = agentDAO.CalculateIdleHrs(idleHrsCalculation);
										for (Agent i1 : idlehrslist) {
											Agent idleHrsUpdation = new Agent();
											idleHrsUpdation.setEmailId(e.getEmailId());
											idleHrsUpdation.setDATE(LoginDate[0]);
											idleHrsUpdation.setIdleHours(i1.getIdleHours());

											int idleHrsUpdationInDayMaster = agentDAO
													.updateIdleHrsInDayMaster(idleHrsUpdation);
											if (idleHrsUpdationInDayMaster >= 0) {

											}

										}

									}

								}
							}

						}
					}

				}

				else {
					logger.info("errorDesc" + errorDesc);
					Agent dataInsert = new Agent();

					dataInsert.setEmailId(e.getEmailId());

					dataInsert.setName(e.getName());
					dataInsert.setLoginTime(e.getLoginTime());
					dataInsert.setLogoutTime(e.getLogoutTime());

					String seconds=e.getProductiveHours();
					float minutes=(Float.parseFloat(seconds)/60);
					float hours = (minutes / 60);					 
					
					dataInsert.setProductiveHours( String.valueOf(hours));
				

					dataInsert.setErrorDesc("Email Id is missing in Agent Master");
					int status = agentDAO.dataInsertionInException(dataInsert);
 

					if (status >= 0) {
						Agent dataDelete = new Agent();
						dataDelete.setEmailId(e.getEmailId());
						dataDelete.setLoginTime(e.getLoginTime());

						int deleteStatus = agentDAO.deleteFromChromeTempMaster(dataDelete);
						if (deleteStatus >= 0) {

							Agent dataInsertInDayDetail = new Agent();
							dataInsertInDayDetail.setEmailId(e.getEmailId());
							dataInsertInDayDetail.setLogoutTime(e.getLogoutTime());

							int dataInsertInDayDetailStatus = agentDAO.dataInsertionInDayDetail(dataInsertInDayDetail);
							if (dataInsertInDayDetailStatus >= 0) {
								int dataDeletionInDayDetailStatus = agentDAO
										.deleteFromChromeTempDetail(dataInsertInDayDetail);
								if (dataDeletionInDayDetailStatus >= 0) {

								}

							}
						}
					}

				}
			}

			///////////////////////// READING CHROME EXCEPTION
			///////////////////////// TABLE///////////////////
			logger.info("Reading Temporary Table");
			List<Agent> chromExceplist = agentDAO.readChromExceptionTable();

			for (Agent e : chromExceplist) {
				logger.info("Email ID :"+e.getEmailId().replaceAll("\\s+",""));
				List<Agent> agentdetails = agentDAO.readAgentDetailsFromAgentMaster(e.getEmailId().replaceAll("\\s+",""));
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
			 
				if (errorDesc.trim().equalsIgnoreCase("")) {

					String LoginDate[] = e.getLoginTime().split(" ");
					int count = agentDAO.totalAgentCountInDayMaster(e.getEmailId(), LoginDate[0]);
		 
					if (count == 0) {

						Agent dataInsert = new Agent();
						dataInsert.setDATE(LoginDate[0]);
						dataInsert.setEmailId(e.getEmailId());
						dataInsert.setShiftTimings(ShiftTimings);
						dataInsert.setName(agentName);
						dataInsert.setLoginTime(e.getLoginTime());
						dataInsert.setLogoutTime(e.getLogoutTime());
						dataInsert.setProductiveHours(e.getProductiveHours());

						int status = agentDAO.dataInsertionInDayMaster(dataInsert);
						if (status >= 0) {
							Agent dataDelete = new Agent();
							dataDelete.setEmailId(e.getEmailId());
							dataDelete.setLoginTime(e.getLoginTime());

							int deleteStatus = agentDAO.deleteFromChromeException(dataDelete);
							if (deleteStatus >= 0) {

								Agent dataInsertInDayDetail = new Agent();
								dataInsertInDayDetail.setEmailId(e.getEmailId());
								dataInsertInDayDetail.setLogoutTime(e.getLogoutTime());

								Agent idleHrsCalculation = new Agent();
								idleHrsCalculation.setEmailId(e.getEmailId());
								idleHrsCalculation.setLogoutTime(e.getLogoutTime());
								idleHrsCalculation.setLoginTime(e.getLoginTime());
								List<Agent> idlehrslist = agentDAO.CalculateIdleHrs(idleHrsCalculation);
								for (Agent i1 : idlehrslist) {
									Agent idleHrsUpdation = new Agent();
									idleHrsUpdation.setEmailId(e.getEmailId());
									idleHrsUpdation.setDATE(LoginDate[0]);
									idleHrsUpdation.setIdleHours(i1.getIdleHours());

									int idleHrsUpdationInDayMaster = agentDAO.updateIdleHrsInDayMaster(idleHrsUpdation);
									if (idleHrsUpdationInDayMaster == 1) {

									}

								}
							}

						}
					}

				}

				else {

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception Occured in Scheduler" + e);
			e.printStackTrace();
		}
	}
 
}
package com.automation.services;
import java.text.DateFormat;
import java.text.ParseException;
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
import com.automation.services.TrackerService;
import com.automation.vo.Agent;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test
{
	
	private final static Logger logger = Logger.getLogger(test.class);
	
 
 

	public void scheduler() {

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

			 

				System.out.println("Reading Temporary Table");
				AgentDAO dao = (AgentDAO) context.getBean("agentDAO");
			logger.info("Reading Temporary Table");

			///////////////////////// READING CHROME TEMPORARY
			///////////////////////// TABLE///////////////////
			List<Agent> chromtempEmailIds =dao.readChromeTempAgentIds();

			for (Agent e : chromtempEmailIds) {

				String emailId = e.getEmailId().trim();

				List<Agent> chromtempAgentTransaction =dao.readChromeTempAgentTransactions(emailId);
				String LoginTime = "";

				float loginDifference = 0;
				String LastTransactionToTime = "";
				String updateFlag = "";
				for (Agent t : chromtempAgentTransaction) {
					logger.info("Current Record"+ t.getFromDate()+"=="+LoginTime.trim());
					if (LoginTime.trim().equalsIgnoreCase("")) {
						logger.info("Login Time Update");
						LoginTime = t.getFromDate();
						String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						loginDifference = dateTimeDifference(LoginTime, timeStamp);
						LastTransactionToTime=t.getToDate();
						logger.info("LoginTime"+LoginTime);
						logger.info("loginDifference"+loginDifference);
						logger.info("LastTransactionToTime"+LastTransactionToTime);
					}
					
			//		logger.info("LoginTime==="+LoginTime);
				//	logger.info("loginDifference==="+loginDifference);
					logger.info("LastTransactionToTime==="+LastTransactionToTime);
					if (!LoginTime.trim().equalsIgnoreCase("") && loginDifference >= 840) {
						logger.info("inside login loop");
						updateFlag = "InProgress";
						float transactionDifference=0;
						logger.info("current from==="+ t.getFromDate());
						  transactionDifference = dateTimeDifference(LastTransactionToTime, t.getFromDate().trim());
							logger.info("transactionDifference=="+transactionDifference);
						if (transactionDifference > 240) {
							////////
							logger.info("Processing Records In Middle");
							logger.info("Email ID :" + e.getEmailId().replaceAll("\\s+", ""));
							List<Agent> agentdetails =dao.readAgentDetailsFromAgentMaster(emailId);
							String agentName = "";
							String ShiftTimings = "";
							String projectId = "";
							String location = "";
							String HCM_Supervisor = "";
						
							for (Agent e1 : agentdetails) {
								agentName = e1.getName();
								ShiftTimings = e1.getShiftTimings();
								projectId = e1.getProjectId();
								location = e1.getLocation();
								HCM_Supervisor = e1.getHcmSupervisor();

							}
							logger.info("agentName " + agentName);
							logger.info("ShiftTimings " + ShiftTimings);
							logger.info("projectId " + projectId);
							logger.info("location " + location);
							logger.info("HCM_Supervisor " + HCM_Supervisor);
					 
							
							
							if (agentName.trim().equalsIgnoreCase("")) {
								Agent dataInsertInException = new Agent();
								dataInsertInException.setEmailId(emailId);
								dataInsertInException.setFromDate(LoginTime);
								dataInsertInException.setToDate(LastTransactionToTime);
								logger.info("From Time " + LoginTime);
								logger.info("To Time " + LastTransactionToTime);
								int insertStatus =dao.dataInsertionInException(dataInsertInException);
								if (insertStatus >= 0) {
									logger.info("Data is Successfully inserted in Chrome Exception Table");
									logger.info("No Of Rows Inserted :"+insertStatus);

									int deleteStatus =dao.deleteFromChromeTempDetail(dataInsertInException);
									if (deleteStatus >= 0) {
										logger.info("Data is Successfully deleted in Chrome Detail Table");
										logger.info("No Of Rows Deleted :"+deleteStatus);

									}
								}

							} else {
								Agent activityHrsCalculation = new Agent();
								activityHrsCalculation.setEmailId(emailId);
								activityHrsCalculation.setFromDate(LoginTime);
								activityHrsCalculation.setToDate(LastTransactionToTime);
								List<Agent> activitydetails =dao.calculateTempActiviyHrs(activityHrsCalculation);
				
								String ACT_O1 = "0";
								String ACT_O2 = "0";
								String ACT_O3 = "0";
								String ACT_O4 = "0";
								String ACT_O5 = "0";
								String ACT_O6 = "0";
								String ACT_O7 = "0";
								String ACT_O8 = "0";
								String ACT_O9 = "0";
								String ACT_10 = "0";

								for (Agent activity : activitydetails) {
									logger.info("activity Code=="+activity.getActivityCode().trim());
									logger.info("activity Hrs=="+activity.getActivityHrs());
									if (activity.getActivityCode().trim().equalsIgnoreCase("01")) {
										ACT_O1 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("02")) {
										ACT_O2 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("03")) {
										ACT_O3 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("04")) {
										ACT_O4 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("05")) {
										ACT_O5 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("06")) {
										ACT_O6 = activity.getActivityHrs();
									}

									if (activity.getActivityCode().trim().equalsIgnoreCase("07")) {
										ACT_O7 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("08")) {
										ACT_O8 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("09")) {
										ACT_O9 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
										ACT_10 = activity.getActivityHrs();
									}

								}

								String activityHrs = ACT_O1 + "," + ACT_O2 + "," + ACT_O3 + "," + ACT_O4 + "," + ACT_O5
										+ "," + ACT_O6 + "," + ACT_O7 + "," + ACT_O8 + "," + ACT_O9 + "," + ACT_10;
								Agent dataInsertionInDayMaster = new Agent();

								String LoginDate[] = LoginTime.split(" ");
                                String timeSplt[]= LoginDate[1].split(":");
                                int hour=Integer.parseInt(timeSplt[0]);
                                String LoginDateUpdated=LoginDate[0];
                                if(hour >=0 && hour <=4)
                                {
                            		
                                 LoginDateUpdated=minusDate(LoginDate[0]);
                                 
                                	
                                	
                                }
                                
								dataInsertionInDayMaster.setDATE(LoginDateUpdated);
								dataInsertionInDayMaster.setEmailId(emailId);
								dataInsertionInDayMaster.setLoginTime(LoginTime);
								dataInsertionInDayMaster.setLogoutTime(LastTransactionToTime);
								dataInsertionInDayMaster.setActivityHrs(activityHrs);
								dataInsertionInDayMaster.setShiftTimings(ShiftTimings);
								dataInsertionInDayMaster.setProjectId(projectId);
								dataInsertionInDayMaster.setHcmSupervisor(HCM_Supervisor);
								dataInsertionInDayMaster.setLocation(location);
								dataInsertionInDayMaster.setName(agentName);
								int inserStatus =dao.dataInsertionInDayMaster(dataInsertionInDayMaster);

								if (inserStatus >= 0) {
									logger.info("Data is Successfully inserted in Day MasterTable");
									logger.info("No Of Rows Inserted :"+inserStatus);
									Agent dataInsertInDayDetail = new Agent();
									dataInsertInDayDetail.setEmailId(emailId);
									dataInsertInDayDetail.setFromDate(LoginTime);
									dataInsertInDayDetail.setToDate(LastTransactionToTime);
									logger.info("From Time " + LoginTime);
									logger.info("To Time " + LastTransactionToTime);
									int insertInDayDetailStatus = dao
											.dataInsertionInDayDetailFromTempDetails(dataInsertInDayDetail);

									if (insertInDayDetailStatus >= 0) {
										Agent datadeleteInChromeTemp = new Agent();
										datadeleteInChromeTemp.setEmailId(emailId);
										datadeleteInChromeTemp.setFromDate(LoginTime);
										datadeleteInChromeTemp.setToDate(LastTransactionToTime);
										int deleteStatus =dao.deleteFromChromeTempDetail(datadeleteInChromeTemp);
										if (deleteStatus >= 0) {
											logger.info("Data is Successfully deleted in Chrome Detail Table");
											logger.info("No Of Rows Deleted :"+deleteStatus);
										}

									}

								}

							}

							LoginTime = "";

							loginDifference = 0;
							LastTransactionToTime = "";
							updateFlag = "";
							
							
							if (LoginTime.trim().equalsIgnoreCase("")) {
								logger.info("Login Time Update");
								LoginTime = t.getFromDate();
								String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
								loginDifference = dateTimeDifference(LoginTime, timeStamp);
								LastTransactionToTime=t.getToDate();
								logger.info("LoginTime"+LoginTime);
								logger.info("loginDifference"+loginDifference);
								logger.info("LastTransactionToTime"+LastTransactionToTime);
							}
							
							
							
							
							
							
							

						}
					}

					LastTransactionToTime=t.getToDate();
				}
				if (updateFlag.trim().equalsIgnoreCase("InProgress")) {

					logger.info("Processing Pending Records");
					logger.info("Email ID :" + e.getEmailId().replaceAll("\\s+", ""));
					List<Agent> agentdetails =dao.readAgentDetailsFromAgentMaster(emailId);
					String agentName = "";
					String ShiftTimings = "";
					String projectId = "";
					String location = "";
					String HCM_Supervisor = "";
				
					for (Agent e1 : agentdetails) {
						agentName = e1.getName();
						ShiftTimings = e1.getShiftTimings();
						projectId = e1.getProjectId();
						location = e1.getLocation();
						HCM_Supervisor = e1.getHcmSupervisor();

					}
					logger.info("agentName " + agentName);
					logger.info("ShiftTimings " + ShiftTimings);
					logger.info("projectId " + projectId);
					logger.info("location " + location);
					logger.info("HCM_Supervisor " + HCM_Supervisor);
			 
					
					if (agentName.trim().equalsIgnoreCase("")) {
						Agent dataInsertInException = new Agent();
						dataInsertInException.setEmailId(emailId);
						dataInsertInException.setFromDate(LoginTime);
						dataInsertInException.setToDate(LastTransactionToTime);
						logger.info("From Time " + LoginTime);
						logger.info("To Time " + LastTransactionToTime);
						int insertStatus =dao.dataInsertionInException(dataInsertInException);
						if (insertStatus >= 0) {
							logger.info("Data is Successfully inserted in Chrome Exception Table");
							logger.info("No Of Rows Inserted :"+insertStatus);

							int deleteStatus =dao.deleteFromChromeTempDetail(dataInsertInException);
							if (deleteStatus >= 0) {
								logger.info("Data is Successfully deleted in Chrome Detail Table");
								logger.info("No Of Rows Deleted :"+deleteStatus);

							}
						}

					} else {
						Agent activityHrsCalculation = new Agent();
						activityHrsCalculation.setEmailId(emailId);
						activityHrsCalculation.setFromDate(LoginTime);
						activityHrsCalculation.setToDate(LastTransactionToTime);
						List<Agent> activitydetails =dao.calculateTempActiviyHrs(activityHrsCalculation);
		
						String ACT_O1 = "0";
						String ACT_O2 = "0";
						String ACT_O3 = "0";
						String ACT_O4 = "0";
						String ACT_O5 = "0";
						String ACT_O6 = "0";
						String ACT_O7 = "0";
						String ACT_O8 = "0";
						String ACT_O9 = "0";
						String ACT_10 = "0";

						for (Agent activity : activitydetails) {
							logger.info("activity Code=="+activity.getActivityCode().trim());
							logger.info("activity Hrs=="+activity.getActivityHrs());
							if (activity.getActivityCode().trim().equalsIgnoreCase("01")) {
								ACT_O1 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("02")) {
								ACT_O2 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("03")) {
								ACT_O3 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("04")) {
								ACT_O4 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("05")) {
								ACT_O5 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("06")) {
								ACT_O6 = activity.getActivityHrs();
							}

							if (activity.getActivityCode().trim().equalsIgnoreCase("07")) {
								ACT_O7 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("08")) {
								ACT_O8 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("09")) {
								ACT_O9 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
								ACT_10 = activity.getActivityHrs();
							}

						}

						String activityHrs = ACT_O1 + "," + ACT_O2 + "," + ACT_O3 + "," + ACT_O4 + "," + ACT_O5
								+ "," + ACT_O6 + "," + ACT_O7 + "," + ACT_O8 + "," + ACT_O9 + "," + ACT_10;
						Agent dataInsertionInDayMaster = new Agent();

						String LoginDate[] = LoginTime.split(" ");
                        String timeSplt[]= LoginDate[1].split(":");
                        int hour=Integer.parseInt(timeSplt[0]);
                        String LoginDateUpdated=LoginDate[0];
                        if(hour >=0 && hour <=4)
                        {
                    		
                         LoginDateUpdated=minusDate(LoginDate[0]);
                         
                        	
                        	
                        }
                        
						dataInsertionInDayMaster.setDATE(LoginDateUpdated);
						dataInsertionInDayMaster.setEmailId(emailId);
						dataInsertionInDayMaster.setLoginTime(LoginTime);
						dataInsertionInDayMaster.setLogoutTime(LastTransactionToTime);
						dataInsertionInDayMaster.setActivityHrs(activityHrs);
						dataInsertionInDayMaster.setShiftTimings(ShiftTimings);
						dataInsertionInDayMaster.setProjectId(projectId);
						dataInsertionInDayMaster.setHcmSupervisor(HCM_Supervisor);
						dataInsertionInDayMaster.setLocation(location);
						dataInsertionInDayMaster.setName(agentName);
						int inserStatus =dao.dataInsertionInDayMaster(dataInsertionInDayMaster);

						if (inserStatus >= 0) {
							logger.info("Data is Successfully inserted in Day MasterTable");
							logger.info("No Of Rows Inserted :"+inserStatus);
							Agent dataInsertInDayDetail = new Agent();
							dataInsertInDayDetail.setEmailId(emailId);
							dataInsertInDayDetail.setFromDate(LoginTime);
							dataInsertInDayDetail.setToDate(LastTransactionToTime);
							logger.info("From Time " + LoginTime);
							logger.info("To Time " + LastTransactionToTime);
							int insertInDayDetailStatus = dao
									.dataInsertionInDayDetailFromTempDetails(dataInsertInDayDetail);

							if (insertInDayDetailStatus >= 0) {
								Agent datadeleteInChromeTemp = new Agent();
								datadeleteInChromeTemp.setEmailId(emailId);
								datadeleteInChromeTemp.setFromDate(LoginTime);
								datadeleteInChromeTemp.setToDate(LastTransactionToTime);
								int deleteStatus =dao.deleteFromChromeTempDetail(datadeleteInChromeTemp);
								if (deleteStatus >= 0) {
									logger.info("Data is Successfully deleted in Chrome Detail Table");
									logger.info("No Of Rows Deleted :"+deleteStatus);

								}

							}

						}

					}
				}

			}

			logger.info("Reading Exception Table");

			///////////////////////// READING CHROME TEMPORARY
			///////////////////////// TABLE///////////////////
			chromtempEmailIds =dao.readChromeExceptionAgentIds();

			for (Agent e : chromtempEmailIds) {

				String emailId = e.getEmailId().trim();

				List<Agent> chromtempAgentTransaction =dao.readChromeExceptionAgentTransactions(emailId);
				String LoginTime = "";

				float loginDifference = 0;
				String LastTransactionToTime = "";
				String updateFlag = "";
				for (Agent t : chromtempAgentTransaction) {

					if (LoginTime.trim().equalsIgnoreCase("")) {
						LoginTime = t.getFromDate();
						String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						loginDifference = dateTimeDifference(LoginTime, timeStamp);
						LastTransactionToTime=t.getToDate();
					}

					if (!LoginTime.trim().equalsIgnoreCase("") && loginDifference >= 840) {
						updateFlag = "InProgress";
						float transactionDifference = dateTimeDifference(LastTransactionToTime, t.getFromDate().trim());
						if (transactionDifference > 240) {
							////////
							logger.info("Processing Records In Middle");
							logger.info("Email ID :" + e.getEmailId().replaceAll("\\s+", ""));
							List<Agent> agentdetails =dao.readAgentDetailsFromAgentMaster(emailId);
							String agentName = "";
							String ShiftTimings = "";
							String projectId = "";
							String location = "";
							String HCM_Supervisor = "";
						
							for (Agent e1 : agentdetails) {
								agentName = e1.getName();
								ShiftTimings = e1.getShiftTimings();
								projectId = e1.getProjectId();
								location = e1.getLocation();
								HCM_Supervisor = e1.getHcmSupervisor();

							}
							logger.info("agentName " + agentName);
							logger.info("ShiftTimings " + ShiftTimings);
							logger.info("projectId " + projectId);
							logger.info("location " + location);
							logger.info("HCM_Supervisor " + HCM_Supervisor);

							if (agentName.trim().equalsIgnoreCase("")) {
								logger.info("Email Id is missing in Agent Master");

							} else {
								Agent activityHrsCalculation = new Agent();
								activityHrsCalculation.setEmailId(emailId);
								activityHrsCalculation.setFromDate(LoginTime);
								activityHrsCalculation.setToDate(LastTransactionToTime);
								List<Agent> activitydetails = dao
										.calculateExceptionActiviyHrs(activityHrsCalculation);
								String where = "";
								String ACT_O1 = "0";
								String ACT_O2 = "0";
								String ACT_O3 = "0";
								String ACT_O4 = "0";
								String ACT_O5 = "0";
								String ACT_O6 = "0";
								String ACT_O7 = "0";
								String ACT_O8 = "0";
								String ACT_O9 = "0";
								String ACT_10 = "0";

								for (Agent activity : activitydetails) {
									logger.info("activity Code=="+activity.getActivityCode().trim());
									logger.info("activity Hrs=="+activity.getActivityHrs());
									if (activity.getActivityCode().trim().equalsIgnoreCase("01")) {
										ACT_O1 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("02")) {
										ACT_O2 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("03")) {
										ACT_O3 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("04")) {
										ACT_O4 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("05")) {
										ACT_O5 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("06")) {
										ACT_O6 = activity.getActivityHrs();
									}

									if (activity.getActivityCode().trim().equalsIgnoreCase("07")) {
										ACT_O7 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("08")) {
										ACT_O8 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("09")) {
										ACT_O9 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
										ACT_10 = activity.getActivityHrs();
									}

								}

								String activityHrs = ACT_O1 + "," + ACT_O2 + "," + ACT_O3 + "," + ACT_O4 + "," + ACT_O5
										+ "," + ACT_O6 + "," + ACT_O7 + "," + ACT_O8 + "," + ACT_O9 + "," + ACT_10;
								Agent dataInsertionInDayMaster = new Agent();

								String LoginDate[] = LoginTime.split(" ");
                                String timeSplt[]= LoginDate[1].split(":");
                                int hour=Integer.parseInt(timeSplt[0]);
                                String LoginDateUpdated=LoginDate[0];
                                if(hour >=0 && hour <=4)
                                {
                            		
                                 LoginDateUpdated=minusDate(LoginDate[0]);
                                 
                                	
                                	
                                }
                                
								dataInsertionInDayMaster.setDATE(LoginDateUpdated);
								dataInsertionInDayMaster.setEmailId(emailId);
								dataInsertionInDayMaster.setLoginTime(LoginTime);
								dataInsertionInDayMaster.setLogoutTime(LastTransactionToTime);
								dataInsertionInDayMaster.setActivityHrs(activityHrs);
								dataInsertionInDayMaster.setShiftTimings(ShiftTimings);
								dataInsertionInDayMaster.setProjectId(projectId);
								dataInsertionInDayMaster.setHcmSupervisor(HCM_Supervisor);
								dataInsertionInDayMaster.setLocation(location);
								dataInsertionInDayMaster.setName(agentName);
								
								int inserStatus =dao.dataInsertionInDayMaster(dataInsertionInDayMaster);

								if (inserStatus >= 0) {
									logger.info("Data is Successfully inserted in Day MasterTable");
									logger.info("No Of Rows Inserted :"+inserStatus);
									Agent dataInsertInDayDetail = new Agent();
									dataInsertInDayDetail.setEmailId(emailId);
									dataInsertInDayDetail.setFromDate(LoginTime);
									dataInsertInDayDetail.setToDate(LastTransactionToTime);
									logger.info("From Time " + LoginTime);
									logger.info("To Time " + LastTransactionToTime);
									int insertInDayDetailStatus = dao
											.dataInsertionInDayDetailFromExceptionDetails(dataInsertInDayDetail);

									if (insertInDayDetailStatus >= 0) {
										Agent datadeleteInChromeTemp = new Agent();
										datadeleteInChromeTemp.setEmailId(emailId);
										datadeleteInChromeTemp.setFromDate(LoginTime);
										datadeleteInChromeTemp.setToDate(LastTransactionToTime);
										int deleteStatus =dao.deleteFromChromeException(datadeleteInChromeTemp);
										if (deleteStatus >= 0) {
											logger.info("Data is Successfully deleted in Chrome Exception Table");
											logger.info("No Of Rows Deleted :"+deleteStatus);

										}

									}

								}

							}

							LoginTime = "";

							loginDifference = 0;
							LastTransactionToTime = "";
							updateFlag = "";
							
							if (LoginTime.trim().equalsIgnoreCase("")) {
								logger.info("Login Time Update");
								LoginTime = t.getFromDate();
								String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
								loginDifference = dateTimeDifference(LoginTime, timeStamp);
								LastTransactionToTime=t.getToDate();
								logger.info("LoginTime"+LoginTime);
								logger.info("loginDifference"+loginDifference);
								logger.info("LastTransactionToTime"+LastTransactionToTime);
							}
							

						}
					}
					LastTransactionToTime=t.getToDate();
				}
				if (updateFlag.trim().equalsIgnoreCase("InProgress")) {

					logger.info("Processing Pending Records");
					logger.info("Email ID :" + e.getEmailId().replaceAll("\\s+", ""));
					List<Agent> agentdetails =dao.readAgentDetailsFromAgentMaster(emailId);
					String agentName = "";
					String ShiftTimings = "";
					String projectId = "";
					String location = "";
					String HCM_Supervisor = "";
				
					for (Agent e1 : agentdetails) {
						agentName = e1.getName();
						ShiftTimings = e1.getShiftTimings();
						projectId = e1.getProjectId();
						location = e1.getLocation();
						HCM_Supervisor = e1.getHcmSupervisor();

					}
					logger.info("agentName " + agentName);
					logger.info("ShiftTimings " + ShiftTimings);
					logger.info("projectId " + projectId);
					logger.info("location " + location);
					logger.info("HCM_Supervisor " + HCM_Supervisor);

					if (agentName.trim().equalsIgnoreCase("")) {
						logger.info("Email Id is missing in Agent Master");

					} else {
						Agent activityHrsCalculation = new Agent();
						activityHrsCalculation.setEmailId(emailId);
						activityHrsCalculation.setFromDate(LoginTime);
						activityHrsCalculation.setToDate(LastTransactionToTime);
						List<Agent> activitydetails = dao
								.calculateExceptionActiviyHrs(activityHrsCalculation);
						String where = "";
						String ACT_O1 = "0";
						String ACT_O2 = "0";
						String ACT_O3 = "0";
						String ACT_O4 = "0";
						String ACT_O5 = "0";
						String ACT_O6 = "0";
						String ACT_O7 = "0";
						String ACT_O8 = "0";
						String ACT_O9 = "0";
						String ACT_10 = "0";

						for (Agent activity : activitydetails) {
							logger.info("activity Code=="+activity.getActivityCode().trim());
							logger.info("activity Hrs=="+activity.getActivityHrs());
							if (activity.getActivityCode().trim().equalsIgnoreCase("01")) {
								ACT_O1 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("02")) {
								ACT_O2 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("03")) {
								ACT_O3 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("04")) {
								ACT_O4 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("05")) {
								ACT_O5 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("06")) {
								ACT_O6 = activity.getActivityHrs();
							}

							if (activity.getActivityCode().trim().equalsIgnoreCase("07")) {
								ACT_O7 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("08")) {
								ACT_O8 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("09")) {
								ACT_O9 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
								ACT_10 = activity.getActivityHrs();
							}

						}

						String activityHrs = ACT_O1 + "," + ACT_O2 + "," + ACT_O3 + "," + ACT_O4 + "," + ACT_O5
								+ "," + ACT_O6 + "," + ACT_O7 + "," + ACT_O8 + "," + ACT_O9 + "," + ACT_10;
						Agent dataInsertionInDayMaster = new Agent();

						String LoginDate[] = LoginTime.split(" ");
                        String timeSplt[]= LoginDate[1].split(":");
                        int hour=Integer.parseInt(timeSplt[0]);
                        String LoginDateUpdated=LoginDate[0];
                        if(hour >=0 && hour <=4)
                        {
                    		
                         LoginDateUpdated=minusDate(LoginDate[0]);
                         
                        	
                        	
                        }
                        
						dataInsertionInDayMaster.setDATE(LoginDateUpdated);
						dataInsertionInDayMaster.setEmailId(emailId);
						dataInsertionInDayMaster.setLoginTime(LoginTime);
						dataInsertionInDayMaster.setLogoutTime(LastTransactionToTime);
						dataInsertionInDayMaster.setActivityHrs(activityHrs);
						dataInsertionInDayMaster.setShiftTimings(ShiftTimings);
						dataInsertionInDayMaster.setProjectId(projectId);
						dataInsertionInDayMaster.setHcmSupervisor(HCM_Supervisor);
						dataInsertionInDayMaster.setLocation(location);
						dataInsertionInDayMaster.setName(agentName);

						int inserStatus =dao.dataInsertionInDayMaster(dataInsertionInDayMaster);

						if (inserStatus >= 0) {
							logger.info("Data is Successfully inserted in Day MasterTable");
							logger.info("No Of Rows Insered :"+inserStatus);
							Agent dataInsertInDayDetail = new Agent();
							dataInsertInDayDetail.setEmailId(emailId);
							dataInsertInDayDetail.setFromDate(LoginTime);
							dataInsertInDayDetail.setToDate(LastTransactionToTime);
							logger.info("From Time " + LoginTime);
							logger.info("To Time " + LastTransactionToTime);
							int insertInDayDetailStatus = dao
									.dataInsertionInDayDetailFromExceptionDetails(dataInsertInDayDetail);

							if (insertInDayDetailStatus >= 0) {
								Agent datadeleteInChromeTemp = new Agent();
								datadeleteInChromeTemp.setEmailId(emailId);
								datadeleteInChromeTemp.setFromDate(LoginTime);
								datadeleteInChromeTemp.setToDate(LastTransactionToTime);
								int deleteStatus =dao.deleteFromChromeException(datadeleteInChromeTemp);
								if (deleteStatus >= 0) {
									logger.info("Data is Successfully deleted in Chrome Exception Table");
									logger.info("No Of Rows Deleted :"+deleteStatus);

								}

							}

						}

					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception Occured in Scheduler" + e);
			e.printStackTrace();
		}
	}

	public float dateTimeDifference(String dateStart, String dateStop) {
 
String startDtsplit[]=dateStart.split(" ");
String formattedStartDate[]=startDtsplit[0].split("-");

String endDtsplit[]=dateStop.split(" ");
String formattedEndDate[]=endDtsplit[0].split("-");
		// HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			//logger.info("Formatted Start Date=="+formattedStartDate[1]+"/"+formattedStartDate[2]+"/"+formattedStartDate[0]+" "+startDtsplit[1]);
		//	logger.info("Formatted End Date=="+formattedEndDate[1]+"/"+formattedEndDate[2]+"/"+formattedEndDate[0]+" "+endDtsplit[1]);		
		//	d1 = format.parse(formattedStartDate[1]+"/"+formattedStartDate[2]+"/"+formattedStartDate[0]+" "+startDtsplit[1]);
		//	d2 = format.parse(formattedEndDate[1]+"/"+formattedEndDate[2]+"/"+formattedEndDate[0]+" "+endDtsplit[1]);
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
			// in milliseconds
			 
			  long milliseconds1 = d1.getTime();
			  long milliseconds2 =d2.getTime();

			  long diff = milliseconds2 - milliseconds1;
			  long diffSeconds = diff / 1000;
			  long diffMinutes = diff / (60 * 1000);
			  long diffHours = diff / (60 * 60 * 1000);
			  long diffDays = diff / (24 * 60 * 60 * 1000);

			    return diffMinutes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	public String minusDate(String Date)
	{
		String datesplt[]=Date.split("-");
   	 Date currentDate = new Date(datesplt[0]+"/"+datesplt[1]+"/"+datesplt[2]);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           System.out.println(dateFormat.format(currentDate));

           // convert date to calendar
           Calendar c = Calendar.getInstance();
           c.setTime(currentDate);

           // manipulate date
        
           c.add(Calendar.DATE, -1); //same with c.add(Calendar.DAY_OF_MONTH, 1);
     

           // convert calendar to date
           Date currentDateMinusOne = c.getTime();

           System.out.println(dateFormat.format(currentDateMinusOne));
           return dateFormat.format(currentDateMinusOne);
	}
	 
    public static void main( String[] args ) throws Exception
    {
    	test obj=new test();
   
  	obj.scheduler();
    }
}

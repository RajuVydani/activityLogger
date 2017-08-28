package com.automation.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.automation.dao.AgentDAO;
import com.automation.idao.IAgentDAO;
import com.automation.vo.Agent;

public class SchedulerTask {
	@Autowired
	private IAgentDAO agentDAO;
	private final static Logger logger = Logger.getLogger(AgentDAO.class);

	@Value("${delete.TemporaryFlag}")
	private String deleteTemporaryFlag;
	
	public void scheduler() {

		try {
			logger.info("*******************************SCHEDULER STARTED*************************************");

			logger.info("Reading Temporary Table");
			///////////////////////// READING CHROME TEMPORARY
			///////////////////////// TABLE///////////////////

			List<Agent> chromtempAgentTransaction = agentDAO.readTemporaryTable();

			for (Agent t : chromtempAgentTransaction) {
				
				String emailId = t.getEmailId().replaceAll("\\s+", "");
				String activityFromTime = t.getFromDate();
				String activityToTime = t.getToDate();
				String websiteVisited = t.getWebsitesVisited();
				String activityCode = t.getActivityCode();

				String activityFromDatesplit[] = activityFromTime.split(" ");
				String activityFromtimeSplt[] = activityFromDatesplit[1].split(":");
				String activityFromDate = activityFromDatesplit[0];
				Agent getDetailsFromDayMasterInput = new Agent();
				getDetailsFromDayMasterInput.setEmailId(emailId);
				getDetailsFromDayMasterInput.setDATE(activityFromDate);

				int entryCountInDayMaster = agentDAO.dayMasterCount(getDetailsFromDayMasterInput);

				if (entryCountInDayMaster == 1) {

					// exist

					Agent DayDetailsInput = new Agent();
					DayDetailsInput.setDATE(activityFromDate);
					DayDetailsInput.setEmailId(emailId);
					DayDetailsInput.setToDate(activityFromTime);
					DayDetailsInput.setActivityCode(activityCode);

					int dayDetailLastActivity = agentDAO.dayDetailLastActivity(DayDetailsInput);
					if (dayDetailLastActivity == 0) {
						int dayDetailCount = agentDAO.dayDetailCount(DayDetailsInput);

						Agent dataInsertInDayDetail = new Agent();
						dataInsertInDayDetail.setEmailId(emailId);
						dataInsertInDayDetail.setFromDate(t.getFromDate());
						dataInsertInDayDetail.setToDate(t.getToDate());
						dataInsertInDayDetail.setWebsitesVisited(t.getWebsitesVisited());
						dataInsertInDayDetail.setActivityCode(t.getActivityCode());
						dataInsertInDayDetail.setRownum((dayDetailCount + 1));
						dataInsertInDayDetail.setDATE(activityFromDate);

						int insertInDayDetailStatus = agentDAO.dayDetailInsert(dataInsertInDayDetail);
						if (insertInDayDetailStatus >= 0) {
							logger.info("Data is Successfully Inserted in Day Detail Table");
							logger.info("No Of Rows Inserted :" + insertInDayDetailStatus);
							
							if(deleteTemporaryFlag.trim().equalsIgnoreCase("Y"))
							{
							
								
								int deleteStatus = agentDAO.temporaryTableDelete(t);
								if (deleteStatus >= 0) {
									logger.info("Data is Successfully deleted in Chrome Detail Table");
									logger.info("No Of Rows Deleted :" + deleteStatus);

								}	
							}
							else
							{
								int insertBkpStatus = agentDAO.temporaryBkpTableInsert(t);
								if (insertBkpStatus >= 0) {
									logger.info("Data is Successfully Inserted in Temporary Back Up Table");
									logger.info("No Of Rows Inserted :" + insertBkpStatus);

								
								int deleteStatus = agentDAO.temporaryTableDelete(t);
								if (deleteStatus >= 0) {
									logger.info("Data is Successfully deleted in Chrome Detail Table");
									logger.info("No Of Rows Deleted :" + deleteStatus);

								}	
								}
							}
						
						}

					} else {

						Agent dataUpdateInDayDetail = new Agent();
						dataUpdateInDayDetail.setEmailId(emailId);
						dataUpdateInDayDetail.setFromDate(activityFromTime);
						dataUpdateInDayDetail.setToDate(activityToTime);
						dataUpdateInDayDetail.setActivityCode(activityCode);
						dataUpdateInDayDetail.setDATE(activityFromDate);

						int dataUpdateInDayDetailStatus = agentDAO.dayDetailUpdate(dataUpdateInDayDetail);
						if (dataUpdateInDayDetailStatus >= 0) {
							logger.info("Data is Successfully Updated in Day Detail Table");
							logger.info("No Of Rows Updated :" + dataUpdateInDayDetailStatus);

							if(deleteTemporaryFlag.trim().equalsIgnoreCase("Y"))
							{
							
								
								int deleteStatus = agentDAO.temporaryTableDelete(t);
								if (deleteStatus >= 0) {
									logger.info("Data is Successfully deleted in Chrome Detail Table");
									logger.info("No Of Rows Deleted :" + deleteStatus);

								}	
							}
							else
							{
								int insertBkpStatus = agentDAO.temporaryBkpTableInsert(t);
								if (insertBkpStatus >= 0) {
									logger.info("Data is Successfully Inserted in Temporary Back Up Table");
									logger.info("No Of Rows Inserted :" + insertBkpStatus);

								
								int deleteStatus = agentDAO.temporaryTableDelete(t);
								if (deleteStatus >= 0) {
									logger.info("Data is Successfully deleted in Chrome Detail Table");
									logger.info("No Of Rows Deleted :" + deleteStatus);

								}	
								}
							}
						}

					}

					List<Agent> loginDetailsFromDayMaster = agentDAO.getLoginTime(getDetailsFromDayMasterInput);
					String loginTimeFromDayMaster = "";
					for (Agent loginTimeFromDayMasterList : loginDetailsFromDayMaster) {
						loginTimeFromDayMaster = loginTimeFromDayMasterList.getLoginTime();

					}
					Agent activityHrsCalculation = new Agent();
					activityHrsCalculation.setEmailId(emailId);
					activityHrsCalculation.setFromDate(loginTimeFromDayMaster);
					activityHrsCalculation.setToDate(t.getToDate());

					List<Agent> activitydetails = agentDAO.activityHrsCalculation(activityHrsCalculation);

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
						logger.info("activity Code==" + activity.getActivityCode().trim());
						logger.info("activity Hrs==" + activity.getActivityHrs());
						if (activity.getActivityCode().trim().equalsIgnoreCase("1")) {
							ACT_O1 = activity.getActivityHrs();
						}
						if (activity.getActivityCode().trim().equalsIgnoreCase("2")) {
							ACT_O2 = activity.getActivityHrs();
						}
						if (activity.getActivityCode().trim().equalsIgnoreCase("3")) {
							ACT_O3 = activity.getActivityHrs();
						}
						if (activity.getActivityCode().trim().equalsIgnoreCase("4")) {
							ACT_O4 = activity.getActivityHrs();
						}
						if (activity.getActivityCode().trim().equalsIgnoreCase("5")) {
							ACT_O5 = activity.getActivityHrs();
						}
						if (activity.getActivityCode().trim().equalsIgnoreCase("6")) {
							ACT_O6 = activity.getActivityHrs();
						}

						if (activity.getActivityCode().trim().equalsIgnoreCase("7")) {
							ACT_O7 = activity.getActivityHrs();
						}
						if (activity.getActivityCode().trim().equalsIgnoreCase("8")) {
							ACT_O8 = activity.getActivityHrs();
						}
						if (activity.getActivityCode().trim().equalsIgnoreCase("9")) {
							ACT_O9 = activity.getActivityHrs();
						}
						if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
							ACT_10 = activity.getActivityHrs();
						}

					}
 
					Agent dataUpdateInDayMaster = new Agent();

					dataUpdateInDayMaster.setEmailId(emailId);
					dataUpdateInDayMaster.setDATE(activityFromDate);
					dataUpdateInDayMaster.setProdSum(ACT_O1);
					dataUpdateInDayMaster.setIdleSum(ACT_O2);
					dataUpdateInDayMaster.setBreakSum(ACT_O3);
					dataUpdateInDayMaster.setMealsSum(ACT_O4);
					dataUpdateInDayMaster.setHuddleSum(ACT_O5);
					dataUpdateInDayMaster.setWelnessSupportSum(ACT_O6);
					dataUpdateInDayMaster.setCoachingSum(ACT_O7);
					dataUpdateInDayMaster.setTeamMeetingSum(ACT_O8);
					dataUpdateInDayMaster.setFbTrainingSum(ACT_O9);
					dataUpdateInDayMaster.setNonFbTrainingSum(ACT_10);
					dataUpdateInDayMaster.setLogoutTime(t.getToDate());
					int updateInDayMasterStatus = agentDAO.dayMasterUpdate(dataUpdateInDayMaster);
					if (updateInDayMasterStatus >= 0) {
						logger.info("Data is Successfully Updated in Day Master Table");
						logger.info("No Of Rows Updated :" + updateInDayMasterStatus);

					}

				} else {

					String updateStatusFlag = "";

					int hour = Integer.parseInt(activityFromtimeSplt[0]);

					if (hour >= 0 && hour <= 10) {
						Agent previousDayDetailsInput = new Agent();
						previousDayDetailsInput.setDATE(minusDate(activityFromDate));
						previousDayDetailsInput.setEmailId(emailId);

						List<Agent> previousDayDetails = agentDAO.dayMasterPreviousDayLogout(previousDayDetailsInput);

						String agentIdFromDayMatser = "";
						String logOutTimeFromDayMatser = "";

						for (Agent previousDayDetailsList : previousDayDetails) {

							agentIdFromDayMatser = previousDayDetailsList.getAgentId();
							logOutTimeFromDayMatser = previousDayDetailsList.getLogoutTime();
						}
						float timeDifference = 0;
						if (!agentIdFromDayMatser.trim().equalsIgnoreCase("")) {
							timeDifference = dateTimeDifference(logOutTimeFromDayMatser, activityFromTime);
						}

						if (!agentIdFromDayMatser.trim().equalsIgnoreCase("") && timeDifference >= 0
								&& timeDifference <= 540) {
							// previous

							String previousDate = minusDate(activityFromDate);

							Agent DayDetailsInput = new Agent();
							DayDetailsInput.setDATE(previousDate);
							DayDetailsInput.setEmailId(emailId);
							DayDetailsInput.setToDate(activityFromTime);
							DayDetailsInput.setActivityCode(activityCode);

							int dayDetailLastActivity = agentDAO.dayDetailLastActivity(DayDetailsInput);
							if (dayDetailLastActivity == 0) {
								int dayDetailCount = agentDAO.dayDetailCount(DayDetailsInput);

								Agent dataInsertInDayDetail = new Agent();
								dataInsertInDayDetail.setEmailId(emailId);
								dataInsertInDayDetail.setFromDate(t.getFromDate());
								dataInsertInDayDetail.setToDate(t.getToDate());
								dataInsertInDayDetail.setWebsitesVisited(t.getWebsitesVisited());
								dataInsertInDayDetail.setActivityCode(t.getActivityCode());
								dataInsertInDayDetail.setRownum((dayDetailCount + 1));
								dataInsertInDayDetail.setDATE(previousDate);

								int insertInDayDetailStatus = agentDAO.dayDetailInsert(dataInsertInDayDetail);
								if (insertInDayDetailStatus >= 0) {
									logger.info("Data is Successfully Inserted in Day Detail Table");
									logger.info("No Of Rows Inserted :" + insertInDayDetailStatus);

									if(deleteTemporaryFlag.trim().equalsIgnoreCase("Y"))
									{
									
										
										int deleteStatus = agentDAO.temporaryTableDelete(t);
										if (deleteStatus >= 0) {
											logger.info("Data is Successfully deleted in Chrome Detail Table");
											logger.info("No Of Rows Deleted :" + deleteStatus);

										}	
									}
									else
									{
										int insertBkpStatus = agentDAO.temporaryBkpTableInsert(t);
										if (insertBkpStatus >= 0) {
											logger.info("Data is Successfully Inserted in Temporary Back Up Table");
											logger.info("No Of Rows Inserted :" + insertBkpStatus);

										
										int deleteStatus = agentDAO.temporaryTableDelete(t);
										if (deleteStatus >= 0) {
											logger.info("Data is Successfully deleted in Chrome Detail Table");
											logger.info("No Of Rows Deleted :" + deleteStatus);

										}	
										}
									}
								}

							} else {

								Agent dataUpdateInDayDetail = new Agent();
								dataUpdateInDayDetail.setEmailId(emailId);
								dataUpdateInDayDetail.setFromDate(activityFromTime);
								dataUpdateInDayDetail.setToDate(activityToTime);
								dataUpdateInDayDetail.setActivityCode(activityCode);
								dataUpdateInDayDetail.setDATE(previousDate);

								int dataUpdateInDayDetailStatus = agentDAO.dayDetailUpdate(dataUpdateInDayDetail);
								if (dataUpdateInDayDetailStatus >= 0) {
									logger.info("Data is Successfully Updated in Day Detail Table");
									logger.info("No Of Rows Updated :" + dataUpdateInDayDetailStatus);

									if(deleteTemporaryFlag.trim().equalsIgnoreCase("Y"))
									{
									
										
										int deleteStatus = agentDAO.temporaryTableDelete(t);
										if (deleteStatus >= 0) {
											logger.info("Data is Successfully deleted in Chrome Detail Table");
											logger.info("No Of Rows Deleted :" + deleteStatus);

										}	
									}
									else
									{
										int insertBkpStatus = agentDAO.temporaryBkpTableInsert(t);
										if (insertBkpStatus >= 0) {
											logger.info("Data is Successfully Inserted in Temporary Back Up Table");
											logger.info("No Of Rows Inserted :" + insertBkpStatus);

										
										int deleteStatus = agentDAO.temporaryTableDelete(t);
										if (deleteStatus >= 0) {
											logger.info("Data is Successfully deleted in Chrome Detail Table");
											logger.info("No Of Rows Deleted :" + deleteStatus);

										}	
										}
									}
								}

							}

							List<Agent> loginDetailsFromDayMaster = agentDAO.getLoginTime(getDetailsFromDayMasterInput);
							String loginTimeFromDayMaster = "";
							for (Agent loginTimeFromDayMasterList : loginDetailsFromDayMaster) {
								loginTimeFromDayMaster = loginTimeFromDayMasterList.getLoginTime();

							}
							Agent activityHrsCalculation = new Agent();
							activityHrsCalculation.setEmailId(emailId);
							activityHrsCalculation.setFromDate(loginTimeFromDayMaster);
							activityHrsCalculation.setToDate(t.getToDate());

							List<Agent> activitydetails = agentDAO.activityHrsCalculation(activityHrsCalculation);

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
								logger.info("activity Code==" + activity.getActivityCode().trim());
								logger.info("activity Hrs==" + activity.getActivityHrs());
								if (activity.getActivityCode().trim().equalsIgnoreCase("1")) {
									ACT_O1 = activity.getActivityHrs();
								}
								if (activity.getActivityCode().trim().equalsIgnoreCase("2")) {
									ACT_O2 = activity.getActivityHrs();
								}
								if (activity.getActivityCode().trim().equalsIgnoreCase("3")) {
									ACT_O3 = activity.getActivityHrs();
								}
								if (activity.getActivityCode().trim().equalsIgnoreCase("4")) {
									ACT_O4 = activity.getActivityHrs();
								}
								if (activity.getActivityCode().trim().equalsIgnoreCase("5")) {
									ACT_O5 = activity.getActivityHrs();
								}
								if (activity.getActivityCode().trim().equalsIgnoreCase("6")) {
									ACT_O6 = activity.getActivityHrs();
								}

								if (activity.getActivityCode().trim().equalsIgnoreCase("7")) {
									ACT_O7 = activity.getActivityHrs();
								}
								if (activity.getActivityCode().trim().equalsIgnoreCase("8")) {
									ACT_O8 = activity.getActivityHrs();
								}
								if (activity.getActivityCode().trim().equalsIgnoreCase("9")) {
									ACT_O9 = activity.getActivityHrs();
								}
								if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
									ACT_10 = activity.getActivityHrs();
								}

							}

						 
							Agent dataUpdateInDayMaster = new Agent();

							dataUpdateInDayMaster.setEmailId(emailId);
							dataUpdateInDayMaster.setDATE(previousDate);
							dataUpdateInDayMaster.setProdSum(ACT_O1);
							dataUpdateInDayMaster.setIdleSum(ACT_O2);
							dataUpdateInDayMaster.setBreakSum(ACT_O3);
							dataUpdateInDayMaster.setMealsSum(ACT_O4);
							dataUpdateInDayMaster.setHuddleSum(ACT_O5);
							dataUpdateInDayMaster.setWelnessSupportSum(ACT_O6);
							dataUpdateInDayMaster.setCoachingSum(ACT_O7);
							dataUpdateInDayMaster.setTeamMeetingSum(ACT_O8);
							dataUpdateInDayMaster.setFbTrainingSum(ACT_O9);
							dataUpdateInDayMaster.setNonFbTrainingSum(ACT_10);
							dataUpdateInDayMaster.setLogoutTime(t.getToDate());
							int updateInDayMasterStatus = agentDAO.dayMasterUpdate(dataUpdateInDayMaster);
							if (updateInDayMasterStatus >= 0) {
								logger.info("Data is Successfully Updated in Day Master Table");
								logger.info("No Of Rows Updated :" + updateInDayMasterStatus);

							}

							updateStatusFlag = "updated";

							///////////////////

						}

					}

					if (!updateStatusFlag.trim().equalsIgnoreCase("updated")) {

						List<Agent> agentdetails = agentDAO.readAgentDetails(emailId);
						String agentName = "";

						String projectId = "";
						String subProjectId = "";
						String projectName = "";
						String subProjectName = "";
						String location = "";
						String hcmSupervisorId = "";
						String hcmSupervisorName = "";
						String billable = "";
						String onshoreOffshore = "";
						String agentId = "";

						String shiftFrom = "";
						String shiftTo = "";

						for (Agent e1 : agentdetails) {
							agentName = e1.getName();

							projectId = e1.getProjectId();
							subProjectId = e1.getSubProjectId();
							location = e1.getLocation();
							hcmSupervisorId = e1.getHcmSupervisorId();
							hcmSupervisorName = e1.getHcmSupervisorName();
							billable = e1.getBillable();
							onshoreOffshore = e1.getOnshoreOffshore();
							agentId = e1.getAgentId();
							projectName = e1.getProjectName();
							subProjectName = e1.getSubProjectName();

						}
						logger.info("agentName " + agentName);
						logger.info("projectId " + projectId);
						logger.info("location " + location);
						logger.info("HCM_Supervisor Id " + hcmSupervisorId);
						logger.info("HCM_Supervisor Name" + hcmSupervisorName);
						logger.info("Billable " + billable);
						logger.info("onshoreOffshore" + onshoreOffshore);
						logger.info("agentId" + agentId);
						logger.info("projectName" + projectName);
						logger.info("subProjectName" + subProjectName);
						logger.info("subProjectId" + subProjectId);

						int shiftHour = Integer.parseInt(activityFromtimeSplt[0]);
						String shiftTime = "";
						if (shiftHour >= 0 && shiftHour <= 3) {

							shiftTime = activityFromDatesplit[1];
						} else {
							shiftTime = activityFromDatesplit[1];
						}

						Agent shiftTimingsInput = new Agent();
						shiftTimingsInput.setProjectId(projectId);
						shiftTimingsInput.setSubProjectId(subProjectId);
						shiftTimingsInput.setLocation(location);
						shiftTimingsInput.setStartTime(shiftTime);

						List<Agent> shiftDetails = agentDAO.fetchShiftDetails(shiftTimingsInput);
						for (Agent e1 : shiftDetails) {
							shiftFrom = e1.getShiftFrom();
							shiftTo = e1.getShiftTo();

						}

						logger.info("shiftFrom" + shiftFrom);
						logger.info("shiftTo" + shiftTo);

						String errorDesc = "";
						if (agentName.trim().equalsIgnoreCase("")) {
							errorDesc = "agent master";

						}
						if (shiftFrom.trim().equalsIgnoreCase("") || shiftTo.trim().equalsIgnoreCase("")) {
							logger.info("Not present in any shift");

						}
						t.setErrorDesc(errorDesc);

						if (agentName.trim().equalsIgnoreCase("")) {

							int insertStatus = agentDAO.exceptionTableInsert(t);
							if (insertStatus >= 0) {
								logger.info("Data is Successfully inserted in Chrome Exception Table");
								logger.info("No Of Rows Inserted :" + insertStatus);

								if(deleteTemporaryFlag.trim().equalsIgnoreCase("Y"))
								{
								
									
									int deleteStatus = agentDAO.temporaryTableDelete(t);
									if (deleteStatus >= 0) {
										logger.info("Data is Successfully deleted in Chrome Detail Table");
										logger.info("No Of Rows Deleted :" + deleteStatus);

									}	
								}
								else
								{
									int insertBkpStatus = agentDAO.temporaryBkpTableInsert(t);
									if (insertBkpStatus >= 0) {
										logger.info("Data is Successfully Inserted in Temporary Back Up Table");
										logger.info("No Of Rows Inserted :" + insertBkpStatus);

									
									int deleteStatus = agentDAO.temporaryTableDelete(t);
									if (deleteStatus >= 0) {
										logger.info("Data is Successfully deleted in Chrome Detail Table");
										logger.info("No Of Rows Deleted :" + deleteStatus);

									}	
									}
								}
							}

						} else {
							///////////

							Agent dayMasterInsert = new Agent();
							if (shiftHour >= 0 && shiftHour <= 3) {
								dayMasterInsert.setDATE(minusDate(activityFromDate));
							} else {
								dayMasterInsert.setDATE(activityFromDate);
							}

							dayMasterInsert.setEmailId(emailId);
							dayMasterInsert.setLoginTime(t.getFromDate());
							dayMasterInsert.setLogoutTime(t.getToDate());
							if (shiftFrom.trim().equalsIgnoreCase("") || shiftTo.trim().equalsIgnoreCase("")) {
								dayMasterInsert.setShiftTimings("N");
							} else {
								dayMasterInsert.setShiftTimings(shiftFrom + "-" + shiftTo);
							}
							dayMasterInsert.setProjectId(projectId);
							dayMasterInsert.setProjectName(projectName);
							dayMasterInsert.setSubProjectId(subProjectId);
							dayMasterInsert.setSubProjectName(subProjectName);
							dayMasterInsert.setHcmSupervisorId(hcmSupervisorId);
							dayMasterInsert.setHcmSupervisorName(hcmSupervisorName);
							dayMasterInsert.setBillable(billable);
							dayMasterInsert.setOnshoreOffshore(onshoreOffshore);
							dayMasterInsert.setLocation(location);
							dayMasterInsert.setName(agentName);
							dayMasterInsert.setAgentId(agentId);
							int inserStatus = agentDAO.dayMasterInsert(dayMasterInsert);

							if (inserStatus >= 0) {
								logger.info("Data is Successfully inserted in Day MasterTable");
								logger.info("No Of Rows Inserted :" + inserStatus);

								// exist

								Agent DayDetailsInput = new Agent();
								if (shiftHour >= 0 && shiftHour <= 3) {
									DayDetailsInput.setDATE(minusDate(activityFromDate));

								} else {
									DayDetailsInput.setDATE(activityFromDate);
								}
								DayDetailsInput.setEmailId(emailId);
								DayDetailsInput.setToDate(activityFromTime);
								DayDetailsInput.setActivityCode(activityCode);

								int dayDetailLastActivity = agentDAO.dayDetailLastActivity(DayDetailsInput);
								if (dayDetailLastActivity == 0) {
									int dayDetailCount = agentDAO.dayDetailCount(DayDetailsInput);

									Agent dataInsertInDayDetail = new Agent();
									dataInsertInDayDetail.setEmailId(emailId);
									dataInsertInDayDetail.setFromDate(t.getFromDate());
									dataInsertInDayDetail.setToDate(t.getToDate());
									dataInsertInDayDetail.setWebsitesVisited(t.getWebsitesVisited());
									dataInsertInDayDetail.setActivityCode(t.getActivityCode());
									dataInsertInDayDetail.setRownum((dayDetailCount + 1));
									if (shiftHour >= 0 && shiftHour <= 3) {
										dataInsertInDayDetail.setDATE(minusDate(activityFromDate));

									} else {
										dataInsertInDayDetail.setDATE(activityFromDate);
									}

									int insertInDayDetailStatus = agentDAO.dayDetailInsert(dataInsertInDayDetail);
									if (insertInDayDetailStatus >= 0) {
										logger.info("Data is Successfully Inserted in Day Detail Table");
										logger.info("No Of Rows Inserted :" + insertInDayDetailStatus);

										if(deleteTemporaryFlag.trim().equalsIgnoreCase("Y"))
										{
										
											
											int deleteStatus = agentDAO.temporaryTableDelete(t);
											if (deleteStatus >= 0) {
												logger.info("Data is Successfully deleted in Chrome Detail Table");
												logger.info("No Of Rows Deleted :" + deleteStatus);

											}	
										}
										else
										{
											int insertBkpStatus = agentDAO.temporaryBkpTableInsert(t);
											if (insertBkpStatus >= 0) {
												logger.info("Data is Successfully Inserted in Temporary Back Up Table");
												logger.info("No Of Rows Inserted :" + insertBkpStatus);

											
											int deleteStatus = agentDAO.temporaryTableDelete(t);
											if (deleteStatus >= 0) {
												logger.info("Data is Successfully deleted in Chrome Detail Table");
												logger.info("No Of Rows Deleted :" + deleteStatus);

											}	
											}
										}
									}

								} else {

									Agent dataUpdateInDayDetail = new Agent();
									dataUpdateInDayDetail.setEmailId(emailId);
									dataUpdateInDayDetail.setFromDate(activityFromTime);
									dataUpdateInDayDetail.setToDate(activityToTime);
									dataUpdateInDayDetail.setActivityCode(activityCode);
									if (shiftHour >= 0 && shiftHour <= 3) {
										dataUpdateInDayDetail.setDATE(minusDate(activityFromDate));

									} else {
										dataUpdateInDayDetail.setDATE(activityFromDate);
									}

									int dataUpdateInDayDetailStatus = agentDAO.dayDetailUpdate(dataUpdateInDayDetail);
									if (dataUpdateInDayDetailStatus >= 0) {
										logger.info("Data is Successfully Updated in Day Detail Table");
										logger.info("No Of Rows Updated :" + dataUpdateInDayDetailStatus);

										if(deleteTemporaryFlag.trim().equalsIgnoreCase("Y"))
										{
										
											
											int deleteStatus = agentDAO.temporaryTableDelete(t);
											if (deleteStatus >= 0) {
												logger.info("Data is Successfully deleted in Chrome Detail Table");
												logger.info("No Of Rows Deleted :" + deleteStatus);

											}	
										}
										else
										{
											int insertBkpStatus = agentDAO.temporaryBkpTableInsert(t);
											if (insertBkpStatus >= 0) {
												logger.info("Data is Successfully Inserted in Temporary Back Up Table");
												logger.info("No Of Rows Inserted :" + insertBkpStatus);

											
											int deleteStatus = agentDAO.temporaryTableDelete(t);
											if (deleteStatus >= 0) {
												logger.info("Data is Successfully deleted in Chrome Detail Table");
												logger.info("No Of Rows Deleted :" + deleteStatus);

											}	
											}
										}
									}

								}

								List<Agent> loginDetailsFromDayMaster = agentDAO
										.getLoginTime(getDetailsFromDayMasterInput);
								String loginTimeFromDayMaster = "";
								for (Agent loginTimeFromDayMasterList : loginDetailsFromDayMaster) {
									loginTimeFromDayMaster = loginTimeFromDayMasterList.getLoginTime();

								}
								Agent activityHrsCalculation = new Agent();
								activityHrsCalculation.setEmailId(emailId);
								activityHrsCalculation.setFromDate(loginTimeFromDayMaster);
								activityHrsCalculation.setToDate(t.getToDate());

								List<Agent> activitydetails = agentDAO.activityHrsCalculation(activityHrsCalculation);

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
									logger.info("activity Code==" + activity.getActivityCode().trim());
									logger.info("activity Hrs==" + activity.getActivityHrs());
									if (activity.getActivityCode().trim().equalsIgnoreCase("1")) {
										ACT_O1 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("2")) {
										ACT_O2 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("3")) {
										ACT_O3 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("4")) {
										ACT_O4 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("5")) {
										ACT_O5 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("6")) {
										ACT_O6 = activity.getActivityHrs();
									}

									if (activity.getActivityCode().trim().equalsIgnoreCase("7")) {
										ACT_O7 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("8")) {
										ACT_O8 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("9")) {
										ACT_O9 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
										ACT_10 = activity.getActivityHrs();
									}

								}

							 
								Agent dataUpdateInDayMaster = new Agent();

								dataUpdateInDayMaster.setEmailId(emailId);
								dataUpdateInDayMaster.setDATE(activityFromDate);
								dataUpdateInDayMaster.setProdSum(ACT_O1);
								dataUpdateInDayMaster.setIdleSum(ACT_O2);
								dataUpdateInDayMaster.setBreakSum(ACT_O3);
								dataUpdateInDayMaster.setMealsSum(ACT_O4);
								dataUpdateInDayMaster.setHuddleSum(ACT_O5);
								dataUpdateInDayMaster.setWelnessSupportSum(ACT_O6);
								dataUpdateInDayMaster.setCoachingSum(ACT_O7);
								dataUpdateInDayMaster.setTeamMeetingSum(ACT_O8);
								dataUpdateInDayMaster.setFbTrainingSum(ACT_O9);
								dataUpdateInDayMaster.setNonFbTrainingSum(ACT_10);
								dataUpdateInDayMaster.setLogoutTime(t.getToDate());
								int updateInDayMasterStatus = agentDAO.dayMasterUpdate(dataUpdateInDayMaster);
								if (updateInDayMasterStatus >= 0) {
									logger.info("Data is Successfully Updated in Day Master Table");
									logger.info("No Of Rows Updated :" + updateInDayMasterStatus);

								}

							}

						}

					}

				}
			}

			logger.info("Reading Exception Table");

			List<Agent> chromtempEmailIds = agentDAO.readExceptionTableAgentIds();

			for (Agent e : chromtempEmailIds) {

				String emailId = e.getEmailId().trim();

				chromtempAgentTransaction = agentDAO.readExceptionTable(emailId);

				for (Agent t : chromtempAgentTransaction) {

					emailId = t.getEmailId().replaceAll("\\s+", "");
					String activityFromTime = t.getFromDate();
					String activityToTime = t.getToDate();
					String websiteVisited = t.getWebsitesVisited();
					String activityCode = t.getActivityCode();

					String activityFromDatesplit[] = activityFromTime.split(" ");
					String activityFromtimeSplt[] = activityFromDatesplit[1].split(":");
					String activityFromDate = activityFromDatesplit[0];
					Agent getDetailsFromDayMasterInput = new Agent();
					getDetailsFromDayMasterInput.setEmailId(emailId);
					getDetailsFromDayMasterInput.setDATE(activityFromDate);

					int entryCountInDayMaster = agentDAO.dayMasterCount(getDetailsFromDayMasterInput);

					if (entryCountInDayMaster == 1) {

						// exist

						Agent DayDetailsInput = new Agent();
						DayDetailsInput.setDATE(activityFromDate);
						DayDetailsInput.setEmailId(emailId);
						DayDetailsInput.setToDate(activityFromTime);
						DayDetailsInput.setActivityCode(activityCode);

						int dayDetailLastActivity = agentDAO.dayDetailLastActivity(DayDetailsInput);
						if (dayDetailLastActivity == 0) {
							int dayDetailCount = agentDAO.dayDetailCount(DayDetailsInput);

							Agent dataInsertInDayDetail = new Agent();
							dataInsertInDayDetail.setEmailId(emailId);
							dataInsertInDayDetail.setFromDate(t.getFromDate());
							dataInsertInDayDetail.setToDate(t.getToDate());
							dataInsertInDayDetail.setWebsitesVisited(t.getWebsitesVisited());
							dataInsertInDayDetail.setActivityCode(t.getActivityCode());
							dataInsertInDayDetail.setRownum((dayDetailCount + 1));
							dataInsertInDayDetail.setDATE(activityFromDate);

							int insertInDayDetailStatus = agentDAO.dayDetailInsert(dataInsertInDayDetail);
							if (insertInDayDetailStatus >= 0) {
								logger.info("Data is Successfully Inserted in Day Detail Table");
								logger.info("No Of Rows Inserted :" + insertInDayDetailStatus);

								int deleteStatus = agentDAO.exceptionTableDelete(t);
								if (deleteStatus >= 0) {
									logger.info("Data is Successfully deleted in Chrome Exception Table");
									logger.info("No Of Rows Deleted :" + deleteStatus);

								}
							}

						} else {

							Agent dataUpdateInDayDetail = new Agent();
							dataUpdateInDayDetail.setEmailId(emailId);
							dataUpdateInDayDetail.setFromDate(activityFromTime);
							dataUpdateInDayDetail.setToDate(activityToTime);
							dataUpdateInDayDetail.setActivityCode(activityCode);
							dataUpdateInDayDetail.setDATE(activityFromDate);

							int dataUpdateInDayDetailStatus = agentDAO.dayDetailUpdate(dataUpdateInDayDetail);
							if (dataUpdateInDayDetailStatus >= 0) {
								logger.info("Data is Successfully Updated in Day Detail Table");
								logger.info("No Of Rows Updated :" + dataUpdateInDayDetailStatus);

								int deleteStatus = agentDAO.exceptionTableDelete(t);
								if (deleteStatus >= 0) {
									logger.info("Data is Successfully deleted in Chrome Exception Table");
									logger.info("No Of Rows Deleted :" + deleteStatus);

								}
							}

						}

						List<Agent> loginDetailsFromDayMaster = agentDAO.getLoginTime(getDetailsFromDayMasterInput);
						String loginTimeFromDayMaster = "";
						for (Agent loginTimeFromDayMasterList : loginDetailsFromDayMaster) {
							loginTimeFromDayMaster = loginTimeFromDayMasterList.getLoginTime();

						}
						Agent activityHrsCalculation = new Agent();
						activityHrsCalculation.setEmailId(emailId);
						activityHrsCalculation.setFromDate(loginTimeFromDayMaster);
						activityHrsCalculation.setToDate(t.getToDate());

						List<Agent> activitydetails = agentDAO.activityHrsCalculation(activityHrsCalculation);

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
							logger.info("activity Code==" + activity.getActivityCode().trim());
							logger.info("activity Hrs==" + activity.getActivityHrs());
							if (activity.getActivityCode().trim().equalsIgnoreCase("1")) {
								ACT_O1 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("2")) {
								ACT_O2 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("3")) {
								ACT_O3 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("4")) {
								ACT_O4 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("5")) {
								ACT_O5 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("6")) {
								ACT_O6 = activity.getActivityHrs();
							}

							if (activity.getActivityCode().trim().equalsIgnoreCase("7")) {
								ACT_O7 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("8")) {
								ACT_O8 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("9")) {
								ACT_O9 = activity.getActivityHrs();
							}
							if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
								ACT_10 = activity.getActivityHrs();
							}

						}

						 
						Agent dataUpdateInDayMaster = new Agent();

						dataUpdateInDayMaster.setEmailId(emailId);
						dataUpdateInDayMaster.setDATE(activityFromDate);
						dataUpdateInDayMaster.setProdSum(ACT_O1);
						dataUpdateInDayMaster.setIdleSum(ACT_O2);
						dataUpdateInDayMaster.setBreakSum(ACT_O3);
						dataUpdateInDayMaster.setMealsSum(ACT_O4);
						dataUpdateInDayMaster.setHuddleSum(ACT_O5);
						dataUpdateInDayMaster.setWelnessSupportSum(ACT_O6);
						dataUpdateInDayMaster.setCoachingSum(ACT_O7);
						dataUpdateInDayMaster.setTeamMeetingSum(ACT_O8);
						dataUpdateInDayMaster.setFbTrainingSum(ACT_O9);
						dataUpdateInDayMaster.setNonFbTrainingSum(ACT_10);
						dataUpdateInDayMaster.setLogoutTime(t.getToDate());

						int updateInDayMasterStatus = agentDAO.dayMasterUpdate(dataUpdateInDayMaster);
						if (updateInDayMasterStatus >= 0) {
							logger.info("Data is Successfully Updated in Day Master Table");
							logger.info("No Of Rows Updated :" + updateInDayMasterStatus);

						}

					} else {

						String updateStatusFlag = "";

						int hour = Integer.parseInt(activityFromtimeSplt[0]);

						if (hour >= 0 && hour <= 10) {
							Agent previousDayDetailsInput = new Agent();
							previousDayDetailsInput.setDATE(minusDate(activityFromDate));
							previousDayDetailsInput.setEmailId(emailId);

							List<Agent> previousDayDetails = agentDAO
									.dayMasterPreviousDayLogout(previousDayDetailsInput);

							String agentIdFromDayMatser = "";
							String logOutTimeFromDayMatser = "";

							for (Agent previousDayDetailsList : previousDayDetails) {

								agentIdFromDayMatser = previousDayDetailsList.getAgentId();
								logOutTimeFromDayMatser = previousDayDetailsList.getLogoutTime();
							}
							float timeDifference = 0;
							if (!agentIdFromDayMatser.trim().equalsIgnoreCase("")) {
								timeDifference = dateTimeDifference(logOutTimeFromDayMatser, activityFromTime);
							}

							if (!agentIdFromDayMatser.trim().equalsIgnoreCase("") && timeDifference >= 0
									&& timeDifference <= 540) {
								// previous

								String previousDate = minusDate(activityFromDate);

								Agent DayDetailsInput = new Agent();
								DayDetailsInput.setDATE(previousDate);
								DayDetailsInput.setEmailId(emailId);
								DayDetailsInput.setToDate(activityFromTime);
								DayDetailsInput.setActivityCode(activityCode);

								int dayDetailLastActivity = agentDAO.dayDetailLastActivity(DayDetailsInput);
								if (dayDetailLastActivity == 0) {
									int dayDetailCount = agentDAO.dayDetailCount(DayDetailsInput);

									Agent dataInsertInDayDetail = new Agent();
									dataInsertInDayDetail.setEmailId(emailId);
									dataInsertInDayDetail.setFromDate(t.getFromDate());
									dataInsertInDayDetail.setToDate(t.getToDate());
									dataInsertInDayDetail.setWebsitesVisited(t.getWebsitesVisited());
									dataInsertInDayDetail.setActivityCode(t.getActivityCode());
									dataInsertInDayDetail.setRownum((dayDetailCount + 1));
									dataInsertInDayDetail.setDATE(previousDate);

									int insertInDayDetailStatus = agentDAO.dayDetailInsert(dataInsertInDayDetail);
									if (insertInDayDetailStatus >= 0) {
										logger.info("Data is Successfully Inserted in Day Detail Table");
										logger.info("No Of Rows Inserted :" + insertInDayDetailStatus);

										int deleteStatus = agentDAO.exceptionTableDelete(t);
										if (deleteStatus >= 0) {
											logger.info("Data is Successfully deleted in Chrome Exception Table");
											logger.info("No Of Rows Deleted :" + deleteStatus);

										}
									}

								} else {

									Agent dataUpdateInDayDetail = new Agent();
									dataUpdateInDayDetail.setEmailId(emailId);
									dataUpdateInDayDetail.setFromDate(activityFromTime);
									dataUpdateInDayDetail.setToDate(activityToTime);
									dataUpdateInDayDetail.setDATE(previousDate);
									dataUpdateInDayDetail.setActivityCode(activityCode);

									int dataUpdateInDayDetailStatus = agentDAO.dayDetailUpdate(dataUpdateInDayDetail);
									if (dataUpdateInDayDetailStatus >= 0) {
										logger.info("Data is Successfully Updated in Day Detail Table");
										logger.info("No Of Rows Updated :" + dataUpdateInDayDetailStatus);

										int deleteStatus = agentDAO.exceptionTableDelete(t);
										if (deleteStatus >= 0) {
											logger.info("Data is Successfully deleted in Chrome Exception Table");
											logger.info("No Of Rows Deleted :" + deleteStatus);

										}
									}

								}

								List<Agent> loginDetailsFromDayMaster = agentDAO
										.getLoginTime(getDetailsFromDayMasterInput);
								String loginTimeFromDayMaster = "";
								for (Agent loginTimeFromDayMasterList : loginDetailsFromDayMaster) {
									loginTimeFromDayMaster = loginTimeFromDayMasterList.getLoginTime();

								}
								Agent activityHrsCalculation = new Agent();
								activityHrsCalculation.setEmailId(emailId);
								activityHrsCalculation.setFromDate(loginTimeFromDayMaster);
								activityHrsCalculation.setToDate(t.getToDate());

								List<Agent> activitydetails = agentDAO.activityHrsCalculation(activityHrsCalculation);

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
									logger.info("activity Code==" + activity.getActivityCode().trim());
									logger.info("activity Hrs==" + activity.getActivityHrs());
									if (activity.getActivityCode().trim().equalsIgnoreCase("1")) {
										ACT_O1 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("2")) {
										ACT_O2 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("3")) {
										ACT_O3 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("4")) {
										ACT_O4 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("5")) {
										ACT_O5 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("6")) {
										ACT_O6 = activity.getActivityHrs();
									}

									if (activity.getActivityCode().trim().equalsIgnoreCase("7")) {
										ACT_O7 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("8")) {
										ACT_O8 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("9")) {
										ACT_O9 = activity.getActivityHrs();
									}
									if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
										ACT_10 = activity.getActivityHrs();
									}

								}

								 

								Agent dataUpdateInDayMaster = new Agent();

								dataUpdateInDayMaster.setEmailId(emailId);
								dataUpdateInDayMaster.setDATE(previousDate);
								dataUpdateInDayMaster.setProdSum(ACT_O1);
								dataUpdateInDayMaster.setIdleSum(ACT_O2);
								dataUpdateInDayMaster.setBreakSum(ACT_O3);
								dataUpdateInDayMaster.setMealsSum(ACT_O4);
								dataUpdateInDayMaster.setHuddleSum(ACT_O5);
								dataUpdateInDayMaster.setWelnessSupportSum(ACT_O6);
								dataUpdateInDayMaster.setCoachingSum(ACT_O7);
								dataUpdateInDayMaster.setTeamMeetingSum(ACT_O8);
								dataUpdateInDayMaster.setFbTrainingSum(ACT_O9);
								dataUpdateInDayMaster.setNonFbTrainingSum(ACT_10);
								
								
								
								dataUpdateInDayMaster.setLogoutTime(t.getToDate());
								int updateInDayMasterStatus = agentDAO.dayMasterUpdate(dataUpdateInDayMaster);
								if (updateInDayMasterStatus >= 0) {
									logger.info("Data is Successfully Updated in Day Master Table");
									logger.info("No Of Rows Updated :" + updateInDayMasterStatus);

								}

								updateStatusFlag = "updated";

								///////////////////

							}

						}

						if (!updateStatusFlag.trim().equalsIgnoreCase("updated")) {

							List<Agent> agentdetails = agentDAO.readAgentDetails(emailId);
							String agentName = "";

							String projectId = "";
							String subProjectId = "";
							String projectName = "";
							String subProjectName = "";
							String location = "";
							String hcmSupervisorId = "";
							String hcmSupervisorName = "";
							String billable = "";
							String onshoreOffshore = "";
							String agentId = "";

							String shiftFrom = "";
							String shiftTo = "";

							for (Agent e1 : agentdetails) {
								agentName = e1.getName();

								projectId = e1.getProjectId();
								subProjectId = e1.getSubProjectId();
								location = e1.getLocation();
								hcmSupervisorId = e1.getHcmSupervisorId();
								hcmSupervisorName = e1.getHcmSupervisorName();
								billable = e1.getBillable();
								onshoreOffshore = e1.getOnshoreOffshore();
								agentId = e1.getAgentId();
								projectName = e1.getProjectName();
								subProjectName = e1.getSubProjectName();

							}
							logger.info("agentName " + agentName);
							logger.info("projectId " + projectId);
							logger.info("location " + location);
							logger.info("HCM_Supervisor Id " + hcmSupervisorId);
							logger.info("HCM_Supervisor Name" + hcmSupervisorName);
							logger.info("Billable " + billable);
							logger.info("onshoreOffshore" + onshoreOffshore);
							logger.info("agentId" + agentId);
							logger.info("projectName" + projectName);
							logger.info("subProjectName" + subProjectName);
							logger.info("subProjectId" + subProjectId);

							int shiftHour = Integer.parseInt(activityFromtimeSplt[0]);
							String shiftTime = "";
							if (shiftHour >= 0 && shiftHour <= 3) {

								shiftTime = activityFromDatesplit[1];
							} else {
								shiftTime = activityFromDatesplit[1];
							}
							Agent shiftTimingsInput = new Agent();
							shiftTimingsInput.setProjectId(projectId);
							shiftTimingsInput.setSubProjectId(subProjectId);
							shiftTimingsInput.setLocation(location);
							shiftTimingsInput.setStartTime(shiftTime);

							List<Agent> shiftDetails = agentDAO.fetchShiftDetails(shiftTimingsInput);
							for (Agent e1 : shiftDetails) {
								shiftFrom = e1.getShiftFrom();
								shiftTo = e1.getShiftTo();

							}

							logger.info("shiftFrom" + shiftFrom);
							logger.info("shiftTo" + shiftTo);

							String errorDesc = "";
							if (agentName.trim().equalsIgnoreCase("")) {
								errorDesc = "agent master";

							}
							if (shiftFrom.trim().equalsIgnoreCase("") || shiftTo.trim().equalsIgnoreCase("")) {
								logger.info("Not Present in any shift");

							}
							t.setErrorDesc("please check " + errorDesc);

							if (agentName.trim().equalsIgnoreCase("")) {

								logger.info("errorDesc===" + errorDesc);

							} else {
								///////////

								Agent dayMasterInsert = new Agent();

								if (shiftHour >= 0 && shiftHour <= 3) {
									dayMasterInsert.setDATE(minusDate(activityFromDate));
								} else {
									dayMasterInsert.setDATE(activityFromDate);
								}
								dayMasterInsert.setEmailId(emailId);
								dayMasterInsert.setLoginTime(t.getFromDate());
								dayMasterInsert.setLogoutTime(t.getToDate());

								if (shiftFrom.trim().equalsIgnoreCase("") || shiftTo.trim().equalsIgnoreCase("")) {
									dayMasterInsert.setShiftTimings("N");
								} else {
									dayMasterInsert.setShiftTimings(shiftFrom + "-" + shiftTo);
								}
								dayMasterInsert.setProjectId(projectId);
								dayMasterInsert.setProjectName(projectName);
								dayMasterInsert.setSubProjectId(subProjectId);
								dayMasterInsert.setSubProjectName(subProjectName);
								dayMasterInsert.setHcmSupervisorId(hcmSupervisorId);
								dayMasterInsert.setHcmSupervisorName(hcmSupervisorName);
								dayMasterInsert.setBillable(billable);
								dayMasterInsert.setOnshoreOffshore(onshoreOffshore);
								dayMasterInsert.setLocation(location);
								dayMasterInsert.setName(agentName);
								dayMasterInsert.setAgentId(agentId);
								int inserStatus = agentDAO.dayMasterInsert(dayMasterInsert);

								if (inserStatus >= 0) {
									logger.info("Data is Successfully inserted in Day MasterTable");
									logger.info("No Of Rows Inserted :" + inserStatus);

									// exist

									Agent DayDetailsInput = new Agent();
									if (shiftHour >= 0 && shiftHour <= 3) {
										DayDetailsInput.setDATE(minusDate(activityFromDate));

									} else {
										DayDetailsInput.setDATE(activityFromDate);
									}
									DayDetailsInput.setEmailId(emailId);
									DayDetailsInput.setToDate(activityFromTime);
									DayDetailsInput.setActivityCode(activityCode);

									int dayDetailLastActivity = agentDAO.dayDetailLastActivity(DayDetailsInput);
									if (dayDetailLastActivity == 0) {
										int dayDetailCount = agentDAO.dayDetailCount(DayDetailsInput);

										Agent dataInsertInDayDetail = new Agent();
										dataInsertInDayDetail.setEmailId(emailId);
										dataInsertInDayDetail.setFromDate(t.getFromDate());
										dataInsertInDayDetail.setToDate(t.getToDate());
										dataInsertInDayDetail.setWebsitesVisited(t.getWebsitesVisited());
										dataInsertInDayDetail.setActivityCode(t.getActivityCode());
										dataInsertInDayDetail.setRownum((dayDetailCount + 1));
										if (shiftHour >= 0 && shiftHour <= 3) {
											dataInsertInDayDetail.setDATE(minusDate(activityFromDate));

										} else {
											dataInsertInDayDetail.setDATE(activityFromDate);
										}

										int insertInDayDetailStatus = agentDAO.dayDetailInsert(dataInsertInDayDetail);
										if (insertInDayDetailStatus >= 0) {
											logger.info("Data is Successfully Inserted in Day Detail Table");
											logger.info("No Of Rows Inserted :" + insertInDayDetailStatus);

											int deleteStatus = agentDAO.exceptionTableDelete(t);
											if (deleteStatus >= 0) {
												logger.info("Data is Successfully deleted in Chrome Exception Table");
												logger.info("No Of Rows Deleted :" + deleteStatus);

											}
										}

									} else {

										Agent dataUpdateInDayDetail = new Agent();
										dataUpdateInDayDetail.setEmailId(emailId);
										dataUpdateInDayDetail.setFromDate(activityFromTime);
										dataUpdateInDayDetail.setToDate(activityToTime);
										dataUpdateInDayDetail.setActivityCode(activityCode);
										if (shiftHour >= 0 && shiftHour <= 3) {
											dataUpdateInDayDetail.setDATE(minusDate(activityFromDate));

										} else {
											dataUpdateInDayDetail.setDATE(activityFromDate);
										}

										int dataUpdateInDayDetailStatus = agentDAO
												.dayDetailUpdate(dataUpdateInDayDetail);
										if (dataUpdateInDayDetailStatus >= 0) {
											logger.info("Data is Successfully Updated in Day Detail Table");
											logger.info("No Of Rows Updated :" + dataUpdateInDayDetailStatus);

											int deleteStatus = agentDAO.exceptionTableDelete(t);
											if (deleteStatus >= 0) {
												logger.info("Data is Successfully deleted in Chrome Exception Table");
												logger.info("No Of Rows Deleted :" + deleteStatus);

											}
										}

									}

									List<Agent> loginDetailsFromDayMaster = agentDAO
											.getLoginTime(getDetailsFromDayMasterInput);
									String loginTimeFromDayMaster = "";
									for (Agent loginTimeFromDayMasterList : loginDetailsFromDayMaster) {
										loginTimeFromDayMaster = loginTimeFromDayMasterList.getLoginTime();

									}
									Agent activityHrsCalculation = new Agent();
									activityHrsCalculation.setEmailId(emailId);
									activityHrsCalculation.setFromDate(loginTimeFromDayMaster);
									activityHrsCalculation.setToDate(t.getToDate());

									List<Agent> activitydetails = agentDAO
											.activityHrsCalculation(activityHrsCalculation);

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
										logger.info("activity Code==" + activity.getActivityCode().trim());
										logger.info("activity Hrs==" + activity.getActivityHrs());
										if (activity.getActivityCode().trim().equalsIgnoreCase("1")) {
											ACT_O1 = activity.getActivityHrs();
										}
										if (activity.getActivityCode().trim().equalsIgnoreCase("2")) {
											ACT_O2 = activity.getActivityHrs();
										}
										if (activity.getActivityCode().trim().equalsIgnoreCase("3")) {
											ACT_O3 = activity.getActivityHrs();
										}
										if (activity.getActivityCode().trim().equalsIgnoreCase("4")) {
											ACT_O4 = activity.getActivityHrs();
										}
										if (activity.getActivityCode().trim().equalsIgnoreCase("5")) {
											ACT_O5 = activity.getActivityHrs();
										}
										if (activity.getActivityCode().trim().equalsIgnoreCase("6")) {
											ACT_O6 = activity.getActivityHrs();
										}

										if (activity.getActivityCode().trim().equalsIgnoreCase("7")) {
											ACT_O7 = activity.getActivityHrs();
										}
										if (activity.getActivityCode().trim().equalsIgnoreCase("8")) {
											ACT_O8 = activity.getActivityHrs();
										}
										if (activity.getActivityCode().trim().equalsIgnoreCase("9")) {
											ACT_O9 = activity.getActivityHrs();
										}
										if (activity.getActivityCode().trim().equalsIgnoreCase("10")) {
											ACT_10 = activity.getActivityHrs();
										}

									}

								 
									Agent dataUpdateInDayMaster = new Agent();

									dataUpdateInDayMaster.setEmailId(emailId);
									dataUpdateInDayMaster.setDATE(activityFromDate);
									dataUpdateInDayMaster.setProdSum(ACT_O1);
									dataUpdateInDayMaster.setIdleSum(ACT_O2);
									dataUpdateInDayMaster.setBreakSum(ACT_O3);
									dataUpdateInDayMaster.setMealsSum(ACT_O4);
									dataUpdateInDayMaster.setHuddleSum(ACT_O5);
									dataUpdateInDayMaster.setWelnessSupportSum(ACT_O6);
									dataUpdateInDayMaster.setCoachingSum(ACT_O7);
									dataUpdateInDayMaster.setTeamMeetingSum(ACT_O8);
									dataUpdateInDayMaster.setFbTrainingSum(ACT_O9);
									dataUpdateInDayMaster.setNonFbTrainingSum(ACT_10);
									dataUpdateInDayMaster.setLogoutTime(t.getToDate());
									int updateInDayMasterStatus = agentDAO.dayMasterUpdate(dataUpdateInDayMaster);
									if (updateInDayMasterStatus >= 0) {
										logger.info("Data is Successfully Updated in Day Master Table");
										logger.info("No Of Rows Updated :" + updateInDayMasterStatus);

									}

								}

							}

						}

					}
				}

			}
			logger.info("*******************************SCHEDULER COMPLETED*************************************");

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
	public float dateTimeDifference(String dateStart, String dateStop) {
		logger.error("Inside Date Difference Startdate=" + dateStart + " EndDate" + dateStop);
		String startDtsplit[] = dateStart.split(" ");
		String formattedStartDate[] = startDtsplit[0].split("-");

		String endDtsplit[] = dateStop.split(" ");
		String formattedEndDate[] = endDtsplit[0].split("-");
		// HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			// logger.info("Formatted Start
			// Date=="+formattedStartDate[1]+"/"+formattedStartDate[2]+"/"+formattedStartDate[0]+"
			// "+startDtsplit[1]);
			// logger.info("Formatted End
			// Date=="+formattedEndDate[1]+"/"+formattedEndDate[2]+"/"+formattedEndDate[0]+"
			// "+endDtsplit[1]);
			// d1 =
			// format.parse(formattedStartDate[1]+"/"+formattedStartDate[2]+"/"+formattedStartDate[0]+"
			// "+startDtsplit[1]);
			// d2 =
			// format.parse(formattedEndDate[1]+"/"+formattedEndDate[2]+"/"+formattedEndDate[0]+"
			// "+endDtsplit[1]);
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
			// in milliseconds

			long milliseconds1 = d1.getTime();
			long milliseconds2 = d2.getTime();

			long diff = milliseconds2 - milliseconds1;
			long diffSeconds = diff / 1000;
			long diffMinutes = diff / (60 * 1000);
			long diffHours = diff / (60 * 60 * 1000);
			long diffDays = diff / (24 * 60 * 60 * 1000);

			return diffMinutes;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception Occured in dateTimeDifference" + e);
		}
		return 0;

	}

	/**
	 * @param Date
	 * @return Minus one day with give given date
	 */
	public String minusDate(String Date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String datesplt[] = Date.split("-");
			Date currentDate = new Date(datesplt[0] + "/" + datesplt[1] + "/" + datesplt[2]);

			System.out.println(dateFormat.format(currentDate));

			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);

			// manipulate date

			c.add(Calendar.DATE, -1); // same with c.add(Calendar.DAY_OF_MONTH,
										// 1);

			// convert calendar to date
			Date currentDateMinusOne = c.getTime();

			System.out.println(dateFormat.format(currentDateMinusOne));
			return dateFormat.format(currentDateMinusOne);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception Occured in minusDate" + e);
		}
		return "";
	}

}
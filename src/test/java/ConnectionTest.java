import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.automation.vo.Agent;
import javax.sql.DataSource;
import com.automation.dao.AgentDAO;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectionTest {

	// Convert Date to Calendar
	private Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		try {

			System.out.println("Reading Temporary Table");
			AgentDAO dao = (AgentDAO) context.getBean("agentDAO");
			///////////////////////// READING CHROME TEMPORARY
			///////////////////////// TABLE///////////////////
			List<Agent> chromtemlist = dao.readChromTempMasterTable();

			for (Agent e : chromtemlist) {

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
				System.out.println("errorDesc" + errorDesc);
				if (errorDesc.trim().equalsIgnoreCase("")) {

					String LoginDate[] = e.getLoginTime().split(" ");
					int count = dao.totalAgentCountInDayMaster(e.getEmailId(), LoginDate[0]);
					System.out.println("count quwery====" + count);
					if (count == 0) {

						Agent dataInsert = new Agent();
						dataInsert.setDATE(LoginDate[0]);
						dataInsert.setEmailId(e.getEmailId());
						dataInsert.setShiftTimings(ShiftTimings);
						dataInsert.setName(agentName);
						dataInsert.setLoginTime(e.getLoginTime());
						dataInsert.setLogoutTime(e.getLogoutTime());
						dataInsert.setProductiveHours(e.getProductiveHours());

						int status = dao.dataInsertionInDayMaster(dataInsert);
						if (status >= 0) {
							Agent dataDelete = new Agent();
							dataDelete.setEmailId(e.getEmailId());
							dataDelete.setLoginTime(e.getLoginTime());

							int deleteStatus = dao.deleteFromChromeTempMaster(dataDelete);
							if (deleteStatus >= 0) {

								Agent dataInsertInDayDetail = new Agent();
								dataInsertInDayDetail.setEmailId(e.getEmailId());
								dataInsertInDayDetail.setLogoutTime(e.getLogoutTime());

								int dataInsertInDayDetailStatus = dao.dataInsertionInDayDetail(dataInsertInDayDetail);
								if (dataInsertInDayDetailStatus >= 0) {

									Agent dataDeletefromChromTempDetails = new Agent();

									int dataDeletionInDayDetailStatus = dao
											.deleteFromChromeTempDetail(dataInsertInDayDetail);
									if (dataDeletionInDayDetailStatus >= 0) {

										Agent idleHrsCalculation = new Agent();
										idleHrsCalculation.setEmailId(e.getEmailId());
										idleHrsCalculation.setLogoutTime(e.getLogoutTime());
										idleHrsCalculation.setLoginTime(e.getLoginTime());
										List<Agent> idlehrslist = dao.CalculateIdleHrs(idleHrsCalculation);
										for (Agent i1 : idlehrslist) {
											Agent idleHrsUpdation = new Agent();
											idleHrsUpdation.setEmailId(e.getEmailId());
											idleHrsUpdation.setDATE(LoginDate[0]);
											idleHrsUpdation.setIdleHours(i1.getIdleHours());

											int idleHrsUpdationInDayMaster = dao
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

					Agent dataInsert = new Agent();

					dataInsert.setEmailId(e.getEmailId());

					dataInsert.setName(e.getName());
					dataInsert.setLoginTime(e.getLoginTime());
					dataInsert.setLogoutTime(e.getLogoutTime());
					dataInsert.setProductiveHours(e.getProductiveHours());

					dataInsert.setErrorDesc("Email Id is missing in Agent Master");
					int status = dao.dataInsertionInException(dataInsert);

					System.out.println("status===" + status);

					if (status >= 0) {
						Agent dataDelete = new Agent();
						dataDelete.setEmailId(e.getEmailId());
						dataDelete.setLoginTime(e.getLoginTime());

						int deleteStatus = dao.deleteFromChromeTempMaster(dataDelete);
						if (deleteStatus >= 0) {

							Agent dataInsertInDayDetail = new Agent();
							dataInsertInDayDetail.setEmailId(e.getEmailId());
							dataInsertInDayDetail.setLogoutTime(e.getLogoutTime());

							int dataInsertInDayDetailStatus = dao.dataInsertionInDayDetail(dataInsertInDayDetail);
							if (dataInsertInDayDetailStatus >= 0) {
								int dataDeletionInDayDetailStatus = dao
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

			List<Agent> chromExceplist = dao.readChromExceptionTable();

			for (Agent e : chromExceplist) {

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
				System.out.println("errorDesc" + errorDesc);
				if (errorDesc.trim().equalsIgnoreCase("")) {

					String LoginDate[] = e.getLoginTime().split(" ");
					int count = dao.totalAgentCountInDayMaster(e.getEmailId(), LoginDate[0]);
					System.out.println("count quwery====" + count);
					if (count == 0) {

						Agent dataInsert = new Agent();
						dataInsert.setDATE(LoginDate[0]);
						dataInsert.setEmailId(e.getEmailId());
						dataInsert.setShiftTimings(ShiftTimings);
						dataInsert.setName(agentName);
						dataInsert.setLoginTime(e.getLoginTime());
						dataInsert.setLogoutTime(e.getLogoutTime());
						dataInsert.setProductiveHours(e.getProductiveHours());

						int status = dao.dataInsertionInDayMaster(dataInsert);
						if (status >= 0) {
							Agent dataDelete = new Agent();
							dataDelete.setEmailId(e.getEmailId());
							dataDelete.setLoginTime(e.getLoginTime());

							int deleteStatus = dao.deleteFromChromeException(dataDelete);
							if (deleteStatus >= 0) {

								Agent dataInsertInDayDetail = new Agent();
								dataInsertInDayDetail.setEmailId(e.getEmailId());
								dataInsertInDayDetail.setLogoutTime(e.getLogoutTime());

								Agent idleHrsCalculation = new Agent();
								idleHrsCalculation.setEmailId(e.getEmailId());
								idleHrsCalculation.setLogoutTime(e.getLogoutTime());
								idleHrsCalculation.setLoginTime(e.getLoginTime());
								List<Agent> idlehrslist = dao.CalculateIdleHrs(idleHrsCalculation);
								for (Agent i1 : idlehrslist) {
									Agent idleHrsUpdation = new Agent();
									idleHrsUpdation.setEmailId(e.getEmailId());
									idleHrsUpdation.setDATE(LoginDate[0]);
									idleHrsUpdation.setIdleHours(i1.getIdleHours());

									int idleHrsUpdationInDayMaster = dao.updateIdleHrsInDayMaster(idleHrsUpdation);
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
			System.out.println("Exception" + e);
			e.printStackTrace();
		}
	}

}

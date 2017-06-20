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

	//Convert Date to Calendar
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
			/////////////////////////READING CHROME TEMPORARY TABLE///////////////////		
			List<Agent> chromtemlist = dao.readChromTempTable();

			for (Agent e : chromtemlist) {
				System.out.println(e.getEmailId());
				System.out.println(e.getName());
				System.out.println(e.getIdleFrom());
				System.out.println(e.getIdleTo());
				System.out.println(e.getWebsitesVisited());
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

					int count = dao.totalAgentCountInDayMaster(e.getEmailId(), "2016-06-18");
					System.out.println("count quwery====" + count);
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

					System.out.println("status===" + status);

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

					System.out.println("status===" + status);

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
				System.out.println(e.getEmailId());
				System.out.println(e.getName());
				System.out.println(e.getIdleFrom());
				System.out.println(e.getIdleTo());
				System.out.println(e.getWebsitesVisited());
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

					int count = dao.totalAgentCountInDayMaster(e.getEmailId(), "2016-06-18");
					System.out.println("count quwery====" + count);
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

					System.out.println("status===" + status);

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
				dataInsert.setDATE(e.getDATE());
				dataInsert.setEmailId(e.getEmailId());
	dataInsert.setIdleHours(e.getIdleHours());

				int status = dao.updateIdleHrsInDayDetail(dataInsert);
			
			
			}
			
			
			
			
			
			
			
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception" + e);
			e.printStackTrace();
		}
	}

}

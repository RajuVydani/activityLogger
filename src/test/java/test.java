import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.automation.dao.AgentDAO;
 
 

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test
{
    public static void main( String[] args ) throws Exception
    {
    	new ClassPathXmlApplicationContext("Spring-Quartz.xml");
    }
}

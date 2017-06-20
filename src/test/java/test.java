import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {

	public static void main(String[] argv) throws ParseException {

		//1. Create a Date from String
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-mm-DD hh:mm:ss");
		String dateInString = "2015-03-21 10:20:56";
		Date date = sdf.parse(dateInString);
       

		//2. Test - Convert Date to Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	 
		System.out.println( sdf.format(calendar.getTime()));
 

	}

	//Convert Date to Calendar
 
 
}
 

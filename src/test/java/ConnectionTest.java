import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectionTest {

	public static void main(String[] args) {
		ApplicationContext context =
	    		new ClassPathXmlApplicationContext("applicationContext.xml");

		DataSource ds = (DataSource) context.getBean("dataSource");
	       
	        try {
				System.out.println(ds.getConnection());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}

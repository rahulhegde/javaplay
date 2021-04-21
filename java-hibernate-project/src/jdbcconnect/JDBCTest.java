package jdbcconnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCTest {

	public static void main(String[] args) {
		String jdbcUrl = "jdbc:mysql://localhost:3306/hb_student_tracer?useSSL=false&serverTimezone=UTC";
		String user = "hbstudent";
		String password = "hbstudent";
		
		try {
			Connection myconn = DriverManager.getConnection(jdbcUrl, user, password);
			
			System.out.println("connection success: " + myconn);
			
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}		
	}
}

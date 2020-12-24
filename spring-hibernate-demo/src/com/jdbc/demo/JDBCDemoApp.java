package com.jdbc.demo;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCDemoApp {

	public static void main(String[] args) {
	
		String url = "jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=true";
		String user = "hbstudent";
		String password = "hbstudent";
				
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connection Successful!! client info: " + conn.getClientInfo() + ", " + conn.getCatalog() + ", " + conn.getNetworkTimeout());			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

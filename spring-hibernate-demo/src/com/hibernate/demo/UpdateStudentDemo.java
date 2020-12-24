package com.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class UpdateStudentDemo {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
								 .configure("hibernate.cfg.xml")
								 .addAnnotatedClass(Student.class)
								 .buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Student student = session.get(Student.class, 4);
			// multiple updates is translated to a single SQL query
			student.setFirstName("Rahul1");
			session.getTransaction().commit();
			
			// bulk update - get a new session always for a new transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			session.createQuery("update Student set emailId='hegde@gmail.com' where firstName='rahul'").executeUpdate();
			session.getTransaction().commit();
			
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		} finally {
			factory.close();
		}
	}
}

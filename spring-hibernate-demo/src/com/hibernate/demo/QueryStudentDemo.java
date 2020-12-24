package com.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class QueryStudentDemo {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
								 .configure("hibernate.cfg.xml")
								 .addAnnotatedClass(Student.class)
								 .buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();

			System.out.println("use of AND");
			List<Student> theStudents = session.createQuery("from Student s where s.firstName='rahul' AND s.lastName='Hegde'").getResultList();
			displayStudents(theStudents);
			
			
			System.out.println("use of Like");
			theStudents = session.createQuery("from Student s where s.emailId LIKE '%@gmail.com'").getResultList();
			displayStudents(theStudents);

			session.getTransaction().commit();

		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		} finally {
			factory.close();
		}
	}

	private static void displayStudents(List<Student> theStudents) {
		for (Student student : theStudents) {
			System.out.println(student);
		}
	}
}

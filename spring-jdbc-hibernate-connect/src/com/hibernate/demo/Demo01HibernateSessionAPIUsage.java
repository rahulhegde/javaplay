package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DeleteStudentDemo {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
								 .configure("hibernate.cfg.xml")
								 .addAnnotatedClass(Student.class)
								 .buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Student student = session.get(Student.class, 1);
			if (student != null) {
				session.delete(student);
			}
			session.createQuery("delete from Student where emailId like '%hegde@gmail.com'").executeUpdate();
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		} finally {
			factory.close();
		}
	}
}

package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OneOnOneDeleteDemoApp {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// delete using object
//			session.beginTransaction();
//			Instructor tmpInstructor = session.get(Instructor.class, 3);
//			System.out.println("instrutor: " + tmpInstructor);
//			System.out.println("instrutor detail: " + tmpInstructor.getInstructorDetail());
//			session.delete(tmpInstructor);
//			session.getTransaction().commit();			
			
			
			// delete using query, this does not delete the associated records
			// deletion of associated records has to be done using another hibernate query language call
			session.beginTransaction();
			session.createQuery("delete from Instructor I where I.firstName='saanvi'").executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		System.out.println("save");
	}

}

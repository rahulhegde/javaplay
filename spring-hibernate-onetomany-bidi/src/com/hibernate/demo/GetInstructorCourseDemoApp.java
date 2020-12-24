package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class GetInstructorCourseDemoApp {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			Instructor instructor = session.get(Instructor.class, 17);
			// API getCourses() will cause fetching of detauls existing open session - Eager Loading.
			//System.out.println("instructor: " + instructor.getCourses());
			session.getTransaction().commit();		

			session.close();
			// print here will fails as OnetoMany uses Lazy Loading and the session
			// is closed.
			// Option1: Do a fetch of the getCourses before session close.
			// Option2: Use HQL FETCH JOIN
			System.out.println("instructor: " + instructor.getCourses());
	
		} finally {
			session.close();
		}
		
		System.out.println("save");
	}

}

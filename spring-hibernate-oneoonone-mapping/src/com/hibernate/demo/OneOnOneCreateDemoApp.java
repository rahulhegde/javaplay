package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OneOnOneCreateDemoApp {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// insert operation for bidirectional
			session.beginTransaction();
			InstructorDetail instructorDetail = new InstructorDetail("saanvihegdeBiDi", "studyBiDi");
			Instructor instructor = new Instructor("saanviBiDi", "hegdeBiDi", "saanviBiDi@gmail.com");
			instructorDetail.setInstructor(instructor);
			// setting the instructor-instructor detail information is required
			// else the instructor is inserted with null as foreign key information 
			instructor.setInstructorDetail(instructorDetail);
			session.save(instructorDetail);
			session.getTransaction().commit();
			
		} finally {
			session.close();
		}
		
		System.out.println("save");
	}

}

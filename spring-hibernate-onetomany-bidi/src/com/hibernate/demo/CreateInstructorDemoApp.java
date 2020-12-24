package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateInstructorDemoApp {

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
			
			// instructor and instructor-detail relation
			Instructor instructor = new Instructor("saanvi", "hegde", "saanvi@gmail.com");
			InstructorDetail instructorDetail = new InstructorDetail("saanvihegde", "study");
			instructor.setInstructorDetail(instructorDetail);
			session.save(instructor);
			
			// course and instructor relation
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		System.out.println("save");
	}

}

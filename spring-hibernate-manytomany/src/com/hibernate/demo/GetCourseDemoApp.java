package com.hibernate.demo;

import java.util.List;

import javax.persistence.Id;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.id.IdentifierGenerator;

public class GetCourseDemoApp {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			Course course2 = session.get(Course.class, 10);
			System.out.println("course: " + course2);
			session.getTransaction().commit();		
		} finally {
			session.close();
		}
	}
}

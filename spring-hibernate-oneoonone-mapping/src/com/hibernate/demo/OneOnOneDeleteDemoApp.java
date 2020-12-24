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
//			InstructorDetail tmpInstructorDetail= session.get(InstructorDetail.class, 7);
//			System.out.println("instructor detail: " + tmpInstructorDetail);
//			// handle the foreign key constraint violation by setting the 
//			// instructor foreign key to an explicit null
//			tmpInstructorDetail.getInstructor().setInstructorDetail(null);
//			session.delete(tmpInstructorDetail);
//			session.getTransaction().commit();			
						
			// delete using query does not succeed as it gives a foreign key violation
			// does not create a relation
			session.beginTransaction();
			session.createQuery("delete from InstructorDetail I where I.hobby='study'").executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		System.out.println("save");
	}

}

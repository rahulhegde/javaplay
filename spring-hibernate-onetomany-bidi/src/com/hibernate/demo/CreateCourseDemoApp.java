package com.hibernate.demo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateCourseDemoApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			
//			// Test 1 - Use of Persist (transient to persistence), Detach (persistence to detached), Merge (detached to persistence)
//			//output
//			// Hibernate: insert into instructor (email, first_name, instructor_detail_id, last_name) values (?, ?, ?, ?)
//			// Hibernate: select instructor0_.id as id1_1_2_, instructor0_.email as email2_1_2_, instructor0_.first_name as first_na3_1_2_, instructor0_.instructor_detail_id as instruct5_1_2_, instructor0_.last_name as last_nam4_1_2_, courses1_.instructor_id as instruct3_0_4_, courses1_.id as id1_0_4_, courses1_.id as id1_0_0_, courses1_.instructor_id as instruct3_0_0_, courses1_.title as title2_0_0_, instructor2_.id as id1_2_1_, instructor2_.hobby as hobby2_2_1_, instructor2_.youtube_channel as youtube_3_2_1_ from instructor instructor0_ left outer join course courses1_ on instructor0_.id=courses1_.instructor_id left outer join instructor_detail instructor2_ on instructor0_.instructor_detail_id=instructor2_.id where instructor0_.id=?
//			// Hibernate: insert into course (instructor_id, title) values (?, ?)
//			// Hibernate: insert into course (instructor_id, title) values (?, ?)
//			// Hibernate: update instructor set email=?, first_name=?, instructor_detail_id=?, last_name=? where id=?
//				
//			Instructor instructor = new Instructor("rahul", "hegde",  "hegde.rahul@gmail.com");
//			Course course1 = new Course("Art24");
//			Course course2 = new Course("Math24");
//			instructor.add(course1);
//			instructor.add(course2);
//			
//			// Remove Cascades.ALL for @onetomany Courses (cascades={})
//			session.beginTransaction();
//			session.save(instructor);		
//			session.evict(instructor);
//			session.getTransaction().commit();		
//
//			// use merge API -JPA
//			session = factory.getCurrentSession();
//			session.beginTransaction();
//			// instructor object is evicted from Persistent Context (session).
//			// This or same object reference in detached state is merged back to Persistent 
//			// state. Any operation further on the merged object is on the same object
//			Instructor mergedInstructor = (Instructor) session.merge(instructor);
//			mergedInstructor.setFirstName("hegderahul");
//			session.getTransaction().commit();
			
			
			// TEST 2 - Save and Update (bring the object from detached to persistence state)
			// output
			//  Hibernate: insert into instructor (email, first_name, instructor_detail_id, last_name) values (?, ?, ?, ?)
			// 	Hibernate: update instructor set email=?, first_name=?, instructor_detail_id=?, last_name=? where id=?
			Instructor t2Instructor1 = new Instructor("rahul", "hegde",  "hegde.rahul@gmail.com");
			session = factory.getCurrentSession();
			session.beginTransaction();
			/// update cannot be called on transient state object, result Persistence Exception
			// session.update(t2Instructor1);
			session.persist(t2Instructor1);
			session.detach(t2Instructor1);
			session.update(t2Instructor1);
			t2Instructor1.setFirstName("rahultest2");
			session.getTransaction().commit();
			
		
			

		} finally {
			session.close();
		}
		
		System.out.println("save");
	}

}

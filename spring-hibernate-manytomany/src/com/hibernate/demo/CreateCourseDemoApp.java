package com.hibernate.demo;

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
				.addAnnotatedClass(Review.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			
//			// Test 1 - Use of Persist (transient to persistence), Detach (persistence to detached), Merge (detached to persistence)
//			//output
//			Hibernate: insert into instructor (email, first_name, instructor_detail_id, last_name) values (?, ?, ?, ?)
//			Hibernate: insert into course (instructor_id, title) values (?, ?)
//			Hibernate: insert into review (comment) values (?)
//			Hibernate: insert into review (comment) values (?)
//			Hibernate: insert into review (comment) values (?)
//			Hibernate: insert into course (instructor_id, title) values (?, ?)
//			Hibernate: update review set course_id=? where id=?
//			Hibernate: update review set course_id=? where id=?
//			Hibernate: update review set course_id=? where id=?
//			
			session.beginTransaction();

			Instructor instructor = new Instructor("rahul", "hegde",  "hegde.rahul@gmail.com");
			Course course1 = new Course("Art26");
			Course course2 = new Course("Math26");
			instructor.add(course1);
			instructor.add(course2);
			
			Review review1 = new Review("Good course!");
			Review review2 = new Review("Average course!");
			Review review3 = new Review("improve course!");

			course1.addReview(review1);
			course1.addReview(review2);
			course1.addReview(review3);

			// use persist (JPA) rather using save
			session.persist(instructor);
			session.getTransaction().commit();		
		
			// TEST 2 - Save and Update (bring the object from detached to persistence state)
			// output
			//  Hibernate: insert into instructor (email, first_name, instructor_detail_id, last_name) values (?, ?, ?, ?)
			// 	Hibernate: update instructor set email=?, first_name=?, instructor_detail_id=?, last_name=? where id=?
//			Instructor t2Instructor1 = new Instructor("rahul", "hegde",  "hegde.rahul@gmail.com");
//			session = factory.getCurrentSession();
//			session.beginTransaction();
//			/// update cannot be called on transient state object, result Persistence Exception
//			// session.update(t2Instructor1);
//			session.persist(t2Instructor1);
//			session.detach(t2Instructor1);
//			session.update(t2Instructor1);
//			t2Instructor1.setFirstName("rahultest2");
//			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		System.out.println("save");
	}

}

package com.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class GetLazyLoadingOptionDemo {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// OnetoMany by default uses Lazy Loading/
			// Courses field is type and will be lazy loaded. This requires session 
			// to be open for lazy loading to fetch the data.
			
			// TwoWays to come-over and load the courses.
			
			// Option1: Do a fetch of the getCourses before session close.
			session.beginTransaction();
			Instructor instructor = session.get(Instructor.class, 17);
			// API getCourses() will cause fetching of courses on an existing open session - Eager Loading.
			System.out.println("instructor: " + instructor.getCourses());
			session.getTransaction().commit();		
			session.close();
			System.out.println("instructor: " + instructor.getCourses());	
			
			// option 1.2, use HQL with Instructor object fetched
			session = factory.getCurrentSession();
			session.beginTransaction();
			Query<Course> tmpQuery = session.createQuery("select c from Course c "
                    + "where c.instructor.id=:theInstructorId", Course.class); 
			tmpQuery.setParameter("theInstructorId", 17);
			
			List<Course> courses = tmpQuery.getResultList();
			
			for (Course course : courses) {
				System.out.println("Course : " + course);
			}
			session.getTransaction().commit();
			courses = null;
							
			// Option2: Use HQL FETCH JOIN
			session = factory.getCurrentSession();
			session.beginTransaction();
			Query<Instructor> query = session.createQuery("select i from Instructor i "
					+ "JOIN FETCH i.courses "
					+ "where i.id=:InstructionId", Instructor.class);
			query.setParameter("InstructionId", 17);
			
			Instructor tmpInstructor = query.getSingleResult();
			session.getTransaction().commit();		
			session.close();
			System.out.println("instructor: " + tmpInstructor.getCourses());
		} finally {
			session.close();
		}
		
		System.out.println("save");
	}

}

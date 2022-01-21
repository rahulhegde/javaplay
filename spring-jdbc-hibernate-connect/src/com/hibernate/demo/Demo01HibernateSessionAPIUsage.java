package com.hibernate.demo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernate.demo.entity.Student;

public class Demo01HibernateSessionAPIUsage {
	SessionFactory factory;
	Demo01HibernateSessionAPIUsage(SessionFactory factory){
		this.factory = 	factory;
	}

	public static void main(String[] args) {
		// per process, create a single factory
		SessionFactory factory = new Configuration()
								 .configure("hibernate.cfg.xml")
								 .addAnnotatedClass(Student.class)
								 .buildSessionFactory();
		Demo01HibernateSessionAPIUsage test = new Demo01HibernateSessionAPIUsage(factory);
		
		// pure JPA API usage
		int studentId = test.insertUsingJPAPersistUsage();
		test.selectUsingJPAFindUsage(studentId);
		test.updateUsingHibernateGetUsage(studentId);
		test.selectUsingHibernateGetUsage(studentId);
		test.mergeUsage(studentId);
		test.deleteUsingJPARemoveUsage(studentId);
		
		// Hibernate API usage
		studentId = test.insertUsingHibernateSaveUsage();
		test.selectUsingHibernateGetUsage(studentId);
		test.updateUsingHibernateGetUsage(studentId);
		test.selectUsingHibernateGetUsage(studentId);
		test.mergeUsage(studentId);
		test.deleteUsingJPARemoveUsage(studentId);
		
		// native query
		List<Integer> studentIdList = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			studentIdList.add(test.insertUsingHibernateSaveUsage());
		}
		test.selectUsingJPAQueryHSQLUsage(studentIdList);
		test.selectUsingJPASQLMappingUsage(studentIdList);
		test.selectUsingJPANativeQueryUsage(studentIdList);
		test.selectUsingJPANamedQueryUsage(studentIdList);
		test.updatesingJPANativeQueryUsage(studentIdList);
		
		for (int i = 0; i < 5; i++) {
			test.deleteUsingJPARemoveUsage(studentIdList.get(i));
		}
		
		factory.close();
	}
	
	private int insertUsingJPAPersistUsage() {
		Session session = factory.getCurrentSession();
		Student student = new Student();
		try {
			session.beginTransaction();
			student.setFirstName("demo01_rahul");
			student.setLastName("demo01_hegde");
			student.setEmailId("demo01_hegde_rahul@gmail.com");
			session.persist(student);
			System.out.println("insertUsingJPAPersistUsage student - before: " + student.toString());
			session.getTransaction().commit();
			System.out.println("insertUsingJPAPersistUsage student: after: " + student.toString());
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
		return student.getId();
	}
	
	
	private int insertUsingHibernateSaveUsage() {
		Session session = factory.getCurrentSession();
		Student student = new Student();
		try {
			session.beginTransaction();
			student.setFirstName("demo01_rahul");
			student.setLastName("demo01_hegde");
			student.setEmailId("demo01_hegde_rahul@gmail.com");
			int studentId= (int) session.save(student);
			System.out.println("insertUsingHibernateSaveUsage - studentId: " + studentId);
			session.getTransaction().commit();
			System.out.println("insertUsingHibernateSaveUsage after commit: " + student.toString());
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
		return student.getId();
	}
	
	private void selectUsingJPAFindUsage(int studentId) {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Student student = session.find(Student.class, studentId);
			System.out.println("selectUsingJPAFindUsage student: " + student.toString());
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
	}
	
	private void selectUsingHibernateGetUsage(int studentId) {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			System.out.println("selectUsingGetUsage student: " + student.toString());
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
	}
	
	private void updateUsingHibernateGetUsage(int studentId) {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			student.setEmailId("demo_hegde.rahul_update@gmail.com");
			System.out.println("updateUsingGetUsage student: " + student.toString());
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
	}
	
	// merge using the @id if zero - its a new record else 
	// select record and update the fields provided for merge
	private void mergeUsage(int studentId) {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Student student = new Student();
			student.setId(78);
			student.setFirstName("demo01_rahul");
			student.setEmailId("demo_hegde.rahul_merge@gmail.com");
			System.out.println("mergeUsage student - before: " + student.toString());
			session.merge(student);
			System.out.println("mergeUsage student - after: " + student.toString());

			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
	}
	
	private void deleteUsingJPARemoveUsage(int studentId) {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			session.remove(student);
			System.out.println("deleteUsingJPARemoveUsage student: " + student.toString());
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
	}
	
	private void deleteUsingHibernateDeletesUsage(int studentId) {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			session.delete(student);
			System.out.println("deleteUsingHibernateDeletesUsage student: " + student.toString());
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
		session.close();
	}
	
	
	// this API demonstrate use of native query SQL (createNativeQuery) - less portable across databases use Hibernate SQL
	private void selectUsingJPANativeQueryUsage(List<Integer> studentIdList) {
		Session session = factory.getCurrentSession();
		
		System.out.println(studentIdList);
		try {
			session.beginTransaction();
			Query getStudetListQuery = session.createNativeQuery("SELECT * FROM hb_student_tracker.student s WHERE id in (:studentList)", Student.class);
			getStudetListQuery.setParameter("studentList", studentIdList);
			List<Student> fetchStudentList = getStudetListQuery.getResultList();
			
			System.out.println("student id list sent for fetch: " + studentIdList);
			
			for(Student student : fetchStudentList) {
				System.out.println("selectUsingJPANativeQueryUsage: " + student);
			}
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
		session.close();
	}
	
	// this API demonstrate use of HSQL - use createQuery
	private void selectUsingJPAQueryHSQLUsage(List<Integer> studentIdList) {
		Session session = factory.getCurrentSession();
		
		System.out.println(studentIdList);
		try {
			session.beginTransaction();
			Query getStudetListQuery = session.createQuery("FROM Student s WHERE s.id IN (:studentList)", Student.class);
			getStudetListQuery.setParameter("studentList", studentIdList);
			List<Student> fetchStudentList = getStudetListQuery.getResultList();
			
			System.out.println("selectUsingJPANativeQueryHSQLUsage: student id list sent for fetch: " + studentIdList);
			
			for(Student student : fetchStudentList) {
				System.out.println("selectUsingJPANativeQueryHSQLUsage: " + student);
			}
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
		session.close();
	}
	
	
	// this API demonstrate a existing entity class (student) can be mapped to resultset having 
	// different column definition. This will save not to define another Entity class
	// EntityResultMapping can also be defined in ORM XML.
	// Reference: https://thorben-janssen.com/result-set-mapping-basics/
	private void selectUsingJPASQLMappingUsage(List<Integer> studentIdList) {
		Session session = factory.getCurrentSession();
		
		System.out.println(studentIdList.get(0));
		try {
			session.beginTransaction();
			Query getStudetListQuery = session.createNativeQuery("SELECT s.id as studentId, s.first_name as studentFirstName, "
					+ "s.last_name as studentLastName, s.email as studentEmailId "
					+ "FROM hb_student_tracker.student s WHERE s.id in (:studentId)", "StudentEntityMapping");
			getStudetListQuery.setParameter("studentId", studentIdList.get(0));
			Student student = (Student) getStudetListQuery.getSingleResult();
			
			System.out.println("selectUsingJPASQLMappingUsage: student id list sent for fetch: " + studentIdList);

			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
		session.close();
	}
	
	// this API demonstrate - named query defined in the entity - alternate to native query
	private void selectUsingJPANamedQueryUsage(List<Integer> studentIdList) {
		Session session = factory.getCurrentSession();
		
		System.out.println(studentIdList);
		try {
			session.beginTransaction();
			Query getStudetListQuery = session.createNamedQuery("Student.getListUsingInQuery");
			getStudetListQuery.setParameter("studentList", studentIdList);
			List<Student> fetchStudentList = getStudetListQuery.getResultList();
			
			System.out.println("student id list sent for fetch: " + studentIdList);
			
			for(Student student : fetchStudentList) {
				System.out.println("selectUsingJPANamedQueryUsage: " + student);
			}
			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
		session.close();
	}
	
	// this API demonstrate use of native query SQL for bulk update - refer to the 2nd argument not used for createNativeQuery
	// also the update returns updated count. same example can be used for delete.
	private void updatesingJPANativeQueryUsage(List<Integer> studentIdList) {
		Session session = factory.getCurrentSession();
		
		System.out.println(studentIdList);
		try {
			session.beginTransaction();
			// notice - 2nd argument is not populated as this is UPDATe or applicable also to DELETE
			Query updateStudetListQuery = session.createNativeQuery("UPDATE hb_student_tracker.student SET email='test'  WHERE id in (:studentList)");
			updateStudetListQuery.setParameter("studentList", studentIdList);
			int updateCount = updateStudetListQuery.executeUpdate();
			
			System.out.println("updatesingJPANativeQueryUsage: update applicable count: " + updateCount);

			session.getTransaction().commit();
		} catch (Exception exp) {
			System.out.println("\n exception handling");		
			exp.printStackTrace();
		}
		session.close();
	}
	
	// Reference: https://thorben-janssen.com/criteria-updatedelete-easy-way-to/
	// use of bulk update through JPA using DELETE and UPDATE critera - this directly runs on database 
	// hence the persistence context is not synchronized
	
	
}
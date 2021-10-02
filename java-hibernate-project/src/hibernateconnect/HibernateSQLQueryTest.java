package hibernateconnect;


import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

// Reference: https://vladmihalcea.com/a-beginners-guide-to-jpa-hibernate-entity-state-transitions/
// this documentation shows the different state of the entity object

public class HibernateSQLQueryTest {
	
	Session globalSession = null;
	SessionFactory globalSessionFactory = null;
	
	private int studentPKId = 49;
	
	/** 
	 * Reference: https://vladmihalcea.com/a-beginners-guide-to-jpa-hibernate-entity-state-transitions/
	 * this documentation shows the different state of the entity object
	 * new - state of entity not managed by persistence context (hibernate or JPA)
		- any changes to the entity will not be reflected until moved to managed state
		Student student = new Student(2, "rahul", "hegde", "hegde.rahul@gmail.com");
		
		- use persist (JPA) or save (Hibernate) API to move from new to managed state .
		- persist (with IDENTITY) 
			- must have identity column to be 0 (such that not set).
			- no return value
		- save (with IDENTITY set) 
			- it is optional to set identity col to zero and ensures object creation.
			- returns identity col in serialized form
		- update + delete - calls are delayed (optimization) using transaction-write behind.
		- flush - forces sync between hibernate (first level) cache and database
			
		state is converted to managed.  
	 */
	int demoHibernateSQL_CreateStudent() {

		SessionFactory sessionFactory = null;
		Session session = null;
		int id = -1;
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			sessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.buildSessionFactory();

			session = sessionFactory.getCurrentSession();
			
			// change the flush mode if required as part of test
			//session.setHibernateFlushMode(FlushMode.COMMIT);

			// object in new or transient state such that not part of the persistent content
			// persistent context provides session factory object
			//Student student = new Student(2, "rahul", "hegde", "hegde.rahul@gmail.com");
			Student student = new Student("rahul1", "hegde", "hegde.rahul@gmail.com");
	
			//session.beginTransaction();
			
			// this will transition new to managed state for the student object 
			// fire up query to database inorder to get Identity Object (not wait)
			// until transaction-write-behind
			session.persist(student);
			//id = (Integer) session.save(student);
			System.out.println("save session return: " +  id);
				
			// further state changes to the managed object, gets reflected
			// these are not immediate however delayed until commit or next flush
			student.setEmail("changed@gmail.com");
			student.setFirstName("changed");
			System.out.println("state changes: " + student.toString());
			System.out.println("session flush mode type: " + session.getFlushMode());

			// if flush is commented - see optimization by hibernate - 
			// 		SQLs are insert -> update -> delete
			// else 
			//		SQLs are insert -> delete (update is skipped)
			// depends on application behavior required - o
			// if flush is commented - here are logs of SQL
			// Hibernate: insert into student (email, first_name, last_name) values (?, ?, ?)
			// state changes -  sleep 3 secsStudent [id=28, firstName=changed, lastName=hegde, email=changed@gmail.com]
			// Hibernate: update student set email=?, first_name=?, last_name=? where id=?
			// Hibernate: delete from student where id=?
		
			// session.flush();
			
			// introduce sleep to view hibernate generated SQL statements.
			System.out.println("sleeping for 3s");
			Thread.sleep(3000);
						
			// remove the entity like DELETE * FROM TABLE with id = ?
			// session.delete(student);
			
			// commit the transaction to the database
			// though there are two updates, hibernate translates to a single SQL update 
			// this is achieved through transaction-write-behind
			//session.getTransaction().commit();

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			session.close();
			sessionFactory.close();
			sessionFactory = null;
		}
		return id;
	}
	
	
	void demoHibernateSQL_GetStudentByPrimaryKey(int id ) {

		SessionFactory sessionFactory = null;
		Session session = null;
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			sessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.buildSessionFactory();
			

			session = sessionFactory.getCurrentSession();
			
			session.beginTransaction();
			// get student object using primary key of Student Entity
			Student student = session.get(Student.class, id);
			System.out.println("Student - " + student.toString());

			session.getTransaction().commit();
			
			System.out.println("Student - " + student.toString());

			
		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			session.close();
			sessionFactory.close();
			sessionFactory = null;
		}
	}
	
	void demoHibernateSQL_GetStudentList( ) {

		SessionFactory sessionFactory = null;
		Session session = null;
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			sessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.buildSessionFactory();

			session = sessionFactory.getCurrentSession();
			
			session.beginTransaction();
			Query <Student> query = session.createQuery("from Student s where "
					+ "(s.firstName=:StudentId OR s.firstName is null)", 
					Student.class);
			query.setParameter("StudentId", "rahul");
			List<Student> student = query.getResultList();
			System.out.println("Student - " + student.toString());

			session.getTransaction().commit();
			
		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			session.close();
			sessionFactory.close();
			sessionFactory = null;
		}
	}
	
	void demoHibernateSQL_SelectnUpdateStudentRecord(int id) {

		SessionFactory sessionFactory = null;
		Session session = null;
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			sessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.buildSessionFactory();
			session = sessionFactory.getCurrentSession();

			session.beginTransaction();

			// get student object using primary key of Student Entity
			Student student = session.get(Student.class, id);
			
			System.out.println("student (before): " + student.getEmail());
			
			student.setEmail("newemail.new@gmail.com");
			
			session.getTransaction().commit();

			// check for change
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			student = session.get(Student.class, studentPKId);
			System.out.println("student (after): " + student.getEmail());
			session.getTransaction().commit();
			session.close();
			
		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			sessionFactory.close();
			sessionFactory = null;
		}
	}
	
	void demoHibernateSQL_DirectSelectUpdateDeleteSupport(int id) {

		SessionFactory sessionFactory = null;
		Session session = null;
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			sessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.buildSessionFactory();
			session = sessionFactory.getCurrentSession();

			session.beginTransaction();

			// get student object using primary key of Student Entity
			int result = session
			.createQuery("update Student SET  email='direct.update@email.com' where id = " + id)
			.executeUpdate();
			
			System.out.println("result of update " + result);
			
			session.getTransaction().commit();
			
			// check for change
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Student student = session.get(Student.class, studentPKId);
			System.out.println("student (after): " + student.getEmail());
			session.getTransaction().commit();
			session.close();
			
		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			sessionFactory.close();
			sessionFactory = null;
		}
	}
	
	
	void demoHibernateSQL_SelectDeleteStudent(int id) {

		SessionFactory sessionFactory = null;
		Session session = null;
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			sessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.buildSessionFactory();
			session = sessionFactory.getCurrentSession();

			session.beginTransaction();

			// get student object using primary key of Student Entity
			Student student = session.get(Student.class, id);
			session.remove(student);
			session.getTransaction().commit();

			// check for change
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Optional<Student> optStudent = Optional.ofNullable(session.get(Student.class, id));
			System.out.println("student (after): " + optStudent.isEmpty());
			session.getTransaction().commit();
			session.close();

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			sessionFactory.close();
			sessionFactory = null;
		}
	}
	
	
	void demoHibernateSQL_FirstCacheLevelTest() {

		SessionFactory sessionFactory = null;
		Session session = null;
		try {	
			// create hibernate session factory
			// save is hibernate specific API whereas persist is JPA adhered
			sessionFactory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Student.class)
					.buildSessionFactory();
			session = sessionFactory.getCurrentSession();

			session.beginTransaction();

			// get student object using primary key of Student Entity
			Query <Student> query = session.createQuery("from Student s where s.id=49", Student.class);
			Student student3 = query.getSingleResult();
			System.out.println("student - "+  student3.toString());
			
			
			Thread.sleep(10000);
			Student student1 = session.get(Student.class, 49);
			Student student2 = session.get(Student.class, 49);
			
			// get student object using primary key of Student Entity
			Query <Student> query4 = session.createQuery("from Student s where s.id in (49)", Student.class);
			Student student4 = query4.getSingleResult();
			
			Query <Student> query5 = session.createQuery("from Student s where s.email = 'Hello1'", Student.class);
			Student student5 = query5.getSingleResult();
			
			System.out.println("isEqual 1 v/s 2 - " + student1.equals(student2));
			System.out.println("isEqual 1 v/s 3 - " + student1.equals(student3));
			System.out.println("isEqual 1 v/s 4 - " + student1.equals(student4));
			System.out.println("isEqual 1 v/s 5 - " + student1.equals(student5));
			
			session.getTransaction().commit();
			session.close();

		} catch (Exception ex){ 
			ex.printStackTrace();			
		} finally {		
			// cleanup
			sessionFactory.close();
			sessionFactory = null;
		}
	}
	

	public static void main(String[] args) {
		HibernateSQLQueryTest test = new HibernateSQLQueryTest();

//		// Create
		int id = test.demoHibernateSQL_CreateStudent();
//		
//		// Read
//		test.demoHibernateSQL_GetStudentByPrimaryKey(id);
//
//		// update
//		test.demoHibernateSQL_SelectnUpdateStudentRecord(id);
//		test.demoHibernateSQL_DirectSelectUpdateDeleteSupport(id);
//		
//		// delete
//		test.demoHibernateSQL_SelectDeleteStudent(id);
	
		//test.demoHibernateSQL_FirstCacheLevelTest();
	}
	
	
}

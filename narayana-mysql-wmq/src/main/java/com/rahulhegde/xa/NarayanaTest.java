package com.rahulhegde.xa;

import java.sql.SQLException;
import java.sql.Statement;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rahulhegde.xa.config.DatabaseConfig;
import com.rahulhegde.xa.model.Student;


@Component
public class NarayanaTest {
	@Autowired
	DataSource ds;

	@Autowired
	ConnectionFactory jmsSource;
	
	@Autowired
	EntityManagerFactory emf;

	@Autowired
	EntityManager em;
	
	@Autowired
	DatabaseConfig dbconfig;

	// JDBC call under Narayana Global Transaction
	public void TestJDBC() {
		UserTransaction utx = dbconfig.userTransaction();
		
		try {
			utx.begin();
			java.sql.Connection mysqlConnection = ds.getConnection();
			Statement s1 = mysqlConnection.createStatement();
			String q1 = "insert into student (email, first_name, last_name) values ('xa1@gmail.com', 'xafn1', 'xaln1')";
			System.out.println("insert state: " + s1.executeUpdate(q1));
			s1.close();
			mysqlConnection.close();
			utx.commit();
		} catch (NotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// JDBC and JMS Put call under Narayana Global Transaction
	public void TestJDBCnJMS() {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

		try {
			UserTransaction utx = dbconfig.userTransaction();
			utx.begin();

			// mysql operations
			java.sql.Connection mysqlConnection = ds.getConnection();
			//mysqlConnection.setAutoCommit(false);
			Statement s1 = mysqlConnection.createStatement();
			String q1 = "insert into student (email, first_name, last_name) values ('xa2@gmail.com', 'xafn2', 'xaln2')";
			System.out.println("insert state: " + s1.executeUpdate(q1));
			s1.close();			
			mysqlConnection.close();

			// mq operations
			javax.jms.Connection cf = jmsSource.createConnection();
			Session s = cf.createSession(true, 0);
			Destination destination = s.createQueue("queue:///" + QUEUE_NAME);

			long uniqueNumber = System.currentTimeMillis() % 1000;
			TextMessage message = s.createTextMessage("Your lucky number today is " + uniqueNumber);

			MessageProducer producer = s.createProducer(destination);
			producer.send(message);

			s.close();
			cf.close();

			//utx.commit();
		} catch (NotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (RollbackException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (HeuristicMixedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (HeuristicRollbackException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} 
	}

	// Mulitple JDBC connection, per connection insert and a single JMS Put call under Narayana Global Transaction
	// Uses Transaction annotation.
	
	// Only Runtime Exception are caught, Exception.class is every exception class for rollback.
	// NarayanaJMSException is thrown is the queue manager was not running
	@Transactional(rollbackOn = {Exception.class})
	public void TestJDBCnJMSUsingAnnotation() throws Exception {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

		//	UserTransactionImp utx = new UserTransactionImp();
		//	utx.begin();

		// mysql operations
		java.sql.Connection mysqlConnection = ds.getConnection();
		//mysqlConnection.setAutoCommit(false);
		Statement s1 = mysqlConnection.createStatement();
		String q1 = "insert into student (email, first_name, last_name) values ('xa3@gmail.com', 'xafn3', 'xaln3')";
		System.out.println("insert state: " + s1.executeUpdate(q1));

		
		java.sql.Connection mysqlConnection2 = dbconfig.dataSource().getConnection();
		Statement s2 = mysqlConnection2.createStatement();
		String q2 = "insert into student (email, first_name, last_name) values ('xa32@gmail.com', 'xafn32', 'xaln32')";
		System.out.println("insert state: " + s2.executeUpdate(q2));
		s2.close();
		mysqlConnection2.close();
		
		
		
		
		// If below line is uncommented - Cannot call method 'commit' while a global transaction is running
		// DBCP2  Exception in thread "main" java.sql.SQLException: Commit can not be set while enrolled in a transaction

		//mysqlConnection.commit();
		s1.close();			
		mysqlConnection.close();
		
		
		
//		java.sql.Connection mysqlConnection2 = dataSource.getConnection();
//		Statement s2 = mysqlConnection2.createStatement();
//		String q2 = "insert into student (email, first_name, last_name) values ('xa3@gmail.com', 'xafn3', 'xaln3')";
//		System.out.println("insert state: " + s2.executeUpdate(q2));
//
//		// If below line is uncommented - Cannot call method 'commit' while a global transaction is running
//		// mysqlConnection.commit();
//		s2.close();			
//		mysqlConnection2.close();


		// mq operations
		javax.jms.Connection cf = jmsSource.createConnection();
		Session s = cf.createSession(true, 0);
		Destination destination = s.createQueue("queue:///" + QUEUE_NAME);

		long uniqueNumber = System.currentTimeMillis() % 1000;
		TextMessage message = s.createTextMessage("Your lucky number today is " + uniqueNumber);

		MessageProducer producer = s.createProducer(destination);
		producer.send(message);

		s.close();
		cf.close();

		// should prevent from adding data to resource managers
		//System.exit(1);
		//throw new JMSException("!");

		//utx.commit();
	}

	
	// Mulitple Hibernate entity manager, single insert per session and a single JMS Put call under Narayana Global Transaction
	// Uses Transaction annotation.
	// Only Runtime Exception are caught, Exception.class is every exception class for rollback.
	// NarayanaJMSException is thrown is the queue manager was not running
	
	@Transactional(rollbackOn = {JMSException.class})
	public void TestHibernatenJMSUsingAnnotation() throws Exception {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

//		UserTransactionImp utx = new UserTransactionImp();
//		utx.begin();

		// hibernate
		EntityManager em = emf.createEntityManager();
		Student p = new Student();
		p.setEmail("xa41@gmail.com");
		p.setFirstName("xafn41");
		p.setLastName("xaln41");
		em.persist(p);
		
		EntityManager em2 = emf.createEntityManager();
		Student p2 = new Student();
		p2.setEmail("xa42@gmail.com");
		p2.setFirstName("xafn42");
		p2.setLastName("xaln42");
		em2.persist(p2);
		em2.close();
		
		
		Student p3 = new Student();
		p3.setEmail("xa42@gmail.com");
		p3.setFirstName("xafn43");
		p3.setLastName("xaln43");
		em.persist(p3);
		em.close();
		
		//System.exit(1);

		// mq operations
		javax.jms.Connection cf = jmsSource.createConnection();
		Session s = cf.createSession(true, 0);
		Destination destination = s.createQueue("queue:///" + QUEUE_NAME);

		long uniqueNumber = System.currentTimeMillis() % 1000;
		TextMessage message = s.createTextMessage("Your lucky number today is " + uniqueNumber);

		MessageProducer producer = s.createProducer(destination);
		producer.send(message);

		s.close();
		cf.close();
		
		throw new JMSException("1");

//		utx.commit();
	}	

}

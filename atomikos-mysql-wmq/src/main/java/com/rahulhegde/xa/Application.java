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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jms.AtomikosJMSException;
import com.rahulhegde.xa.model.Student;

@Component
public class Application {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ConnectionFactory jmsSource;

	@Autowired
	EntityManagerFactory entityManagerFactory;

	public void TestAtomikosJDBC() {
		UserTransactionImp utx = new UserTransactionImp();

		try {
			utx.begin();
			java.sql.Connection mysqlConnection = dataSource.getConnection();
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

	public void TestAtomikosJDBCnJMS() {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

		try {
			UserTransactionImp utx = new UserTransactionImp();
			utx.begin();

			// mysql operations
			java.sql.Connection mysqlConnection = dataSource.getConnection();
			Statement s1 = mysqlConnection.createStatement();
			String q1 = "insert into student (email, first_name, last_name) values ('xa2@gmail.com', 'xafn2', 'xaln2')";
			System.out.println("insert state: " + s1.executeUpdate(q1));
			//mysqlConnection.commit();
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
		}catch (JMSException e) {
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

	// Only Runtime Exception are caught, Exception.class is every exception class for rollback.
	// AtomikosJMSException is thrown is the queue manager was not running
	@Transactional(rollbackFor= {AtomikosJMSException.class})
	public void TestAtomikosJDBCnJMSUsingAnnotation() throws Exception {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from


		//	UserTransactionImp utx = new UserTransactionImp();
		//	utx.begin();

		// mysql operations
		java.sql.Connection mysqlConnection = dataSource.getConnection();
		Statement s1 = mysqlConnection.createStatement();
		String q1 = "insert into student (email, first_name, last_name) values ('xa3@gmail.com', 'xafn3', 'xaln3')";
		System.out.println("insert state: " + s1.executeUpdate(q1));

		// If below line is uncommented - Cannot call method 'commit' while a global transaction is running
		// mysqlConnection.commit();
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

		// utx.commit();
	}
	
	
	// Only Runtime Exception are caught, Exception.class is every exception class for rollback.
	// AtomikosJMSException is thrown is the queue manager was not running
	//@Transactional(rollbackFor= {AtomikosJMSException.class})
	public void TestAtomikosHibernatenJMSUsingAnnotation() throws Exception {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

			UserTransactionImp utx = new UserTransactionImp();
			utx.begin();

		// mysql operations
//		java.sql.Connection mysqlConnection = dataSource.getConnection();
//		Statement s1 = mysqlConnection.createStatement();
//		String q1 = "insert into student (email, first_name, last_name) values ('xa3@gmail.com', 'xafn3', 'xaln3')";
//		System.out.println("insert state: " + s1.executeUpdate(q1));
//		s1.close();			
//		mysqlConnection.close();
		
		EntityManager em = entityManagerFactory.createEntityManager();
		Student p = new Student();
		p.setEmail("xa4@gmail.com");
		p.setFirstName("xafn4");
		p.setLastName("xaln4");
		em.persist(p);
		em.getTransaction().commit();
		em.close();
		
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

		utx.commit();
	}	
}

package com.rahulhegde.xa;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
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
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rahulhegde.xa.config.TransactionManagerConfig;
import com.rahulhegde.xa.model.Student;


@Component
public class NarayanaTest {
    org.slf4j.Logger logger = LoggerFactory.getLogger(NarayanaTest.class);
    
	@Autowired
	DataSource dbSource;

	@Autowired
	ConnectionFactory jmsSource;
	
	@Autowired
	EntityManagerFactory emf;

	@Autowired
	EntityManager em;
	
	@Autowired
	TransactionManagerConfig tmConfig;
	
	private AtomicInteger counter = new AtomicInteger(0);

	// JDBC call under Narayana Global Transaction
	public void TestJDBC(int threadwait) throws IllegalStateException, SecurityException, SystemException {
		//UserTransaction utx = new UserTransactionImple();
		TransactionManager utx = tmConfig.getTM();
		
		try {
			// release the transaction manager's transaction for that thread in case
			// the transaction was reaped due to timeout (or not completed in the last transaction scope).
			// This can be fixed alternatively by handling the RollbackException exception to call Rollback which 
			// gets triggered during the commit call and.
			if (utx.getStatus() != Status.STATUS_NO_TRANSACTION) {
				logger.info("b-rollback: " + utx.getStatus() + " ,tid: " + Thread.currentThread().getId() + ",utx: " + utx);
				utx.rollback();
				logger.info("a-rollback: " + utx.getStatus() + ",tid: " + Thread.currentThread().getId() + " ,utx: " + utx);
			}
			
			
			utx.begin();
			logger.info("getting connection for datasource: thread id: " + Thread.currentThread().getId());
			java.sql.Connection mysqlConnection = dbSource.getConnection();
			logger.info("got connection for datasource: thread id: " + Thread.currentThread().getId());

			Statement s1 = mysqlConnection.createStatement();
			String q1 = "insert into student (email, first_name, last_name) values ('xa1@gmail.com', 'xafn1', 'xaln1')";
			logger.info("insert state: " + s1.executeUpdate(q1) + ", utx: " + utx.getTransaction().toString());
			
			try {
				logger.info("thread ID: " + Thread.currentThread().getId() + ", sleeping for sec" + ", tran id: " + utx);
				Thread.currentThread().sleep(threadwait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			s1.close();
			mysqlConnection.close();

			// enable alternate behavior
			int lvalue = counter.getAndIncrement();
			logger.info("thread ID: " + Thread.currentThread().getId() + ", throwing exception for counter:" + lvalue);
			if ( true || lvalue % 2 == 0) {
				logger.info("post timeout: " + utx.getStatus() + ",tid: " + Thread.currentThread().getId() + " ,utx: " + utx);
				//throw new NumberFormatException("1");
			}
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
		} catch (NumberFormatException e) {
			utx.rollback();
		}
	}

	// JDBC and JMS Put call under Narayana Global Transaction
	public void TestJDBCnJMS(long threadwait) {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

		try {
//			UserTransaction utx = tmConfig.getUt();
//			utx.begin();
			
			TransactionManager txm = tmConfig.getTM();
			txm.begin();

			// mysql operations
			java.sql.Connection mysqlConnection = dbSource.getConnection();
			//mysqlConnection.setAutoCommit(false);
			Statement s1 = mysqlConnection.createStatement();
			String q1 = "insert into student (email, first_name, last_name) values ('xa2@gmail.com', 'xafn2', 'xaln2')";
			logger.info("insert state: " + s1.executeUpdate(q1));
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

			try {
				logger.info("thread ID: " + Thread.currentThread().getId() + ", sleeping for sec" + ", tran id: " + txm);
				Thread.currentThread().sleep(threadwait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			// enable alternate behavior
			int lvalue = counter.getAndIncrement();
			
			if (false && lvalue % 2 == 0) {
				logger.info("post timeout: " + txm.getStatus() + ",tid: " + Thread.currentThread().getId() + " ,txm: " + txm);
				logger.info("thread ID: " + Thread.currentThread().getId() + ", rollback initiated for counter:" + lvalue);
				txm.rollback();
			} else {
				txm.commit();
			}			

			s.close();
			cf.close();

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
	

	// JDBC and JMS Put call under Narayana Global Transaction
	//@Transactional(rollbackFor = {NumberFormatException.class})
	@Transactional(rollbackFor = {NumberFormatException.class, JMSException.class})
	public void Test2JDBCnJMS() {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from
		final String HOST = "localhost"; // Host name or IP address
		final int PORT = 1414; // Listener port for your queue manager
		final String CHANNEL = "DEV.APP.SVRCONN"; // Channel name
		final String QMGR = "QM1"; // Queue manager name
		final String APP_USER = "app"; // User name that application uses to connect to MQ
		final String APP_PASSWORD = "passw0rd"; // Password that the application uses to connect to MQ
		
		try {
//			UserTransaction utx = dbconfig.getUt();
//			utx.begin();

//			TransactionManager tm = dbconfig.getTm();
//			tm.begin();
			
			logger.info("data source: " + dbSource);
			// mysql operations
			java.sql.Connection mysqlConnection = dbSource.getConnection();
			//mysqlConnection.setAutoCommit(false);
			Statement s1 = mysqlConnection.createStatement();
			String q1 = "insert into student (email, first_name, last_name) values ('xa2@gmail.com', 'xafn2', 'xaln2')";
			logger.info("insert state: " + s1.executeUpdate(q1));
			s1.close();			
			mysqlConnection.close();
			
			// mq operations
			Connection conn = jmsSource.createConnection();
			Session s = conn.createSession(true, 0);
			Destination destination = s.createQueue("queue:///" + QUEUE_NAME);
			long uniqueNumber = System.currentTimeMillis() % 1000;
			TextMessage message = s.createTextMessage("Your lucky number today is " + uniqueNumber);
			MessageProducer producer = s.createProducer(destination);
			producer.send(message);


			
			// enable alternate behavior
			int lvalue = counter.getAndIncrement();
			logger.info("--thread ID: " + Thread.currentThread().getId() + ", rollback initiated for counter:" + lvalue);
			if (true && lvalue % 2 == 0) {
				//logger.info("post timeout: " + txm.getStatus() + ",tid: " + Thread.currentThread().getId() + " ,txm: " + txm);
				throw new NumberFormatException();
			}
			
			//s.commit();
			s.close();
			conn.close();
			

			//throw new NumberFormatException();
			
//			tm.commit();
			//utx.commit();
//		} catch (NotSupportedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (SystemException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
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
	@Transactional(rollbackFor = {Exception.class})
	public void TestJDBCnJMSUsingAnnotation() throws Exception {
		final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from

		//	UserTransactionImp utx = dbConfig.getUt();
		//	utx.begin();

		// mysql operations
		java.sql.Connection mysqlConnection = dbSource.getConnection();
		//mysqlConnection.setAutoCommit(false);
		Statement s1 = mysqlConnection.createStatement();
		String q1 = "insert into student (email, first_name, last_name) values ('xa3@gmail.com', 'xafn3', 'xaln3')";
		logger.info("insert state: " + s1.executeUpdate(q1));

		
		java.sql.Connection mysqlConnection2 = tmConfig.dataSource().getConnection();
		Statement s2 = mysqlConnection2.createStatement();
		String q2 = "insert into student (email, first_name, last_name) values ('xa32@gmail.com', 'xafn32', 'xaln32')";
		logger.info("insert state: " + s2.executeUpdate(q2));
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
//		logger.info("insert state: " + s2.executeUpdate(q2));
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
	
	@Transactional(rollbackFor = {NumberFormatException.class, JMSException.class})
	@javax.transaction.Transactional(rollbackOn = {NumberFormatException.class, JMSException.class})
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
		
//		// enable alternate behavior
//		int lvalue = counter.getAndIncrement();
//		logger.info("--thread ID: " + Thread.currentThread().getId() + ", rollback initiated for counter:" + lvalue);
//		if (true && lvalue % 2 == 0) {
//			//logger.info("post timeout: " + txm.getStatus() + ",tid: " + Thread.currentThread().getId() + " ,txm: " + txm);
//			throw new NumberFormatException();
//		}
		
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
		
		
		
		// enable alternate behavior
		int lvalue = counter.getAndIncrement();
		if (true && lvalue % 2 == 0) {
			//logger.info("post timeout: " + txm.getStatus() + ",tid: " + Thread.currentThread().getId() + " ,txm: " + txm);
			logger.info("--thread ID: " + Thread.currentThread().getId() + ", rollback initiated for counter:" + lvalue);
			throw new NumberFormatException();
		}		
		
		//s.commit();
		s.close();
		cf.close();

		//System.exit(1);

		//throw new NumberFormatException("1");

//		utx.commit();
	}
	
	public void MultithreadOps_TestJDBC() {
		/**
		 * Database Connection Pooling
		 * 
		 * - Thread pool: 5, Thread Execution Time: 1s, Max DB Connection: 2, Narayana Transaction Timeout: 10s 
		 * 	+ Since there are more threads, there is wait at the time of getting the database connection from DBCP2 during API 
		 * call - dbSource.getConnection())
		 * 	+ wireshark trace shows only two DB connections active
		 * 
		 * - Thread pool: 1, Thread Execution Time: 1s, Max DB Connection: 2, Narayana Transaction Timeout: 10s 	 
		 *
		 *
		 * - Thread pool: 5, Thread Execution Time: 5s, Max DB Connection Pool: 1, Narayana Transaction Timeout: 10s, DBCP2 Max Wait Time: 1s
		 * 	Timeout of DBCP2 as there is lack of connection from connection pool as they are occupied on other task
		 * 
			java.sql.SQLException: Unable to acquire a new connection from the pool
			at org.apache.commons.dbcp2.managed.ManagedConnection.updateTransactionStatus(ManagedConnection.java:305)
			at org.apache.commons.dbcp2.managed.ManagedConnection.<init>(ManagedConnection.java:89)
			at org.apache.commons.dbcp2.managed.ManagedDataSource.getConnection(ManagedDataSource.java:64)	
			...
			caused by: java.util.NoSuchElementException: Timeout waiting for idle object, borrowMaxWaitDuration=PT1S
			at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:312)	 * 	 
		 */
		ExecutorService es = Executors.newFixedThreadPool(5);
		Runnable t1 = new Runnable() {
			@Override
			public void run() {
				try {
					// default: 1000
					TestJDBC(2000);
				} catch (IllegalStateException | SecurityException | SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		for (int i =0; i < 10; i++) {
			es.execute(t1);
		}
		
		es.shutdown();
		try {
		    if (!es.awaitTermination(60, TimeUnit.SECONDS)) {
		    	es.shutdownNow();
		    } 
		} catch (InterruptedException e) {
			es.shutdownNow();
		}		
		logger.info("shuting down the executor service for MultithreadOps_TestJDBC");		
	}
	
	public void MultithreadOps_TestJDBCnJMS() {
		/**
		 * JMS Session Caching
		 * 
		 * Setting JMS Session Cache: 1, Thread Pool: 2 - Result in using 1 new connection whereas the other connection is 
		 * used from JMS session cache
		 * 
		 */
		ExecutorService es = Executors.newFixedThreadPool(3);
		Runnable t1 = new Runnable() {
			
			@Override
			public void run() {
				try {
					TestJDBCnJMS(1000);
				} catch (IllegalStateException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		for (int i = 0; i < 6; i++) {
			es.execute(t1);
		}
		
		es.shutdown();
		try {
		    if (!es.awaitTermination(60, TimeUnit.SECONDS)) {
		    	es.shutdownNow();
		    } 
		} catch (InterruptedException e) {
			es.shutdownNow();
		}	
		
		logger.info("shuting down the executor service for MultithreadOps_TestJDBCnJMS");		
	}

}

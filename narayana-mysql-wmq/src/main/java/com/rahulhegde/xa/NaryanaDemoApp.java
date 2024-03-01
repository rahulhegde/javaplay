package com.rahulhegde.xa;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.arjuna.ats.arjuna.common.arjPropertyManager;

/**
 * todo
 * - rollback test - passed
 * - spring properties usage - not possible as support removed from >= spring boot 2.x
 * - in-between transaction failure and recovered using transaction log - https://jbossts.blogspot.com/2018/01/narayana-periodic-recovery-of-xa.html
 * - database connection pool 
 * - jms connection pool -
 * - quartz under narayana JTA - http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-11.html
 * 
 *
 */

/**
 * Learning 
 * 
 * 1. Scope of the transaction (object) is linked to a thread. This
 * linkage happens when .begin is called on Transaction Manager or User
 * Transaction. - Creating multiple Transaction Manager to a single thread, does
 * not help create multiple transactions scope per thread. Internally Narayana
 * TM maintains a concurrent hash of transaction-thread mapping. So having a
 * single TM should suffice per application/process instance.
 * 
 * 2. any timeout on the transaction is managed by a Narayana Reaper process
 * which puts the transaction in rollback state. This means the transaction is
 * not ready for use and a call for begin will fail. This requires a transaction
 * rollback in the exception handling.
 * 
 * 
 * 3. Take care of HeuristicMixedException - Reference: https://developer.jboss.org/thread/129559
 *
 * 4. DB Connection Pool - call connection close to release connection back to pool for reused
 * else the next requester waits until DBCP2 pool timesout. Same should be done for MQ.
 * 
 */

@SpringBootApplication
public class NaryanaDemoApp {
	public static void MultithreadOps_Test2JDBCnJMS(NarayanaTest test, org.slf4j.Logger logger) {
		ExecutorService es = Executors.newFixedThreadPool(5);
		Runnable t1 = new Runnable() {
			@Override
			public void run() {
				try {
					test.Test2JDBCnJMS();
				} catch (IllegalStateException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		for (int i = 0; i < 2; i++) {
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
	
	public static void MultithreadOps_TestHibernatenJMSUsingAnnotation(NarayanaTest test, org.slf4j.Logger logger) {
		/**
		 * spring transactional annotation - a spring proxy supporting transaction only
		 * gets wrapped, if the calling class (DemoApp) is different from the method
		 * having * @Transactinal annotation (NarayanaTest::Test2JDBCnJMS). If they are called
		 * from same class, @Transactional does not work. 
		 * 
		 * Reference: https://stackoverflow.com/questions/27182157/spring-transactional-method-not-work-on-separated-thread
		 */
		ExecutorService es = Executors.newFixedThreadPool(5);
		Runnable t1 = new Runnable() {
			@Override
			public void run() {
				try {
					test.TestHibernatenJMSUsingAnnotation();
				} catch (IllegalStateException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		for (int i = 0; i < 100; i++) {
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
		logger.info("shuting down the executor service for MultithreadOps_TestHibernatenJMSUsingAnnotation");
	}
	
	public static void MultithreadOps_Mix(NarayanaTest test, org.slf4j.Logger logger) {
		/**
		 * spring transactional annotation - a spring proxy supporting transaction only
		 * gets wrapped, if the calling class (DemoApp) is different from the method
		 * having * @Transactinal annotation (NarayanaTest::Test2JDBCnJMS). If they are called
		 * from same class, @Transactional does not work. 
		 * 
		 * Reference: https://stackoverflow.com/questions/27182157/spring-transactional-method-not-work-on-separated-thread
		 */
		ExecutorService es = Executors.newFixedThreadPool(5);
		Runnable t1 = new Runnable() {
			@Override
			public void run() {
				try {
					test.TestJDBC(1);
				} catch (IllegalStateException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Runnable t2 = new Runnable() {
			@Override
			public void run() {
				try {
					test.Test2JDBCnJMS();
				} catch (IllegalStateException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Runnable t3 = new Runnable() {
			@Override
			public void run() {
				try {
					test.TestHibernatenJMSUsingAnnotation();
				} catch (IllegalStateException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		


		for (int i = 0; i < 10; i++) {
			//es.execute(t1);
			//es.execute(t2);
			es.execute(t3);
		}

		es.shutdown();
		try {
			if (!es.awaitTermination(60, TimeUnit.SECONDS)) {
				es.shutdownNow();
			}
		} catch (InterruptedException e) {
			es.shutdownNow();
		}
		logger.info("shuting down the executor service for MultithreadOps_Mix");
	}
	

    public static void main(String[] args) throws Exception {
        org.slf4j.Logger logger = LoggerFactory.getLogger(NaryanaDemoApp.class);

		ConfigurableApplicationContext ctx = SpringApplication.run(NaryanaDemoApp.class, args);
		NarayanaTest app = ctx.getBean(NarayanaTest.class);
		
		// different test cases
//		app.TestJDBC();
//		app.TestJDBCnJMS();
// 		app.Test2JDBCnJMS();
//		app.TestJDBCnJMSUsingAnnotation();
//		app.TestHibernatenJMSUsingAnnotation();


		// pre-requisite setup for the test
		// mysql database - student entity defined
		// ibm mq setup 
		// 	docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 
		// 		--volume /home/developer/workspace/mq-ws/qm1data:/mnt/mqm --publish 1414:1414
		// 		--publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest

		// minimum setting for narayana JTS
		arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier("1");
		arjPropertyManager.getObjectStoreEnvironmentBean()
				.setObjectStoreDir("/home/developer/workspace/java-ws/arjuna-xa/narayana-mysql-wmq/userdefobjstore/");
		arjPropertyManager.getCoordinatorEnvironmentBean().setDefaultTimeout(15);
		arjPropertyManager.getCoordinatorEnvironmentBean().setEnableStatistics(true);
		
		//app.MultithreadOps_TestJDBC();
		app.MultithreadOps_TestJDBCnJMS();
	
		/**
		 * spring @Transactional annotation -
		 * source_class::method calling target_class::method whereas target_class:method has @Transactional
		 * annotation will result in spring creating or wrapping the target_class via a spring proxy class.
		 * spring proxy is responsible for managing the transactional boundaries - begin/commit/rollback.
		 * 
		 * If the source_class::method1 calls the source_class::method2 where source_class:method2 has @Transactional
		 * then spring does not recognize or create spring proxy wrapper and as such Transactional annotation 
		 * does not work.
		 * 
		 * Reference: https://stackoverflow.com/questions/27182157/spring-transactional-method-not-work-on-separated-thread
		 */
		//NaryanaDemoApp.MultithreadOps_Test2JDBCnJMS(app, logger);
		//NaryanaDemoApp.MultithreadOps_TestHibernatenJMSUsingAnnotation(app, logger);
		
		/**
		 * Known Error - 
		 * 
		 * javax.transaction.xa.XAException: The method 'xa_rollback' has failed with errorCode '-4'.
				at com.ibm.mq.jmqi.JmqiXAResource.rollback(JmqiXAResource.java:881) ~[com.ibm.mq.allclient-9.2.0.1.jar:9.2.0.1 - p920-001-200918]
				at com.arjuna.ats.internal.jta.resources.arjunacore.XAResourceRecord.topLevelAbort(XAResourceRecord.java:362) ~[jta-5.12.7.Final.jar:5.12.7.Final]
				at com.arjuna.ats.arjuna.coordinator.BasicAction.doAbort(BasicAction.java:3037) ~[arjuna-5.12.7.Final.jar:5.12.7.Final]
				at com.arjuna.ats.arjuna.coordinator.BasicAction.doAbort(BasicAction.java:3016) ~[arjuna-5.12.7.Final.jar:5.12.7.Final]
				at com.arjuna.ats.arjuna.coordinator.BasicAction.Abort(BasicAction.java:1679) ~[arjuna-5.12.7.Final.jar:5.12.7.Final]
			...
			
			java.lang.NumberFormatException
				at com.rahulhegde.xa.NarayanaTest.TestHibernatenJMSUsingAnnotation(NarayanaTest.java:436)
				at com.rahulhegde.xa.NarayanaTest$$FastClassBySpringCGLIB$$8840514e.invoke(<generated>)
				at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
		 * 
		 * This exception is seen during rollback either by @Transactional OR application initiated. 
		 * This is found to benign and not impacting any functionality of rollback and seems to be fixed
		 * if the JMS session is closed before the exception is thrown/rollback. This is also not caused
		 * by spring' JMS CachingConnectionFactory.
		 * 
		 */
		//NaryanaDemoApp.MultithreadOps_Mix(app, logger);
		
		logger.info("demo app test complete - MultithreadOps, no of processors: "
				+ Runtime.getRuntime().availableProcessors());
	}
}
package com.rahulhegde.xa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.arjuna.ats.arjuna.common.arjPropertyManager;

/**
 * - rollback test - passed
 * - spring properties usage - not possible as support removed from >= spring boot 2.x
 * - in-between transaction failure and recovered using transaction log
 * - database connection pool 
 * - jms connection pool 
 * - quartz under narayana JTA - http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-11.html
 */

@SpringBootApplication
public class DemoApp {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApp.class, args);
		NarayanaTest app = ctx.getBean(NarayanaTest.class);

		// pre-requisite setup for the test
		// mysql database - student entity defined
		// ibm mq setup - docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume /home/developer/workspace/mq-ws/qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest

		// minimum setting for 
		arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier("1");
		arjPropertyManager.getObjectStoreEnvironmentBean().setObjectStoreDir("/home/developer/workspace/java-ws/arjuna-xa/narayana-mysql-wmq/userdefobjstore/");

		// different test cases 
		//app.TestJDBC();

		app.TestJDBCnJMS();

		//app.TestJDBCnJMSUsingAnnotation();

		//app.TestHibernatenJMSUsingAnnotation();
	}
}
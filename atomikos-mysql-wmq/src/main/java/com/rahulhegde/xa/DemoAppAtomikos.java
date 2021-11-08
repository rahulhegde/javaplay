package com.rahulhegde.xa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoAppAtomikos {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoAppAtomikos.class, args);
		AtomikosTest app = ctx.getBean(AtomikosTest.class);

		// pre-requisite setup for the test
		// mysql database - student entity defined
		// ibm mq setup - docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume /home/developer/workspace/mq-ws/qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest

		app.TestAtomikosJDBC();

		app.TestAtomikosJDBCnJMS();

		app.TestAtomikosJDBCnJMSUsingAnnotation();

		app.TestAtomikosHibernatenJMSUsingAnnotation();
	}
}
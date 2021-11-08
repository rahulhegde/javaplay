package com.rahulhegde.xa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AtomikosMysqlWmqApplication {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(AtomikosMysqlWmqApplication.class, args);
		Application app = ctx.getBean(Application.class);
		
		app.TestAtomikosJDBC();
		
		app.TestAtomikosJDBCnJMS();
	
		app.TestAtomikosJDBCnJMSUsingAnnotation();
		
		app.TestAtomikosHibernatenJMSUsingAnnotation();
	}
}
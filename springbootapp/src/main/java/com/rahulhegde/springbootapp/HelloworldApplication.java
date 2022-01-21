package com.rahulhegde.springbootapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {
	
	// uses the slf4j - simple logging framework facade. this APIs are abstraction 
	// and as a result the underlying logging framework - log4j2, logback can be changed at runtime
	// without changing the code
	// reference: https://www.baeldung.com/spring-boot-logging
    //private final static Logger logger = LoggerFactory.getLogger(HelloworldApplication.class);

	//	import org.apache.logging.log4j.LogManager;
	//	import org.apache.logging.log4j.Logger;
	private final static Logger logger = LogManager.getLogger(HelloworldApplication.class);

	public static void main(String[] args) {
		
		logger.debug("hello world is running with {} or {}", logger.getClass(), logger.toString());

		SpringApplication.run(HelloworldApplication.class, args);
	}

}

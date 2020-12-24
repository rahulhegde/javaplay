package com.springdemo.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationConfigDemoApp {

	// Demo App shows how to populate the beans using java code. 
	// This uses Configuration class with ComponentScan with package name builds all the 
	// beans on the spring container.
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CustomBeanScanner.class);
		
		Coach tennisCoach1 = context.getBean("SimpleTennisCoach", Coach.class);
		Coach tennisCoach2 = context.getBean("SimpleTennisCoach", Coach.class);

		System.out.println("Tennis Coach" + tennisCoach1 );
		System.out.println("Tennis Coach" + tennisCoach2 );
		tennisCoach1 = null;
		System.out.println("context close start");
		context.close();
		System.out.println("context close end");
	
	}
}

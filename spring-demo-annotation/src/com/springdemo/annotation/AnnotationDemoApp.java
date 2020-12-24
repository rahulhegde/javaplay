package com.springdemo.annotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationDemoApp {

	// Demo App uses spring XML file to perform bean or component scan across package
	// Every Class needs to define annotation like @Component, @Scope alike a bean definition
	// This reduces the needs to write a big XML files with lots of beans.
	
	// Check the usage of PropertySource
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanAnnotationContext.xml");
		
		Coach theCoach1 = context.getBean("SimpleTennisCoach", Coach.class);
		// default annotation component name for CricketCoach
		Coach theCoach2 = context.getBean("cricketCoach", Coach.class);

		System.out.println(theCoach1.getCoachAdvice());
		
		System.out.println(theCoach2.getCoachAdvice());
		System.out.println(theCoach2.getFortuneAdvice());
		context.close();
	}

}

package com.springdemo.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationJavaCodeDemoApp {

	// This Demo App uses Configuration Class to define Bean.
	// So far the Bean can be defined using XML, Component-Scan the Package, Configuration class with ComponentScan the Package
	// Configuration class defines the Bean that will be created using @Bean and also inject dependency.
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnnotationJavaConfig.class);
		System.out.println("context creation complete");
		
		Coach theCoach1 = context.getBean("SwimCoach", Coach.class);
		System.out.println(theCoach1.getCoachAdvice());
		System.out.println(theCoach1.getFortuneAdvice());
		Coach theCoach2 = context.getBean("SwimCoach", Coach.class);
		
		context.close();
	}

}

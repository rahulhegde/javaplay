package com.springdemo.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springdemo.prototypescope.PrototypeTennisCoach;

public class Demo02PrototypeBeanDestroyUsingAnnotation {

	// Demo shows how to call the PreDestroy method of prototype scope bean
	// It uses custom bean scanner to record all beans of type prototype and then call using 
	public static void main(String[] args) {
		// use the 
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanScopePrototypePostDestroyUsingAnnotation.xml");

		System.out.println("spring application context created usies XML component scan component to allow spring to perform bean scan");
		
		// prototype example
		//  - tennisCoach1 and tennisCoach2 post-construct called but not pre-destroy method as they are prototype
		//  - require CustomBeanScanner to explicitly call the destroy method
		PrototypeTennisCoach tennisCoach = context.getBean("prototypeTennisCoach", PrototypeTennisCoach.class);
		
		// close the application context
		System.out.println("spring application context destroying all singleton will be destroyed");
		context.close();
	}
}

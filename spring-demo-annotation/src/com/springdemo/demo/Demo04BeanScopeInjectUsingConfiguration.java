package com.springdemo.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springdemo.coach.Coach;

public class Demo04BeanScopeInjectUsingConfiguration {

	// Demo shows creation of bean by application context using @bean tag in the @configuration annotation.
	//
	// @bean use case is where SwimCoach is like 3rd party class and does not have spring annotation support (@component)
	// this approach using @bean will integrate such class to spring usage pattern
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanScanConfigurationUsesBeanAnnotation.class);

		System.out.println("spring application context created using @Configuration and @Bean example");
		
		Coach theCoach1 = context.getBean("SwimCoach", Coach.class);
		System.out.println(theCoach1.getCoachAdvice());
		System.out.println(theCoach1.getFortuneAdvice());
		Coach theCoach2 = context.getBean("SwimCoach", Coach.class);
		
		System.out.println("spring application context closing");
		context.close();
	}
}

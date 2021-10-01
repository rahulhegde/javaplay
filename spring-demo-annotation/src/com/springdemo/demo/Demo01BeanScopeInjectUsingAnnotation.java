package com.springdemo.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springdemo.coach.Coach;

public class Demo01BeanScopeInjectUsingAnnotation {

	// Demo shows 
	// - Inversion of Control - Spring Application Context - 
	//		- 
	// - Dependency Injection - on the CricketCoach class showing 
	//		- injecting value using property source (XML, class annotation @PropertySource)
	//		- injecting member using @autowired annotation on member variable, constructor and setter method 
	public static void main(String[] args) {
		// use the 
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanScopeDemoUsingAnnotation.xml");

		System.out.println("spring application context created usies XML component scan component to allow spring to perform bean scan");

		// singleton example
		// check the Inversion & Dependency Injection used for Cricket using annotation - no XML
		//	
		// - Dependency Injection - on the CricketCoach class showing 
		//		- injecting value using property source (XML, class annotation @PropertySource)
		//		- injecting member using @autowired annotation on member variable, constructor and setter method 
		Coach cricketCoach1 = context.getBean("cricketCoach", Coach.class);
		Coach cricketCoach2 = context.getBean("cricketCoach", Coach.class);
		boolean checkIfCoachEqual = cricketCoach1.equals(cricketCoach2);
		System.out.println("cricketCoach1 and cricketCoach2 are singletone and isEqual: " + checkIfCoachEqual);
		System.out.println("check the inversion & dependency injection on cricket coach class");
		System.out.println(cricketCoach1.toString());
		
		// prototype example
		//  - tennisCoach1 and tennisCoach2 post-construct called but not pre-destroy method as they are prototype
		//  - require CustomBeanScanner to explicitly call the destroy method
		Coach tennisCoach1 = context.getBean("SimpleTennisCoach", Coach.class);
		Coach tennisCoach2 = context.getBean("SimpleTennisCoach", Coach.class);
		boolean checkIfTennisEqual = tennisCoach1.equals(tennisCoach2);
		System.out.println("tennisCoach1 and tennisCoach2 are prototype and isEqual: " + checkIfTennisEqual);
		
		// close the application context
		System.out.println("spring application context destroying all singleton will be destroyed");
		context.close();
	}
}

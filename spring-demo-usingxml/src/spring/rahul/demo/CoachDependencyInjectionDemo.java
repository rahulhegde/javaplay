package spring.rahul.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.rahul.coach.Coach;

/**
 * Demo shows how object is constructed using either constructor, setter method injecting literal or through property file.
 * 
 */
public class CoachDependencyInjectionDemo {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dependencyInjectionContext.xml");
		System.out.println("spring context container creation complete");

		// info - t 
		Coach myCoach1 = context.getBean("myCricketCoachMethodInjection", Coach.class);
		System.out.println("method injection: " + myCoach1);

		Coach myCoach2 = context.getBean("myCricketCoachConstructorInjection", Coach.class);
		System.out.println("constructor injection: " + myCoach2);
		
		System.out.println("initiate spring context container deletion");
		context.close();
	}
}

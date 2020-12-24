package spring.rahul.coach;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanScopeTestApp {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanScopeContext.xml");
		
		System.out.println("-- Spring application Context Constructed --");

		
		Coach coach1 = context.getBean("myCoachSingleton", Coach.class);
		Coach coach2 = context.getBean("myCoachSingleton", Coach.class);
		boolean status = (coach1 == coach2);
		System.out.println("compare status - myCoachSingleton: " + status);
		System.out.println("coach1 address: " + coach1 + ", HappyFortuneService: " + coach1.getFortuneAddress());
		System.out.println("coach2 address: " + coach2 + ", HappyFortuneService: " + coach2.getFortuneAddress());
		
		System.out.println("-- HappyFortuneService is singleton by default, forced to prototype --");

		coach1 = context.getBean("myCoachPrototype", Coach.class);
		coach2 = context.getBean("myCoachPrototype", Coach.class);
		status = (coach1 == coach2);
		System.out.println("compare status - myCoachPrototype: " + status);
		System.out.println("coach1 address: " + coach1 + ", HappyFortuneService: " + coach1.getFortuneAddress());
		System.out.println("coach2 address: " + coach2 + ", HappyFortuneService: " + coach2.getFortuneAddress());
		
		// spring does not have reference of the instance hence cannot called destroy method during Spring Application 
		// context deletion.
		System.out.println("-- Spring application Context Closed, singleton Destroy is called but not for prototype. -- ");
		context.close();
	}

}

package spring.rahul.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.rahul.coach.Coach;
/**
 * if parent object is singleton and its member is singleton - every new parent instance created using spring context will have same address
 * 
 * if parent object is singleton and its member is prototype - every new parent instance created using spring context will get the same address
 * 
 * if parent object is prototype and its member is singleton - every new parent instance created using spring context will get the new address 
 * but the member will be same object 
 * 
 * if parent object is prototype and its member is prototype - every new parent instance created using spring context will get the new address 
 * and also the member will get a new address.
 *
 */
public class BeanScopeDisplayDemo {

	public static void main(String[] args) {
		// load all singleton definition as defined in the XML
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanScopeContext.xml");

		System.out.println("-- Spring application Context Constructed --");


		// test-1: constructing a singleton class that has input as singleton class
		// myCoachSingleton (singleton) takes constructor input as HappyFortuneService (prototype) constructed during 
		// spring application context loading time.
		// multiple instance of myCoachSingleton are same and therefore its element are object equal (address same)
		Coach coach1 = context.getBean("myCricketCoachSingleton", Coach.class);
		Coach coach2 = context.getBean("myCricketCoachSingleton", Coach.class);
		boolean status = (coach1 == coach2);
		System.out.println("compare status - myCoachSingleton: " + status);
		System.out.println("coach1 address: " + coach1 + ", HappyFortuneService: " + coach1.getFortuneAddress());
		System.out.println("coach2 address: " + coach2 + ", HappyFortuneService: " + coach2.getFortuneAddress());

		System.out.println("-- HappyFortuneService is prototye by default in the XML");

		// test-2: constructing a prototype class that has input as singleton class
		// myCoachPrototype (prototype) takes constructor input as HappyFortuneService (prototype), a new instance of 
		// HappyFortuneService is constructed for every new instance of myCoachPrototype.
		coach1 = context.getBean("myCricketCoachPrototype", Coach.class);
		coach2 = context.getBean("myCricketCoachPrototype", Coach.class);
		status = (coach1 == coach2);
		System.out.println("compare status - myCoachPrototype: " + status);
		System.out.println("coach1 address: " + coach1 + ", HappyFortuneService: " + coach1.getFortuneAddress());
		System.out.println("coach2 address: " + coach2 + ", HappyFortuneService: " + coach2.getFortuneAddress());

		// info: spring context only has object reference of singleton and not of prototype hence can only call 
		// destroy singleton upon context close
		System.out.println("-- Spring application context closed, singleton Destroy is called but not for prototype.");
		context.close();
	}

}

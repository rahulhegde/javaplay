package spring.rahul.coach;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author developer
 *
 */
public class CoachApp {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println("Spring context container created");
		Coach myCoach = context.getBean("myCoach", Coach.class);
		System.out.println("Retrieve created bean");
		
		System.out.println(myCoach.getSuggestion());
		System.out.println(myCoach.getFortune());
		
		context.close();
	}
}

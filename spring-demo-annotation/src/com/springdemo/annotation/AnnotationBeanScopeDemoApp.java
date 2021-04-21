package com.springdemo.annotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationBeanScopeDemoApp {

	// Demo App shows the loading the beans using component scan of package using XML definition.
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"beanAnnotationContext.xml");
		
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

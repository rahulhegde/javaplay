package com.springdemo.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AnnotationJavaConfig {

	@Bean("SwimCoach")
	public Coach CreateSwimCoachBean( ) {
		System.out.println("swim coach instantiated");
		return new SwimCoach(CreateFortuneService());
	}
	
	@Bean
	public FortuneTeller CreateFortuneService( ) {
		System.out.println("FortuneTeller instantiated");
		return new TestFortuneTeller();
	}
}

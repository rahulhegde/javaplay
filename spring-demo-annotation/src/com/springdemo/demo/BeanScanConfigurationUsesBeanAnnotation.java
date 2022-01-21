package com.springdemo.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.springdemo.coach.Coach;
import com.springdemo.coach.SwimCoach;
import com.springdemo.fortuneteller.FortuneTeller;
import com.springdemo.fortuneteller.TestFortuneTeller;

@Configuration
@PropertySource("classpath:coach2.properties")
public class BeanScanConfigurationUsesBeanAnnotation {
	@Value("${fortuneTeller.name}") 
	private String value;

	// the @configuration with @bean annotation helps provide spring support to class 
	// that does not define it as bean
	
	@Bean(name = "SwimCoach", initMethod = "swimcoachBeanLifecyclePostConstructEvent", destroyMethod = "swimcoachBeanLifecyclePreDestroyEvent")
	public Coach CreateSwimCoachBean() {
		System.out.println("swim coach instantiated, reading from property source: " + value);
		return new SwimCoach(CreateFortuneService());
	}
	
	@Bean
	public FortuneTeller CreateFortuneService( ) {
		System.out.println("TestFortuneTeller instantiated");
		return new TestFortuneTeller();
	}
}

package com.springdemo.coach;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("SimpleTennisCoach")
@Scope("prototype")
public class TennisCoach implements Coach, DisposableBean {
	
	@Value("tennis.cricket.name")
	private String name;
	
	@Override
	public String getCoachAdvice() {
		return "Do a backend volley for 15mins";
	}

	@Override
	public String getFortuneAdvice() {
		System.out.println("Tennis Coach Name @Value: " + name);
		return "Tennis Coach - Empty";
	}

	@PostConstruct
	public void PostConstructMethod() {
		System.out.println("Tennis Coach bean' post construct called" + this);
	}
	
	@PreDestroy
	private void PreDestroyMethod() {
		// this is called before DisposableBean::destroy
		System.out.println("Tennis Coach bean' destroy called" + this);
	}
	
	@Override
	public void destroy() {
		System.out.println("Tennis Coach DisposableBean destroy method called" + this);
	}
}

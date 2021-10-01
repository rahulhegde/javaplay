package com.springdemo.prototypescope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("prototypeTennisCoach")
@Scope("prototype")
public class PrototypeTennisCoach implements DisposableBean {
	@Value("tennis.cricket.name")
	private String name;

	@PostConstruct
	public void PostConstructMethod() {
		System.out.println("Tennis Coach bean' post construct called" + this);
	}
	
	@PreDestroy
	private void PreDestroyMethod() {
		// this is called before DisposableBean::destroy
		System.out.println("Tennis Coach bean' destroy called: " + this);
	}
	
	@Override
	public void destroy() {
		System.out.println("Tennis Coach DisposableBean destroy method called: " + this);
	}
}

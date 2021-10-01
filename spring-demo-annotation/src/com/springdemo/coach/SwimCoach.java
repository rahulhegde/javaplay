package com.springdemo.coach;

import javax.annotation.PostConstruct;

import com.springdemo.fortuneteller.FortuneTeller;

public class SwimCoach implements Coach {
	
	private FortuneTeller fortuneTeller;
	
	public SwimCoach(FortuneTeller fortuneTeller) {
		super();
		this.fortuneTeller = fortuneTeller;
	}

	public SwimCoach() {
		super();
	}

	@Override
	public String getCoachAdvice() {
		return "Do a swim for 15mins";
	}

	@Override
	public String getFortuneAdvice() {
		return fortuneTeller.GetFortune();
	}
	
	// any name, no argument allowed, any method scope, argument preferable void as cannot be tracked by application
	public void swimcoachBeanLifecyclePostConstructEvent() {
		System.out.println("swim coach - singleton and hence bean post-initialized called after bean creation by spring context init");
	}
	
	public void swimcoachBeanLifecyclePreDestroyEvent() {
		System.out.println("swim coach - singleton and hence bean pre-destroyed called before bean destroy by spring context");
	}
}

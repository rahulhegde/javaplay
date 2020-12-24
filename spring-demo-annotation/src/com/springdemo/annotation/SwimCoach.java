package com.springdemo.annotation;

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
}

package com.springdemo.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

// default component name i.e. cricketCoach
@Component
@PropertySource("classpath:coach.properties")
@PropertySource("classpath:coach1.properties")
public class CricketCoach implements Coach {
	
	@Value("${coach.cricket.name}")
	private String name;
	
	@Value("${coach.cricket.type}")
	private String type;

	private FortuneTeller myFortuneTeller;
	private FortuneTeller myFortuneTeller1;
	
	@Autowired
	@Qualifier("happyFortuneTeller")
	private FortuneTeller myFortuneTeller2;
	
	@Autowired
	public CricketCoach(@Qualifier("randomFortuneTeller")FortuneTeller myFortuneTeller) {
		System.out.println("FortuneTeller autowired - constructor: " + myFortuneTeller);
		this.myFortuneTeller = myFortuneTeller;
	}
	
	@Autowired
	@Qualifier("testFortuneTeller")
	public void SetFortuneServiceUsingSetter(FortuneTeller myFortuneTeller) {
		System.out.println("FortuneTeller autowired - setter method: " + myFortuneTeller);
		this.myFortuneTeller1 = myFortuneTeller;
	}

//	public CricketCoach() {
//	}
	
	@Override
	public String getCoachAdvice() {
		return "Do a balling practice for 15mins";
	}

	@Override
	public String getFortuneAdvice() {
		if (myFortuneTeller == null) {
			return "Fortune Teller - Empty";
		} else {
			System.out.println("myFortuneTeller: " + myFortuneTeller + ", "
					+ "myFortuneTeller1: " + myFortuneTeller1 + ", myFortuneTeller2: " + myFortuneTeller2);
			System.out.println("Cricket Coach Name: " + name);
			System.out.println("Cricket Coach Type: " + type);
			return myFortuneTeller.GetFortune();
		}
	}
}

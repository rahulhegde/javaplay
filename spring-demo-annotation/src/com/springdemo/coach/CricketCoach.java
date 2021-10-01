package com.springdemo.coach;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.springdemo.fortuneteller.FortuneTeller;

// default component name i.e. cricketCoach
@Component
@PropertySource("classpath:coach1.properties")
@PropertySource("classpath:coach.properties")
public class CricketCoach implements Coach {
	
	@Autowired
	ApplicationContext applicationContext;
	
	// same attribute value present in coach, coach1 & coach2 however the order of last value is used as assignment
	// change the @PropertySource order to see the change.
	@Value("${coach.cricket.name}")
	private String coachname;
	
	// unique key present in one of the property source file
	@Value("${coach.cricket.type}")
	private String type;

	// unique key present in one of the property source file
	@Value("${coach.fetch}")
	private String fetchMetaDataFile;
	
	@Autowired
	@Qualifier("happyFortuneTeller")
	// even on private field using reflection
	private FortuneTeller myFortuneTellerObjectValueInject;

	// compilation error as spring is not able to inject field value to Qualifier using @value in it
	// below is way to do it using autowired and then using spring application context and class text.
//	@Autowired
//	@Qualifier(value = "${fortuneTeller.name}")
	private FortuneTeller myFortuneTellerObjectValueInjectThroughConfig;

	@Autowired
	public void setFortuneTellerUsingConfig(@Value("${fortuneTeller.name}") String fortuneTeller) {
		myFortuneTellerObjectValueInjectThroughConfig = applicationContext.getBean(fortuneTeller, FortuneTeller.class); 
		System.out.println("setter for forturne teller populated using config: ${fortuneTeller.name} : " + fortuneTeller + " " + myFortuneTellerObjectValueInjectThroughConfig);
	}
	
	private FortuneTeller myFortuneTellerConstructorInject;
	private FortuneTeller myFortuneTellerMethodInject;
	
	@Autowired
	public CricketCoach(@Qualifier("randomFortuneTeller")FortuneTeller myFortuneTeller) {
		System.out.println("FortuneTeller autowired - constructor: " + myFortuneTeller);
		this.myFortuneTellerConstructorInject = myFortuneTeller;
	}
	
	@Autowired
	@Qualifier("testFortuneTeller")
	// can be any method name and can be private method using reflection
	private void SetFortuneServiceUsingSetter(FortuneTeller myFortuneTeller) {
		System.out.println("FortuneTeller autowired - setter method: " + myFortuneTeller);
		this.myFortuneTellerMethodInject = myFortuneTeller;
	}
	
	@Override
	public String getCoachAdvice() {
		return "Do a balling practice for 15mins";
	}

	@Override
	public String getFortuneAdvice() {
		if (myFortuneTellerConstructorInject == null) {
			return "Fortune Teller - Empty";
		} else {
			System.out.println("myFortuneTeller: " + myFortuneTellerConstructorInject + ", "
					+ "myFortuneTeller1: " + myFortuneTellerMethodInject + ", myFortuneTeller2: " + myFortuneTellerObjectValueInject);
			System.out.println("Cricket Coach Name: " + coachname);
			System.out.println("Cricket Coach Type: " + type);
			return myFortuneTellerConstructorInject.GetFortune();
		}
	}

	@Override
	public String toString() {
		return "CricketCoach [coachname=" + coachname + ", type=" + type + ", fetchMetaDataFile=" + fetchMetaDataFile
				+ ", myFortuneTellerValueInject=" + myFortuneTellerObjectValueInject + ", myFortuneTellerConstructorInject="
				+ myFortuneTellerConstructorInject + ", myFortuneTellerMethodInject=" + myFortuneTellerMethodInject
				+ "]";
	}
	
	// any name, no argument allowed, any method scope, argument preferable void as cannot be tracked by application
	@PostConstruct
	public void cricketCoachBeanPostConstructFunction() {
		System.out.println("cricket coach - singleton and hence bean post-initialized called after bean creation by spring context init");
	}

	// any name, no argument allowed, any method scope, argument preferable void as cannot be tracked by application
	@PreDestroy
	public void cricketCoachBeanPreDestroyMethod() {
		System.out.println("cricket coach - singleton and hence bean pre-destroyed called before bean destroy by spring context");
	}
	
}

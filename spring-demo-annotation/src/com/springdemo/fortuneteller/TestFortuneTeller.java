package com.springdemo.fortuneteller;

import org.springframework.stereotype.Component;

@Component
public class TestFortuneTeller implements FortuneTeller {

	@Override
	public String GetFortune() {
		return "TestFortune Teller: Its nice day!";
	}

}
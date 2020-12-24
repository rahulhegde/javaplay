package com.springdemo.annotation;

import org.springframework.stereotype.Component;

@Component
public class HappyFortuneTeller implements FortuneTeller {

	@Override
	public String GetFortune() {
		return "HappyFortune Teller: Its nice day!";
	}

}

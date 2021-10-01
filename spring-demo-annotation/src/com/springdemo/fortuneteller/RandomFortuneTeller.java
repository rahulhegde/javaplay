package com.springdemo.fortuneteller;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomFortuneTeller implements FortuneTeller {
	
	private String[] fortuneMEssages = {
		"my first fortune message",
		"my second fortune message"	
	};

	private Random myRandom = new Random();

	@Override
	public String GetFortune() {
		return fortuneMEssages[myRandom.nextInt(fortuneMEssages.length)];
	}
}

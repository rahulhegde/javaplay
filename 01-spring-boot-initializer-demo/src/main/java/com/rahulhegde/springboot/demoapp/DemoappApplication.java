package com.rahulhegde.springboot.demoapp;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoappApplication {

	// @SpringBootApplication is helper annotation and defines 
	public static void main(String[] args) {
		// Option 1
		// More finer control on the Spring Application Object
		SpringApplication app = new SpringApplication(DemoappApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		
		// Option 2 - Start the Spring Application with default setting
		// SpringApplication.run(DemoappApplication.class, args);
	}
}

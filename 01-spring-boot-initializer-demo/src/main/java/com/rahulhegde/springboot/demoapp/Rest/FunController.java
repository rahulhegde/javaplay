package com.rahulhegde.springboot.demoapp.Rest;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunController {

	@GetMapping ("/hello")
	public String Hello() {
		return "Hello World, time: " + LocalDate.now();
	}
}

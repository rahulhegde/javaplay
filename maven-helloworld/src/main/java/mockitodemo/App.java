package com.rahulhegde.maven;

import java.time.ZoneId;

import net.bytebuddy.utility.privilege.GetSystemPropertyAction;

/**
 * Hello world!
 *
 */
public class App 
{	
	private int property;
	
	public static int addition(int number1, int number2) {
		return number1 + number2;
	}
	
	public int multiple (int multiplier1, int multiplier2) {
		return multiplier1* multiplier2;
	}
	
	public int printer(String name) {
		String prependString = prepend(name);
		System.out.println("string: " + prependString);
		return prependString.length();
	}
	
	private String prepend(String name) {
		return "prepend" + name;
	}
	
	public static void main(String args[]) {
		System.out.println("Zone Id: " + ZoneId.of("Asia/Kolkata"));
	}
	
	int getProperty() {
		return property;
	}
}
package com.hibernate.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "student")
public class Student {
	
	@Id
	@GeneratedValue
	private int Id;

	
	
}

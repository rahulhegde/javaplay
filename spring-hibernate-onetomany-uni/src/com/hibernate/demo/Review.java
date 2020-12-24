package com.hibernate.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="review")
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id")
	private int Id;
	
	@Column(name = "comment")
	private String comment;

	@Override
	public String toString() {
		return "Review [Id=" + Id + ", comment=" + comment + "]";
	}

	public Review(String comment) {
		this.comment = comment;
	}

	public Review() {

	}
}

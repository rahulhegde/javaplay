package com.hibernate.demo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id")
	private int courseId;
	
	@Column(name = "title")
	private String title;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "instructor_id")
	private Instructor instructor;
	
	// single course contains multiple reviews
	// here the course_id is the foreign key on the target table such that Review
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private List<Review> reviews;
	
	public void addReview (Review review) {
		if (reviews == null) {
			reviews = new ArrayList<Review>();
		}
		reviews.add(review);
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", title=" + title + "]";
	}

	public Course(String title) {
		this.title = title;
	}
	public Course() {
	}

}

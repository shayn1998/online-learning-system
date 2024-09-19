package com.company_name.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long courseId;
	
	private String courseName;
	
	private int numberOfChapters;
	
	@ElementCollection
	private List<Topics> courseTopics;
	
	public Course() {
		super();
	}

	public Course(Long courseId, String courseName, int numberOfChapters, List<Topics> courseTopics) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.numberOfChapters = numberOfChapters;
		this.courseTopics = courseTopics;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getNumberOfChapters() {
		return numberOfChapters;
	}

	public void setNumberOfChapters(int numberOfChapters) {
		this.numberOfChapters = numberOfChapters;
	}

	public List<Topics> getCourseTopics() {
		return courseTopics;
	}

	public void setCourseTopics(List<Topics> courseTopics) {
		this.courseTopics = courseTopics;
	}

}

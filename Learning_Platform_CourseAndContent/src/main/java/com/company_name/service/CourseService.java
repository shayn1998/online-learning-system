package com.company_name.service;

import java.util.List;
import java.util.Optional;

import com.company_name.model.Course;

//Service Layer Interface

public interface CourseService {

	boolean existsByCourseName(String courseName);

	void save(Course course);

	boolean existsById(long courseId);

	Optional<Course> findById(long courseId);

	void deleteById(long courseId);

	List<Course> findAll();


}

package com.company_name.repository;

//Repository

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_name.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

	boolean existsByCourseName(String courseName);

}

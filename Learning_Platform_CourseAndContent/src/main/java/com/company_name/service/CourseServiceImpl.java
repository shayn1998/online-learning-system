package com.company_name.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company_name.model.Course;
import com.company_name.repository.CourseRepository;

//Service Layer Implementation Class

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseRepository courseRepo;

	@Override
	public boolean existsByCourseName(String courseName) {
		// TODO Auto-generated method stub
		return courseRepo.existsByCourseName(courseName);
	}

	@Override
	public void save(Course course) {
		// TODO Auto-generated method stub
		courseRepo.save(course);
		
	}

	@Override
	public boolean existsById(long courseId) {
		// TODO Auto-generated method stub
		return courseRepo.existsById(courseId);
	}


	@Override
	public Optional<Course> findById(long courseId) {
		// TODO Auto-generated method stub
		return courseRepo.findById(courseId);
	}

	@Override
	public void deleteById(long courseId) {
		// TODO Auto-generated method stub
		
		courseRepo.deleteById(courseId);
		
	}

	@Override
	public List<Course> findAll() {
		// TODO Auto-generated method stub
		return courseRepo.findAll();
	}

}

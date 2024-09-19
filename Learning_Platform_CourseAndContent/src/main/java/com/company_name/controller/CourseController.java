package com.company_name.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.company_name.model.Course;
import com.company_name.model.Topics;
import com.company_name.service.*;

@Controller
public class CourseController {
	
	//Autowiring the bean of service class which contains the dao definitions

	@Autowired
	private CourseService courseService;

	// Handler method for returning create course form
	
	// Redirecting to this url from Author Controller
	
	@RequestMapping("/course_createCourse")
	String createCourse(){
		
		return "CreateCourseForm";
		
	}
	
	//Handler method to handle the data submitted by Author through CreateCourseForm
	
	@RequestMapping("/course_creatingCourse")
	ModelAndView creatingCourse(@RequestParam("courseName") String courseName, @RequestParam("topics.topicName") String [] topicNames, @RequestParam("topics.numberOfPages") Integer[] numberOfPages) {
		
		ModelAndView mv = new ModelAndView();
		
		if(!(courseService.existsByCourseName(courseName))) {
		
			Course course = new Course();
		
			course.setCourseName(courseName);
		
			List<Topics> listOfTopics = new ArrayList<>();
			
			List<String> doneList = new ArrayList<>();
		
			for(int i = 0; i < topicNames.length; i += 1) {
		
				String topic = topicNames[i];
				
				if(doneList.indexOf(topic) == -1) {
		
					switch(topic) {
			
						case "Topic1":
					
							Topics tp1= new Topics();
				
							tp1.setTopicName(topic);
					
							tp1.setNumberOfPages(numberOfPages[i]);
					
							listOfTopics.add(tp1);
						
							break;
						
						case "Topic2":
						
							Topics tp2= new Topics();
					
							tp2.setTopicName(topic);
				
							tp2.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp2);
					
							break;
						
						case "Topic3":

							Topics tp3= new Topics();
					
							tp3.setTopicName(topic);
				
							tp3.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp3);
					
							break;
						
						case "Topic4":

							Topics tp4= new Topics();
					
							tp4.setTopicName(topic);
				
							tp4.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp4);
					
							break;
						
						case "Topic5":

							Topics tp5= new Topics();
					
							tp5.setTopicName(topic);
				
							tp5.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp5);
					
							break;

						case "Topic6":

							Topics tp6= new Topics();
					
							tp6.setTopicName(topic);
				
							tp6.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp6);
					
							break;

						case "Topic7":

							Topics tp7= new Topics();
					
							tp7.setTopicName(topic);
				
							tp7.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp7);
					
							break;
	
					
						case "Topic8":

							Topics tp8= new Topics();
					
							tp8.setTopicName(topic);
				
							tp8.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp8);
					
							break;

						case "Topic9":

							Topics tp9= new Topics();
					
							tp9.setTopicName(topic);
				
							tp9.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp9);
					
							break;
	
						case "Topic10":

							Topics tp10= new Topics();
					
							tp10.setTopicName(topic);
				
							tp10.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp10);
					
							break;
						
					}
					
					doneList.add(topic);
						
				}
		
		
			}
			
			course.setNumberOfChapters(listOfTopics.size());
	
			course.setCourseTopics(listOfTopics);
			
			courseService.save(course);
			
			mv.addObject("msg", "Course Added");
			
			mv.setViewName("CreateCourseForm");
			
		}else {
			
			mv.addObject("msg", "Can't add Course");
			
			mv.setViewName("CreateCourseForm");
		
		}

		return mv;
		
	}
	
	// Handler method for returning edit course form
	
	// Redirecting to this url from Author Controller
	
	@RequestMapping("/course_editCourse")
	String editCourse() {
		
		return "EditCourseForm";
		
	}
	
	//Handler method to handle the data submitted by Author through EditCourseForm
	
	@PostMapping("/course_editingCourse")
	ModelAndView creatingCourse(@RequestParam("courseId") long courseId, @RequestParam("courseName") String courseName, @RequestParam("topics.topicName") String [] topicNames, @RequestParam("topics.numberOfPages") Integer[] numberOfPages) {
		
		ModelAndView mv = new ModelAndView();
		
		if(courseService.existsById(courseId)) {
			
			Course course = courseService.findById(courseId).orElse(null);
			
			course.setCourseName(courseName);
		
			List<Topics> listOfTopics = new ArrayList<>();
			
			List<String> doneList = new ArrayList<>();
		
			for(int i = 0; i < topicNames.length; i += 1) {
		
				String topic = topicNames[i];
				
				if(doneList.indexOf(topic) == -1) {
		
					switch(topic) {
			
						case "Topic1":
					
							Topics tp1= new Topics();
				
							tp1.setTopicName(topic);
					
							tp1.setNumberOfPages(numberOfPages[i]);
					
							listOfTopics.add(tp1);
						
							break;
						
						case "Topic2":
						
							Topics tp2= new Topics();
					
							tp2.setTopicName(topic);
				
							tp2.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp2);
					
							break;
						
						case "Topic3":

							Topics tp3= new Topics();
					
							tp3.setTopicName(topic);
				
							tp3.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp3);
					
							break;
						
						case "Topic4":

							Topics tp4= new Topics();
					
							tp4.setTopicName(topic);
				
							tp4.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp4);
					
							break;
						
						case "Topic5":

							Topics tp5= new Topics();
					
							tp5.setTopicName(topic);
				
							tp5.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp5);
					
							break;

						case "Topic6":

							Topics tp6= new Topics();
					
							tp6.setTopicName(topic);
				
							tp6.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp6);
					
							break;

						case "Topic7":

							Topics tp7= new Topics();
					
							tp7.setTopicName(topic);
				
							tp7.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp7);
					
							break;
	
					
						case "Topic8":

							Topics tp8= new Topics();
					
							tp8.setTopicName(topic);
				
							tp8.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp8);
					
							break;

						case "Topic9":

							Topics tp9= new Topics();
					
							tp9.setTopicName(topic);
				
							tp9.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp9);
					
							break;
	
						case "Topic10":

							Topics tp10= new Topics();
					
							tp10.setTopicName(topic);
				
							tp10.setNumberOfPages(numberOfPages[i]);
				
							listOfTopics.add(tp10);
					
							break;
						
					}
					
					doneList.add(topic);
						
				}
		
		
			}
			
			course.setNumberOfChapters(listOfTopics.size());
	
			course.setCourseTopics(listOfTopics);
			
			courseService.save(course);
			
			mv.addObject("msg", "Course Edited");
			
			mv.setViewName("EditCourseForm");
			
		}else {
			
			mv.addObject("msg", "Can't edit Course");
			
			mv.setViewName("EditCourseForm");
		
		}

		return mv;

	}
	
	// Handler method for returning delete course form
	
	// Redirecting to this url from Author Controller
	
	@RequestMapping("/course_removeCourse")
	String deleteCourse() {
		
		return "DeleteCourseForm";
		
	}
	
	//Handler method to handle the data submitted by Author through DeleteCourseForm
	
	@GetMapping("/course_deletingCourse")
	ModelAndView deletingCourse(@RequestParam("courseId") long courseId) {
		
		ModelAndView mv = new ModelAndView();
		
		if(courseService.existsById(courseId)) {
			
			courseService.deleteById(courseId);
			
			mv.addObject("msg", "Course Deleted Successfully");
			
		}else {
			
			mv.addObject("msg", "No such course exists");
			
		}
		
		mv.setViewName("DeleteCourseForm");
		
		return mv;
		
	}
	
	// Handler method for viewing all Courses
	
	// Redirecting to this url from Author Controller
	
	@RequestMapping("/course_viewAllCourses")
	ModelAndView viewingAllCourses() {
		
		ModelAndView mv = new ModelAndView();
		
		List<Course> listOfCourses = courseService.findAll();
		
		mv.addObject("courses", listOfCourses);
		
		mv.setViewName("AllCourses");
		
		return mv;
		
	}
	
	//Handler method to show all the courses available for learners
	
	// Redirecting to this url from Learner Controller
	@RequestMapping("/course_allCoursesForLearners")
	ModelAndView allCoursesForLearners() {
		
		ModelAndView mv = new ModelAndView();
		
		List<Course> listOfCourses = courseService.findAll();
		
		mv.addObject("courses", listOfCourses);
		
		mv.setViewName("AllCoursesForLearnersPage");
		
		return mv;
		
	}
	
}
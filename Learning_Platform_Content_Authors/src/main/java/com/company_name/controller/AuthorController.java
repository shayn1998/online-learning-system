package com.company_name.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.company_name.model.Author;
import com.company_name.service.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	//Autowiring the bean of service class which contains the dao definitions
	
	@Autowired
	private AuthorService authorService;
	
	//Handler for returning registration form to the Author
	
	@RequestMapping("/")
	String home() {
		
		return "home";
		
	}
	
	@RequestMapping("/author_register")
	String register() {
		
		return "Register";
			
	}
	
	//Handler for registering Author-details and saving it in a database
	
	@PostMapping("/author_registering")
	ModelAndView registering(@RequestParam("authorEmailId") String authorEmailId, @RequestParam("authorName") String authorName, @RequestParam("authorPassword") String authorPassword) {
		
		ModelAndView mv = new ModelAndView();
		
		if(!(authorService.existsById(authorEmailId))) {
			
			Author author = new Author();
			
			author.setAuthorEmailId(authorEmailId);
			
			author.setAuthorName(authorName);
			
			author.setAuthorPassword(authorPassword);
			
			authorService.save(author);
			
			mv.addObject("msg", "Author saved successfully");
			
			mv.setViewName("Register");
			
		}else {
			
			mv.addObject("msg", "Author already exists");
			
			mv.setViewName("Register");
			
		}
		
		return mv;
		
	}
	
	//Handler for returning login page to the author 
	
	@RequestMapping("/author_login")
	String login() {
		
		return "loginpage";
			
	}
	
	//Handler for logging in the Author and authenticating his details
	
	@PostMapping("/author_loggingIn")
	ModelAndView loggingIn(@RequestParam("authorEmailId") String authorEmailId, @RequestParam("authorPassword") String authorPassword) {
		
		ModelAndView mv = new ModelAndView();
		
		if(authorService.existsById(authorEmailId)) {
			
			Author author = authorService.findById(authorEmailId).orElse(null);
				
			if(author.getAuthorPassword().equals(authorPassword)) {
				
				mv.setViewName("Dashboard");
				
			}else {
				
				mv.addObject("msg", "Wrong Password");		
				
				mv.setViewName("loginpage");
			
			}
		
		}else {
			
			mv.addObject("msg", "Account doesn't exists, pls register");
			
			mv.setViewName("loginpage");
			
		}
	
		return mv;
		
	}
	
	// Handler to return form for editing details of the Author
	
	@RequestMapping("/author_editDetails")
	String editDetails() {
		
		return "EditDetailsPage";
		
	}
	
	//Handler that handles EditDetails form submitted by author and save the required changes
	
	@PostMapping("/author_editingDetails")
	ModelAndView editingDetails(@RequestParam("authorEmailId") String authorEmailId, @RequestParam("authorName") String authorName, @RequestParam("authorOldPassword") String authorOldPassword, @RequestParam("authorNewPassword") String authorNewPassword) {
		
		ModelAndView mv = new ModelAndView();
		
		if(authorService.existsById(authorEmailId)) {
			
			Author author = authorService.findById(authorEmailId).orElse(null);
			
			if(author.getAuthorPassword().equals(authorOldPassword)) {
			
				author.setAuthorName(authorName);
			
				author.setAuthorPassword(authorNewPassword);
			
				authorService.save(author);
			
				mv.addObject("msg", "Author Edited Successfully");
				
			}else {
				
				mv.addObject("msg", "Wrong Password");
				
			}
			
		}else {
			
			mv.addObject("msg", "Can't find Account");
			
		}
		
		mv.setViewName("EditDetailsPage");
		
		return mv;
		
	}
	
	// Handler that returns the form to remove the account if it exists.
	
	@RequestMapping("/author_removeAccount")
	String removeAccount() {
		
		return "RemoveAccountPage";
		
	}
	
	//Handler method to handle the data submitted by Author through RemoveAccountPage
	
	@GetMapping("/author_removingAccount")
	ModelAndView removingAccount(@RequestParam("authorEmailId") String authorEmailId, @RequestParam("authorPassword") String authorPassword) {
	
		ModelAndView mv = new ModelAndView();
		
		if(authorService.existsById(authorEmailId)) {
			
			Author author = authorService.findById(authorEmailId).orElse(null);
			
			if((author.getAuthorPassword().equals(authorPassword))) {
			
				authorService.deleteById(authorEmailId);
			
				mv.addObject("msg", "Author Deleted Successfully !!");
				
			}else {
				
				mv.addObject("msg", "Wrong Password");
				
			}
			
		}else {
			
			mv.addObject("msg", "No such Account Exists..");
			
		}
		
		mv.setViewName("RemoveAccountPage");
		
		return mv;
		
	}
	
	// Handler method for viewing all the authors
	
	// Redirecting to this url from Admin Controller
	@RequestMapping("/author_allAuthors")
	ModelAndView allAuthors() {
		
		ModelAndView mv = new ModelAndView();
		
		List<Author> authorsList = authorService.findAll();
		
		System.out.println(authorsList);
		
		mv.addObject("authors", authorsList);
		
		mv.setViewName("AllAuthors");
		
		return mv;
		
	}
	
	//Handler method to redirect to course_&_content microservice for course_&_content related operations
	
	@RequestMapping("/author_createCourse")
	String createCourse() {
		
		final String serviceName = "course_&_content-service";
		
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
		
		URI uri = instances.get(0).getUri();
		
		String url = uri.toString() + "/course_createCourse";
		
		return "redirect:"+url;
	
	}
	
	//Handler method to redirect to course_&_content microservice for course_&_content related operations
	
	@RequestMapping("/author_editCourse")
	String editCourse() {
		
		final String serviceName = "course_&_content-service";
		
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
		
		URI uri = instances.get(0).getUri();
		
		String url = uri.toString() + "/course_editCourse";
		
		return "redirect:"+url;

		
	}
	
	//Handler method to redirect to course_&_content microservice for course_&_content related operations
	
	@RequestMapping("/author_removeCourse")
	String removeCourse() {
		
		final String serviceName = "course_&_content-service";
		
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
		
		URI uri = instances.get(0).getUri();
		
		String url = uri.toString() + "/course_removeCourse";
		
		return "redirect:"+url;

	}
	
	//Handler method to redirect to course_&_content microservice for course_&_content related operations
	
	@RequestMapping("/author_viewAllCourses")
	String viewAllCourses(){
		
		final String serviceName = "course_&_content-service";
		
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
		
		URI uri = instances.get(0).getUri();
		
		String url = uri.toString() + "/course_viewAllCourses";
		
		return "redirect:"+url;

	}

}

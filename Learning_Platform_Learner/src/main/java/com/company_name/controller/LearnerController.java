package com.company_name.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.company_name.model.Learner;
import com.company_name.service.LearnerService;

@Controller
@RequestMapping("/learner")
public class LearnerController {
	
	//Autowiring the bean of service class which contains the dao definitions
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private LearnerService learnerService;
	
	// Handler method for saving the details of learner coming after payment confirmation
	
	// Redirecting to this url from Payment Controller in payment-gateway application
	
	@RequestMapping("/")
	String home() {
		
		return "home";
		
	}

	@RequestMapping("/learner_setDetails")
	void initializeLearner(@RequestBody Map<String,Object> map) {
		
		Learner learner = new Learner();
		
		learner.setLearnerEmail(String.valueOf(map.get("id")));
		
		learner.setLearnerPassword(String.valueOf(map.get("password")));
		
		learner.setLearnerName(String.valueOf(map.get("name")));
		
		learner.setLearnerMobile(Long.parseLong(String.valueOf(map.get("mobile"))));
		
		learnerService.save(learner);
		
	}
	
	//Handler for logging in the Learner and authenticating his details
	
	@PostMapping("/learner_loggingIn")
	ModelAndView loggingin(@RequestParam("emailId") String emailId, @RequestParam("password") String password) {
		
		ModelAndView mv = new ModelAndView();
		
		Learner learner = learnerService.findById(emailId).orElse(null);
		
		if(learner != null) {
			
			if(learner.getLearnerPassword().equals(password)) {
				
				final String serviceName = "course_&_content-service";
				
				List<ServiceInstance> li = discoveryClient.getInstances(serviceName);
				
				URI uri = li.get(0).getUri();
				
				String url = uri.toString()+"/course_allCoursesForLearners";
				
				mv.setViewName("redirect:"+url);
				
			}else {
				
				mv.addObject("msg", "Incorrect Password");
				
				mv.setViewName("Message");
				
			}
			
			
		}else {
			
			mv.addObject("msg","Account doesn't exists");
			
			mv.setViewName("Message");
		}
		
		return mv;
		
	}
	
	@RequestMapping("/learner_allLearners")
	ModelAndView allLearners() {
		
		ModelAndView mv = new ModelAndView();
		
		List<Learner> li = learnerService.findAll();
		
		mv.addObject("learners", li);
		
		mv.setViewName("AllLearners");
		
		return mv;
		
	}

}

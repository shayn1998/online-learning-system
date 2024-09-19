package com.company_name.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company_name.model.AppUsers;
import com.company_name.service.AppUsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class AppUsersController {
	
	//Autowiring the bean of service class which contains the dao definitions
	@Autowired
	private AppUsersService appUsersService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	//Handler for returning Register Page to the User
	
	@RequestMapping("/")
	String home(){
		
		return "home";
		
	}
	
	@RequestMapping("/user_register")
	String register() {
		
		return "Register";
		
	}
	
	
	//Handler that handles Register form data submitted by the User
	@PostMapping("/user_registerUser")
	ModelAndView registerUser(@RequestParam("emailId") String emailId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("mobile") Long mobileNumber, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		
		if(!(appUsersService.existsById(emailId))) {
			
			AppUsers appusers = new AppUsers();
			
			appusers.setAppUsersEmail(emailId);
			
			appusers.setAppUsersPassword(password);
			
			appusers.setAppUsersName(name);
			
			appusers.setAppUsersMobile(mobileNumber);
			
			appusers.setSubscribedTo(-1);
			
			appusers.setBookedSubscription(false);
			
			appUsersService.save(appusers);
			
			mv.addObject("msg", "User Added Successfully");
			
			mv.setViewName("Register");
			
			session.setAttribute("id", "emailId");
			
		} else {
			
			mv.addObject("msg", "User already Exists");
			
			mv.setViewName("Register");
			
		}
			
		return mv;
		
	}
	
	//Handler for displaying all users to the Admin
	//Redirecting to this url from Admin Controller
	@RequestMapping("/user_allUsers")
	ModelAndView allUsers() {
		
		ModelAndView mv = new ModelAndView();
		
		List<AppUsers> userDetails = appUsersService.findAll();
		
		mv.addObject("msg", userDetails);
		
		mv.setViewName("AllUsers");
		
		return mv;
		
	}
	
	
	//Handler for returning Login Page to the User
	@RequestMapping("/user_login")
	String login() {
		
		return "loginPage";
		
	}
	
	//Handler that authenticates the login form data submitted by the User 
	//Redirects to allSubscriptionForUsers handler in the subscription controller after successfull authentication
	//Also creates a session using the id of the user after successfull authentication
	@PostMapping("/user_loggingIn")
	ModelAndView loggingIn(@RequestParam("emailId") String emailId, @RequestParam("password") String password, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		
		if(appUsersService.existsById(emailId)) {
			
			AppUsers appUsers = (AppUsers)appUsersService.findById(emailId).orElse(null);
			
			if(appUsers.getAppUsersPassword().equals(password)) {
				
				session.setAttribute("id", emailId);
				
				final String serviceName = "subscription-service";
				
				List<ServiceInstance> li = discoveryClient.getInstances(serviceName);
				
				URI uri = li.get(0).getUri();
				
				String url = uri.toString();
				
				url += "/subscription_allSubscriptionsForUser";
				
				mv.setViewName("redirect:"+url);
				
				
			}else {
				
				mv.addObject("msg", "Wrong Password, Kindly Check..");
				
				mv.setViewName("loginPage");
				
			}
			
		}else{
			
			mv.addObject("msg", "No such Account exists, Kindly Register first..");
			
			mv.setViewName("loginPage");
			
		}
		
		return mv;
		
	}
	
	//Handler handles the Post request coming from PaymentController in payment-service
	//Recieves a map object that contains id of user and the price filled by user in Payment form
	//Gets the details of the user using the id of user.
	//Get the id of the subscription to which the user has subscribed to
	//Send the Post request to the subscription controller to verify if the price recieved is equals to the price of the subscription.
	@PostMapping("/user_verifyPrice")
	@ResponseBody
	Boolean verifyPrice(@RequestBody Map<String, Object> map) {
		
		boolean priceVerification = false;
		
		AppUsers appusers = appUsersService.findById(String.valueOf(map.get("id"))).orElse(null);
		
		Long id = appusers.getSubscribedTo();
		
		Map<String, Object> aa = new HashMap<String, Object> ();
		
		aa.put("id", Long.parseLong(String.valueOf((id))));
		
		aa.put("price", Integer.parseInt(String.valueOf(map.get("price"))));
		
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(aa);
		
		final String serviceName = "subscription-service";
		
		List<ServiceInstance> li1 = discoveryClient.getInstances(serviceName);
		
		URI uri = li1.get(0).getUri();
		
		String url = uri.toString() + "/subscription_verifyPrice";
		
		Boolean result = restTemplate.postForObject(url,httpEntity,Boolean.class);
		
		boolean b = result;
		
		if(b == true) {
			
			priceVerification = true;
			
		}
		
		return priceVerification;
		
	}
	
	//Redirecting to this url from subscription-controller 
	//Receives a subscriptionId through @RequestParam
	//Uses HttpSession to get the id of the User who recently tried to book subscrition
	//Tries the verify if user hs already subscribed to some subscription and if his subscription is successfully booked
	//If not sets the subscribedTo the cuurently received subscriptonId.
	//Redirects to the allSubscriptionsForUserVerification Handler in subscription service if the user has already booked a subscription successfully
	//redirects to the payment-gateway if the subscription is not booked yet.
	@RequestMapping("/user_verifyUser")
	ModelAndView verifyUser(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam("subscriptionId") long subscriptionId) {
		
		ModelAndView mv  = new ModelAndView();
		
		String id = (String) session.getAttribute("id");
		
		AppUsers appusers = appUsersService.findById(id).orElse(null);
		
		if(appusers.getSubscribedTo() == -1 || appusers.isBookedSubscription() == false) {
			
			appusers.setSubscribedTo(subscriptionId);
			
			appUsersService.save(appusers);
			
			redirectAttributes.addAttribute("id", appusers.getAppUsersEmail());
			
			redirectAttributes.addAttribute("password", appusers.getAppUsersPassword());
			
			redirectAttributes.addAttribute("name", appusers.getAppUsersName());
			
			redirectAttributes.addAttribute("mobile", appusers.getAppUsersMobile());
			
			final String serviceName = "payment-service";
			
			List<ServiceInstance> li = discoveryClient.getInstances(serviceName);
			
			URI uri = li.get(0).getUri();
			
			String url = uri.toString() + "/home";
			
			mv.setViewName("redirect:"+url);
			
		}else {
			
			redirectAttributes.addAttribute("str", "User already Subscribed");
			
			final String serviceName = "subscription-service";
			
			List<ServiceInstance> li = discoveryClient.getInstances(serviceName);
			
			URI uri = li.get(0).getUri();
			
			String url = uri.toString();
			
			url += "/subscription_allSubscriptionsForUserVerification";
			
			mv.setViewName("redirect:"+url);
			
		}
		
		return mv;
		
	}
	
	
	//redireting to this url from PaymentController in payment-service after payment is successfull
	//it sets the bookedSubscription to true symbolizing that the user has successfully booked a subscription
	@ResponseBody
	@PostMapping("/user_paymentConfirmation")
	Boolean paymentConfirmation(@RequestBody String id) {
		
		AppUsers appuser = appUsersService.findById(id).orElse(null);
		
		appuser.setBookedSubscription(true);
		
		appUsersService.save(appuser);
		
		return true;
		
	}

}

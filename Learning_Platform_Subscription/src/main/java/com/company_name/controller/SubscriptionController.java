package com.company_name.controller;
import java.util.List;
import java.util.Map;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company_name.model.Subscription;
import com.company_name.service.SubscriptionService;

@Controller
public class SubscriptionController {
	
	
	//Autowiring the bean of service class which contains the dao definitions
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	//Handler for returning CreateSubscriptionPage to the Admin
	//Redirecting to this url from AdminController
	@RequestMapping("/subscription_createSubscription")
	String createSubscription() {
		
		return "CreateSubscriptionPage";
		
	}
	
	//Handler that handles CreateSubscriptionPage form data submitted by Admin
	
	@GetMapping("/subscription_creatingSubscription")
	ModelAndView creatingSubscription(@RequestParam("subscriptionName") String subscriptionName, @RequestParam("subscriptionPrice") int subscriptionPrice, @RequestParam("durationInMonths") int durationInMonths) {
		
		ModelAndView mv = new ModelAndView();
		
		Subscription subscription = new Subscription();
		
		subscription.setSubscriptionName(subscriptionName);
		
		subscription.setSubscriptionPrice(subscriptionPrice);
		
		subscription.setDurationInMonths(durationInMonths);
		
		if(subscriptionService.save(subscription) != null) {
			
			mv.addObject("msg", "Subscription Added");
			
			mv.setViewName("CreateSubscriptionPage");
			
		}else {
			
			mv.addObject("msg", "Can't add Subscription");
			
			mv.setViewName("CreateSubscriptionPage");
		
		}
		
		return mv;
		
	}
	
	//Handler for returning RemoveSubscriptionPage to the Admin
	//Redirecting to this url from AdminController
	@RequestMapping("/subscription_removeSubscription")
	String removeSubscription() {
		
		return "RemoveSubscriptionPage";
		
	}
	
	//Handler that handles RemoveSubscriptionPage form data submitted by Admin
	@GetMapping("/subscription_removingSubscription")
	ModelAndView removingSubscription(@RequestParam("subscriptionId") long subscriptionId){
		
		ModelAndView mv = new ModelAndView();
		
		if(subscriptionService.existsById(subscriptionId)) {
			
			subscriptionService.deleteById(subscriptionId);
			
			mv.addObject("msg", "Subscription Removed Successfully");
			
		}else {
			
			mv.addObject("msg", "No such subscription exists");
			
		}
		
		mv.setViewName("RemoveSubscriptionPage");
		
		return mv;
		
	}
	
	
	//Handler for returning UpdateSubscriptionPage to the Admin
	//Redirecting to this url from AdminController
	@RequestMapping("/subscription_updateSubscription")
	String updateSubscription() {
		
		return "UpdateSubscriptionPage";
		
	}
	
	//Handler that handles UpdateSubscriptionPage form data submitted by Admin
	@GetMapping("/subscription_updatingSubscription")
	ModelAndView updatingSubscription(@RequestParam("subscriptionId") long subscriptionId, @RequestParam("subscriptionName") String subscriptionName, @RequestParam("subscriptionPrice") double subscriptionPrice, @RequestParam("durationInMonths") int durationInMonths) {
		
		ModelAndView mv = new ModelAndView();
		
		if(subscriptionService.existsById(subscriptionId)) {
			
			Subscription subscription = subscriptionService.findById(subscriptionId).orElse(null);
			
			subscription.setSubscriptionName(subscriptionName);
			
			subscription.setSubscriptionPrice(subscriptionPrice);
			
			subscription.setDurationInMonths(durationInMonths);
			
			subscriptionService.save(subscription);
			
			mv.addObject("msg", "Subscription changed Successfully");
			
			mv.setViewName("UpdateSubscriptionPage");
			
		}else {
			
			mv.addObject("msg", "Subscription doesn't exists");
			
			mv.setViewName("UpdateSubscriptionPage");
			
		}
		
		return mv;
		
	}
	
	//Handler to show all subscriptions to the Admin
	//Redirecting to this url from AdminController
	@RequestMapping("/subscription_allSubscriptions")
	ModelAndView allSubscriptions() {
		
		ModelAndView mv  = new ModelAndView();
		
		List<Subscription> subscriptions = subscriptionService.findAll();
		
		mv.addObject("subscriptions", subscriptions);
		
		mv.setViewName("AllSubscriptions");
		
		return mv;
		
	}
	
	//Handler to show all subscriptions to the User
	//Redirecting to this url from UserController
	@RequestMapping("/subscription_allSubscriptionsForUser")
	ModelAndView allSubscriptionsForUser() {
		
		ModelAndView mv  = new ModelAndView();
		
		List<Subscription> subscriptions = subscriptionService.findAll();
		
		mv.addObject("subscriptions", subscriptions);
		
		mv.setViewName("AllSubscriptionsForUserPage");
		
		return mv;
		
		
	}
	
	//Handler to verify if the user has booked a right subscription i.e the subscription exists
	//Redirect to user-service after successfull verification for further verification regarding whether the user is already a subscriber
	//else render error message on Message.html page	
	//Using redirect attributes to redirect subscription details to the user-service
	@GetMapping("/subscription_bookSubscription")
	ModelAndView bookSubscription(@RequestParam("subscriptionId") long subscriptionId, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		
		if(!(subscriptionService.existsById(subscriptionId))){
			
			mv.addObject("msg", "Invalid Subscription Id, pls enter correct Id..");
			
			mv.setViewName("Message");
			
		}else {
			
			final String serviceName = "user-service";
			
			List<ServiceInstance> li = discoveryClient.getInstances(serviceName);
			
			URI uri = li.get(0).getUri();
			
			String url = uri.toString()+"/user/user_verifyUser";
			
			redirectAttributes.addAttribute("subscriptionId", subscriptionId);
			
			mv.setViewName("redirect:"+url);
			
		}
		
		return mv;
		
		
	}
	
	//Handler method to verfy whether the price of the subscription is equal to the price entered by User in Payment form
	//Redirecting to this url from user-service after getting the id of subscription to which the user has subscribed
	//Returns Boolean value as a response body to the handler that called it
	@PostMapping("/subscription_verifyPrice")
	@ResponseBody
	Boolean verifyPrice(@RequestBody Map<String, Object> aa) {
		
		boolean b = false;
		
		System.out.println(aa.get("id"));
		
		System.out.println(aa.get("price"));
		
		Subscription subscription = subscriptionService.findById(Long.parseLong(String.valueOf(aa.get("id")))).orElse(null);
		
		if(subscription.getSubscriptionPrice() ==  Double.parseDouble(String.valueOf(aa.get("price")))) {
			
			b = true;
			
		}
		
		return b;
		
	}
	
	//This handler method is called if the User is already a subscriber
	//Redirecting to this url from UserController
	//Renders the User already subscribed message in Message.html Page
	@RequestMapping("/subscription_allSubscriptionsForUserVerification")
	ModelAndView allSubscriptionsForUserVerification(@RequestParam("str") String str) {
		
		ModelAndView mv  = new ModelAndView();
		
		mv.addObject("msg",str);
		
		mv.setViewName("Message");
		
		return mv;
		
	}
	
}

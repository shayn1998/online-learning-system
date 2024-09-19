package com.company_name.controller;

import java.net.URI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//Controller class

@Controller
@RequestMapping("/admin")
public class AdminController {

	// Autowiring discovery client object for making connection across microservices

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/")
	String home() {
		
		return "home";
		
	}

	@RequestMapping("/admin_createSubscription")
	String createSubscription() {

		// redirecting to Subscription microservice for subscription creating
		// subscription

		final String serviceName = "subscription-service";

		// getting instances of subscription-service for eureka discovery client

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

		URI uri = instances.get(0).getUri();

		// appending string url to redirect to target url mapping

		String url = uri.toString() + "/subscription_createSubscription";

		return "redirect:" + url;

	}

	@RequestMapping("/admin_removeSubscription")
	String removeSubscription() {

		// redirecting to subscription microservice for removing subscription

		final String serviceName = "subscription-service";

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

		URI uri = instances.get(0).getUri();

		String url = uri.toString() + "/subscription_removeSubscription";

		return "redirect:" + url;

	}

	@RequestMapping("/admin_allSubscriptions")

	// redirecting to subscription microservice for retrieving all subscriptions

	String allSubscriptions() {

		final String serviceName = "subscription-service";

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

		URI uri = instances.get(0).getUri();

		String url = uri.toString() + "/subscription_allSubscriptions";

		return "redirect:" + url;

	}

	@RequestMapping("/admin_updateSubscription")
	String updateSubscription() {

		// redirecting to subscription microservice for updating subscription

		final String serviceName = "subscription-service";

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

		URI uri = instances.get(0).getUri();

		String url = uri.toString() + "/subscription_updateSubscription";

		return "redirect:" + url;

	}

	@RequestMapping("/admin_allUsers")
	String allUsers() {

		// redirecting to user microservice for retrieving all users

		final String serviceName = "user-service";

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

		URI uri = instances.get(0).getUri();

		String url = uri.toString() + "/user/user_allUsers";

		return "redirect:" + url;

	}

	@RequestMapping("/admin_allAuthors")
	String allAuthors() {

		// redirecting to author microservice for retrieving all authors

		final String serviceName = "author-service";

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

		URI uri = instances.get(0).getUri();

		String url = uri.toString() + "/author/author_allAuthors";

		return "redirect:" + url;

	}

	@RequestMapping("/admin_allLearners")
	String allLearners() {

		// redirecting to learner microservice for retrieving all learners

		final String serviceName = "learner-service";

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

		URI uri = instances.get(0).getUri();

		String url = uri.toString() + "/learner/learner_allLearners";

		return "redirect:" + url;

	}

}

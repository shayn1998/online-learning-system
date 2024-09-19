package com.company_name.controller;

import com.company_name.dto.CreatePayment;
import com.company_name.dto.CreatePaymentResponse;
import com.company_name.model.CheckoutForm;
import com.company_name.model.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class PaymentController {
	
	// Autowiring bean of rest Template for API consumption 
	@Autowired
	private RestTemplate restTemplate;

	//Autowiring a static User object for saving the User info. coming from user-service application  
	@Autowired
	private static User user;

	//Autowiring discovery client object for making connections across microservices
	@Autowired
	private DiscoveryClient discoveryClient;

	
	//Reading value of stripe public key defined in application.properties file
	@Value("${stripe.public.key}")
	private String stripePublicKey;

	
	//Reading value of stripe secret key defined in application.properties file
	@Value("${STRIPE_SECRET_KEY}")
	private String secretKey;

	
	//Initializing Stripe api key as secret key
	@PostConstruct
	public void init() {
		Stripe.apiKey = secretKey;
	}

	
	//Reading values directed from user-service application for saving it in an User object and returning index page after 
	//setting CkeckOutForm object as an attribute
	//Redirecting to this url from AppUsers Controller in user-service application
	@RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
	public String home(@RequestParam("id") String id, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("mobile") Long mobile, Model model) {
		
		user = new User();

		user.setUserId(id);

		user.setUserPassword(password);

		user.setUserName(name);

		user.setUserMobile(mobile);

		model.addAttribute("checkoutForm", new CheckoutForm());

		return "index";

	}
	
	
	//Handler method for handling data entered by User in index.html form
	@PostMapping("/")
	public ModelAndView checkout(@ModelAttribute @Valid CheckoutForm checkoutForm, BindingResult bindingResult,
			Model model) {

		ModelAndView mv = new ModelAndView();

		final String serviceName = "user-service";
		
		//Declaring map object for storing the id of the User and the price entered by the user in the index form  
		
		Map<String, Object> map = new HashMap<String, Object>();

		Integer price = checkoutForm.getAmount();

		map.put("id", new String(user.getUserId()));

		map.put("price", price);
		
		//Wrapping map object as an HttpEntity 
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map);

		List<ServiceInstance> li = discoveryClient.getInstances(serviceName);

		URI uri = li.get(0).getUri();
		
		// Verify in price entered by user in index form and the actual price of the subscription 

		String url = uri.toString() + "/user/user_verifyPrice";
		
		//Posting the map object containing user info. for verification in the user-service
		
		Boolean result = restTemplate.postForObject(url, httpEntity, Boolean.class);

		Boolean b = result;

		if (b == true) {

			if (bindingResult.hasErrors()) {

				mv.addObject("index");

				return mv;
			}

			model.addAttribute("stripePublicKey", stripePublicKey);

			model.addAttribute("amount", checkoutForm.getAmount());

			model.addAttribute("email", checkoutForm.getEmail());

			model.addAttribute("featureRequest", checkoutForm.getFeatureRequest());

			return new ModelAndView("checkout");

		} else {

			mv.addObject("msg", "Incorrect amount pls correct it.");

			mv.setViewName("index");

		}

		return mv;

	}
	
	//Handler method to create Payment Intent
	//Client.js make a POST request to this mapping on the loading of checkout.html page
	//Receive a CreatePayment DTO as a RequestBody and returns CreatePaymentResponse DTO in JSON format
	@ResponseBody
	@RequestMapping(value = "/create-payment-intent/", method = { RequestMethod.GET, RequestMethod.POST })
	public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {

		System.out.println("Inside create Payment");

		PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder().setCurrency("inr")
				.putMetadata("featureRequest", createPayment.getFeatureRequest())
				.setAmount(createPayment.getAmount() * 100L).build();

		PaymentIntent intent = PaymentIntent.create(createParams);

		System.out.println(intent.getClientSecret());

		return new CreatePaymentResponse(intent.getClientSecret());

	}
	
	//Redirected to this Url after Payment is successfull
	//This handler post the information of the user to the learner-service to register the particular User as a Learner  
	@RequestMapping(value = "/payment-confirmed", method = { RequestMethod.GET, RequestMethod.POST })
    public void paymentConfirmation(RedirectAttributes redirectattributes) {
    	
    	String id = user.getUserId();
    	
    	HttpEntity<String> httpEntity = new HttpEntity<>(id);
    	
    	final String serviceName = "user-service";
    	
    	List<ServiceInstance> li = discoveryClient.getInstances(serviceName);
    	
    	URI uri = li.get(0).getUri();
    	
    	String url = uri.toString() + "/user/user_paymentConfirmation";
    	
    	Boolean b = restTemplate.postForObject(url, httpEntity, Boolean.class);
    		
    	final String newServiceName = "learner-service";
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	map.put("id", user.getUserId());
    	
		map.put("password", user.getUserPassword());
			
		map.put("name", user.getUserName());
		
		map.put("mobile", (Long)(user.getUserMobile()));
		
		HttpEntity<Map<String, Object>> httpEntity1 = new HttpEntity<>(map);
				
        List<ServiceInstance> newLi = discoveryClient.getInstances(newServiceName);
        	
        URI newUri = newLi.get(0).getUri();
        	
        String newUrl = newUri.toString() + "/learner/learner_setDetails";    	
        	
        restTemplate.postForObject(newUrl, httpEntity1, Boolean.class);
        
	}
	
}

package com.company_name;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class AppSecurityConfig{
	@Bean
	protected InMemoryUserDetailsManager userDetailsService() {
		
		//Saving Admin details inside In-Memory Cache 
		
		UserDetails user = User.withDefaultPasswordEncoder()
	            .username("shyan")
	            .password("1234")
	            .roles("ADMIN")
	            .build();
	    return new InMemoryUserDetailsManager(user);
	    
		
	}
	
}
package com.company_name;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;


//AppSecurityConfig class for Verify admin details at the time of accessing the url
//The application is having exactly one Admin and his/her details are stored in In-memory.
@Configuration
@EnableWebSecurity
public class AppSecurityConfig{
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		
		return (http
			// ...
			.csrf(csrf -> csrf.disable())).build();
		 
	}
	
}

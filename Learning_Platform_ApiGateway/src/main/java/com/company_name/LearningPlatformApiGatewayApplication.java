package com.company_name;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
public class LearningPlatformApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningPlatformApiGatewayApplication.class, args);
	}
	/*
	@Bean
	public ServerCodecConfigurer serverCodecConfigurer() {
	   return ServerCodecConfigurer.create();
	}
	*/
	
	@Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
        	.route("user-route", r -> r.path("/user/**").uri("lb://user-service"))
         	.route("admin-route", r -> r.path("/admin/**").uri("lb://admin-service"))
         	.route("learner-route", r -> r.path("/learner/**").uri("lb://learner-service"))
         	.route("author-route", r -> r.path("/author/**").uri("lb://author-service"))
         	//.route("subscription-route", r -> r.path("/subscription/**").uri("lb://subscription-service"))
         	//.route("course_&_content-route", r -> r.path("/course/**").uri("lb://course_&_content-service"))
         	.build();
	}
	
}

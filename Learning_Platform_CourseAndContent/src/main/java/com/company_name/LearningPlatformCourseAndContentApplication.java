package com.company_name;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LearningPlatformCourseAndContentApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningPlatformCourseAndContentApplication.class, args);
	}

}

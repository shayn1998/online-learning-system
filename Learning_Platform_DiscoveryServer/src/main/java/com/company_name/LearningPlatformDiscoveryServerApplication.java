package com.company_name;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//Discovery Server main class

@SpringBootApplication
@EnableEurekaServer
public class LearningPlatformDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningPlatformDiscoveryServerApplication.class, args);
	}

}

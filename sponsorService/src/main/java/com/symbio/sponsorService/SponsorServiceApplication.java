package com.symbio.sponsorService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SponsorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SponsorServiceApplication.class, args);
	}

}

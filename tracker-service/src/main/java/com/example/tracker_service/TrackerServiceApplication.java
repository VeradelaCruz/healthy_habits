package com.example.tracker_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveMongoAuditing
public class TrackerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackerServiceApplication.class, args);
	}

}

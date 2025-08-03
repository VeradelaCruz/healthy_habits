package com.example.habit_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@OpenAPIDefinition(
		info = @Info(
				title = "Habit Service API",
				version = "1.0",
				description = "API para gestionar hábitos en Healthy Habits",
				contact = @Contact(
						name = "Vera de la Cruz",
						email = "maidanabru@yahoo.com.ar",
						url = "https://github.com/VeradelaCruz"
				),
				license = @License(
						name = "MIT License",
						url = "https://opensource.org/licenses/MIT"
				)
		),
		servers = {
				@Server(url = "http://localhost:8081", description = "Servidor local de desarrollo"),
				}
)

@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveMongoAuditing
public class HabitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitServiceApplication.class, args);
	}

}

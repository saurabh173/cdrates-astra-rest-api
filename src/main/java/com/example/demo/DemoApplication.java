package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
		info = @Info(
				title = "Bank Certificate of Deposit Rates APIs",
				description = "This application offers APIs for different groups to access and manage certificates of deposit rates.<br><br> GraphQL end point can be accessed from host:8080/graphql",
				version = "1"
		),
		tags = {
				@Tag(name = "admin-cd-rates-controller", description = "Controller for Admin to create and update CD Rates"),
				@Tag(name = "manager-cd-rates-controller", description = "Controller for managers to access current CD rates and historical CD Rates"),
				@Tag(name = "consumer-cd-rates-controller", description = "Controller for consumers to access current CD rates and historical CD Rates"),
				@Tag(name = "third-party-cd-rates-controller", description = "Controller for Third Parties to access current CD rates and historical CD Rates")
		}
)

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}

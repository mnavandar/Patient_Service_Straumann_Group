package com.example.patient_hub_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PatientHubServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientHubServiceApplication.class, args);
	}

}

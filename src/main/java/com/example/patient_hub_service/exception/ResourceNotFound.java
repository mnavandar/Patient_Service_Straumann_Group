package com.example.patient_hub_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResourceNotFound extends PatientExceptionClass {
	public ResourceNotFound(String message) {
		super(message);
	}
	
	public String getErrorMessage() {
        return getMessage();
    }
	
	public ResponseEntity<String> handleException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorMessage());
    }
}


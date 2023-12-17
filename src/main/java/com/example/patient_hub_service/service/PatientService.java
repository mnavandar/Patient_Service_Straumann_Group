package com.example.patient_hub_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.patient_hub_service.Entity.Patient;
import com.example.patient_hub_service.exception.ResourceNotFound;
import com.example.patient_hub_service.repository.PatientRepository;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    
    @Profile("dev")
    @PostConstruct
    public void devSpecificMethod() {
        System.out.println("Development Environment");
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Patient not found with ID: " + id));
    }

    public List<Patient> getAllPatients() {
        return (List<Patient>) patientRepository.findAll();
    }

    public Patient savePatient(@Valid Patient patient) {
    	System.out.println("madhur"+patient.getFirstName());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}


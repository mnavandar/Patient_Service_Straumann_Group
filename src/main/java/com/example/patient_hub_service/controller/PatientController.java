package com.example.patient_hub_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.patient_hub_service.Entity.Patient;
import com.example.patient_hub_service.exception.ResourceNotFound;
import com.example.patient_hub_service.exception.ValidationExceptionClass;
import com.example.patient_hub_service.service.PatientService;


import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    @Cacheable(value = "patientsCache", key = "#id")
    public ResponseEntity<?> getPatientById(@PathVariable String id) {
    	try {
            Long patientId = Long.parseLong(id);
            System.out.println("Fetching from databases");
            Patient patient = patientService.getPatientById(patientId);
            return ResponseEntity.ok(patient);
        } catch (NumberFormatException ex) {
        	ValidationExceptionClass validationException = new ValidationExceptionClass("ID is invalid; it should be in the form of an integer");
            return validationException.handleException();
        } catch (ResourceNotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorMessage());
        } 
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        Patient existingPatient = patientService.getPatientById(id);

        if (existingPatient != null) {
            existingPatient.setAge(updatedPatient.getAge());
            existingPatient.setFirstName(updatedPatient.getFirstName());
            existingPatient.setLastName(updatedPatient.getLastName());
            existingPatient.setDiseaseName(updatedPatient.getDiseaseName());
            existingPatient.setGender(updatedPatient.getGender());
            existingPatient.setEmailId(updatedPatient.getEmailId());
            Patient savedPatient = patientService.savePatient(existingPatient);
            return new ResponseEntity<>(savedPatient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


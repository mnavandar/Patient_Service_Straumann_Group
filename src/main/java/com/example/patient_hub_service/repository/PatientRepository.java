package com.example.patient_hub_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.patient_hub_service.Entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // additional custom query methods can be declared here
}

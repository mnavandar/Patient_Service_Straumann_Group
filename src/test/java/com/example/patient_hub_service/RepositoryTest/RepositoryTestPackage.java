package com.example.patient_hub_service.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.example.patient_hub_service.PatientHubServiceApplication;
import com.example.patient_hub_service.Entity.Patient;
import com.example.patient_hub_service.repository.PatientRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-dev.properties")
public class RepositoryTestPackage {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testSavePatient() {
        // Create a sample patient
        Patient patient = new Patient();
        patient.setFirstName("Madhur1");
        patient.setLastName("Navandar1");
        patient.setAge(26);
        patient.setGender("Male");
        patient.setEmailId("mnavandar87@gmail.com");
        patient.setDiseaseName("Fever");

        // Save the patient to the repository
        Patient savedPatient = patientRepository.save(patient);

        // Verify that the patient is saved and has an ID assigned
        assertNotNull(savedPatient.getId());
    }

    @Test
    public void testFindPatientById() {
        // Create a sample patient
        Patient patient = new Patient();
        patient.setFirstName("Varun1");
        patient.setLastName("Navandar1");
        patient.setAge(29);
        patient.setGender("Female");
        patient.setEmailId("varunnavandar@gmail.com");
        patient.setDiseaseName("Cough");

        // Save the patient to the repository
        Patient savedPatient = patientRepository.save(patient);

        // Retrieve the patient by ID
        Optional<Patient> retrievedPatient = patientRepository.findById(savedPatient.getId());

        // Verify that the patient is retrieved and has the correct information
        assertTrue(retrievedPatient.isPresent());
        assertEquals("Varun1", retrievedPatient.get().getFirstName());
        assertEquals("Navandar1", retrievedPatient.get().getLastName());
    }
}
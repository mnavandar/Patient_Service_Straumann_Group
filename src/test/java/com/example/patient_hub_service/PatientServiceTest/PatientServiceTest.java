package com.example.patient_hub_service.PatientServiceTest;

import com.example.patient_hub_service.Entity.Patient;
import com.example.patient_hub_service.exception.ResourceNotFound;
import com.example.patient_hub_service.repository.PatientRepository;
import com.example.patient_hub_service.service.PatientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPatientById_ValidId_ReturnsPatient() {
        Long patientId = 1L;
        Patient mockPatient = new Patient();
        mockPatient.setId(patientId);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(mockPatient));

   
        Patient result = patientService.getPatientById(patientId);

   
        assertNotNull(result);
        assertEquals(mockPatient, result);
        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    void getPatientById_InvalidId_ThrowsResourceNotFound() {
      
        Long invalidId = 99L;
        when(patientRepository.findById(invalidId)).thenReturn(Optional.empty());

   
        assertThrows(ResourceNotFound.class, () -> patientService.getPatientById(invalidId));
        verify(patientRepository, times(1)).findById(invalidId);
    }

    @Test
    void getAllPatients_ReturnsListOfPatients() {
     
        List<Patient> mockPatients = Arrays.asList(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(mockPatients);

        
        List<Patient> result = patientService.getAllPatients();

    
        assertNotNull(result);
        assertEquals(mockPatients.size(), result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void savePatient_ValidPatient_ReturnsSavedPatient() {
    
        Patient mockPatient = new Patient();
        when(patientRepository.save(mockPatient)).thenReturn(mockPatient);

   
        Patient result = patientService.savePatient(mockPatient);

  
        assertNotNull(result);
        assertEquals(mockPatient, result);
        verify(patientRepository, times(1)).save(mockPatient);
    }

    @Test
    void deletePatient_ValidId_DeletesPatient() {
   
        Long patientId = 1L;

     
        patientService.deletePatient(patientId);

      
        verify(patientRepository, times(1)).deleteById(patientId);
    }
}


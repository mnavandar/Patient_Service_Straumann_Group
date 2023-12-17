package com.example.patient_hub_service.ControllerTest;

import com.example.patient_hub_service.Entity.Patient;
import com.example.patient_hub_service.controller.PatientController;
import com.example.patient_hub_service.exception.ResourceNotFound;
import com.example.patient_hub_service.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Test
    void getPatientById_ValidId_ReturnsPatient() {
        // Arrange
        Long patientId = 1L;
        Patient mockPatient = new Patient();
        mockPatient.setId(patientId);
        when(patientService.getPatientById(patientId)).thenReturn(mockPatient);

        // Act
        ResponseEntity<?> responseEntity = patientController.getPatientById(patientId.toString());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockPatient, responseEntity.getBody());
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    void getPatientById_InvalidId_ReturnsBadRequest() {
        // Arrange
        String invalidId = "invalidId";

        // Act
        ResponseEntity<?> responseEntity = patientController.getPatientById(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("ID is invalid; it should be in the form of an integer", responseEntity.getBody());
        verify(patientService, never()).getPatientById(anyLong());
    }

    @Test
    void getPatientById_PatientNotFound_ReturnsNotFound() {
        // Arrange
        Long patientId = 1L;
        when(patientService.getPatientById(patientId)).thenThrow(new ResourceNotFound("Patient not found"));

        // Act
        ResponseEntity<?> responseEntity = patientController.getPatientById(patientId.toString());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Patient not found", responseEntity.getBody());
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    void getAllPatients_ReturnsListOfPatients() {
        // Arrange
        List<Patient> mockPatients = Arrays.asList(new Patient(), new Patient());
        when(patientService.getAllPatients()).thenReturn(mockPatients);

        // Act
        ResponseEntity<List<Patient>> responseEntity = patientController.getAllPatients();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockPatients, responseEntity.getBody());
        verify(patientService, times(1)).getAllPatients();
    }
}


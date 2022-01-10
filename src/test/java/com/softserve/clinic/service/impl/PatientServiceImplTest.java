/*
package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.mapper.PatientMapper;
import com.softserve.clinic.model.Patient;
import com.softserve.clinic.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientDto expectedPatientDto;

    @Mock
    private Patient expectedPatient;

    @Mock
    private PatientMapper patientMapper;

    @Test
    void testCorrectCreation() {
        when(patientRepository.save(expectedPatient))
                .thenReturn(expectedPatient);
        PatientDto actualPatient = patientService.createPatient(expectedPatientDto);

        assertEquals(expectedPatientDto, actualPatient);
        verify(patientRepository, never()).save(new Patient());
    }

    @Test
    void testCorrectGetById() {
        when(patientRepository.findById(any()))
                .thenReturn(Optional.of(expectedPatient));
        UUID id = UUID.randomUUID();
        Patient actualPatient = patientService.getPatientById(id);

        assertEquals(expectedPatient, actualPatient);
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    void testGetAll() {
        List<Patient> expectedPatients = List.of(new Patient(), new Patient(), new Patient());

        when(patientRepository.findAll()).thenReturn(expectedPatients);
        List<Patient> actualPatients = patientService.getAllPatients();

        assertEquals(expectedPatients, actualPatients);
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        doNothing().when(patientRepository).deleteById(any());
        patientService.deletePatientById(any());

        verify(patientRepository, times(1)).deleteById(any());
    }

}*/

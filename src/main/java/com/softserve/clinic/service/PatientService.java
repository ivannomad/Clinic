package com.softserve.clinic.service;


import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.model.Patient;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    PatientDto createPatient(PatientDto patientDto);

    List<Patient> getAllPatients();

    Patient getPatientById(UUID id);

    void deletePatientById(UUID id);

}

package com.softserve.clinic.service;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.PatientDto;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    PatientDto createPatient(PatientDto patientDto);

    List<PatientDto> getAllPatients();

    PatientDto getPatientById(UUID patientId);

    void updatePatient(PatientDto patientDto, UUID patientId);

    void deletePatientById(UUID patientId);

    void makeAppointment(UUID patientId, UUID appId);

    List<AppointmentDto> getAllPatientAppointments(UUID patientId);
}

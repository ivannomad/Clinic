package com.softserve.clinic.service;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.PatientDto;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    List<PatientDto> getAllPatients();

    PatientDto getPatientById(UUID patientId);

    PatientDto createPatient(PatientDto patientDto);

    PatientDto updatePatient(PatientDto patientDto, UUID patientId);

    void deletePatientById(UUID patientId);

    List<AppointmentDto> getAllPatientAppointments(UUID patientId);

    AppointmentDto makeAppointment(UUID patientId, UUID appId);

}

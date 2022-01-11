package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.dto.mapper.AppointmentMapper;
import com.softserve.clinic.dto.mapper.PatientMapper;
import com.softserve.clinic.model.Appointment;
import com.softserve.clinic.model.Patient;
import com.softserve.clinic.repository.AppointmentRepository;
import com.softserve.clinic.repository.PatientRepository;
import com.softserve.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::patientToPatientDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDto getPatientById(UUID patientId) {
        return patientRepository.findById(patientId)
                .map(patientMapper::patientToPatientDto)
                .orElseThrow(
                        () -> new EntityNotFoundException("Unable to find Patient with id " + patientId));
    }

    @Override
    public void createPatient(PatientDto patientDto) {
        patientRepository.save(patientMapper.patientDtoToPatient(patientDto));
    }

    @Override
    public void updatePatient(PatientDto patientDto, UUID patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Patient with id " + patientId));
        patientMapper.updatePatientFromPatientDto(patientDto, patient);
        patientRepository.save(patient);
    }

    @Override
    public void deletePatientById(UUID patientId) {
        if (patientRepository.existsById(patientId)) {
            patientRepository.deleteById(patientId);
        } else {
            throw new EntityNotFoundException("Unable to find Patient with id " + patientId);
        }
    }

    @Override
    public void createAppointment(UUID patientId, UUID appId) {
        Appointment appointment = appointmentRepository.findById(appId).orElseThrow();
        appointment.setPatient(patientRepository.getById(patientId));
        appointmentRepository.save(appointment);
    }

/*  @Override
    public void createAppointment(UUID patientId, UUID appId) {
        Appointment appointment = appointmentRepository.findById(appId).orElseThrow(
                () -> new EntityNotFoundException("Could not find appointment: " + appId));
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new EntityNotFoundException("Could not find patient: " + id));
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
    }*/


    @Override
    public List<AppointmentDto> getAllAppointments(UUID patientId) {
        return appointmentRepository.findAppointmentsByPatientId(patientId).stream()
                .map(appointmentMapper::appointmentToAppointmentDto)
                .collect(Collectors.toList());
    }
}

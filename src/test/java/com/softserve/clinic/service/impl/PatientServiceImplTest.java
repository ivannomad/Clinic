package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.dto.mapper.AppointmentMapper;
import com.softserve.clinic.dto.mapper.PatientMapper;
import com.softserve.clinic.model.Appointment;
import com.softserve.clinic.model.Patient;
import com.softserve.clinic.repository.AppointmentRepository;
import com.softserve.clinic.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PatientMapper patientMapper;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AppointmentMapper appointmentMapper;
    @Mock
    private Patient patient;
    @Mock
    private PatientDto patientDto;
    @Mock
    private Appointment appointment;
    @Mock
    private AppointmentDto appointmentDto;
    @InjectMocks
    private PatientServiceImpl patientService;

    private static final UUID PATIENT_ID = UUID.randomUUID();
    private static final UUID APPOINTMENT_ID = UUID.randomUUID();

    @Test
    void shouldGetAllPatients() {
        List<Patient> patientList = List.of(patient);
        List<PatientDto> expected = List.of(patientDto);

        when(patientRepository.findAll()).thenReturn(patientList);
        when(patientMapper.patientToPatientDto(patient)).thenReturn(patientDto);

        List<PatientDto> actual = patientService.getAllPatients();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetPatientById() {
        PatientDto expected = patientDto;

        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));
        when(patientMapper.patientToPatientDto(patient)).thenReturn(patientDto);

        PatientDto actual = patientService.getPatientById(PATIENT_ID);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenPatientNotExist() {
        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.getPatientById(PATIENT_ID)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldCreatePatient() {
        PatientDto expected = patientDto;

        when(patientMapper.patientDtoToPatient(patientDto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.patientToPatientDto(patient)).thenReturn(patientDto);

        PatientDto actual = patientService.createPatient(patientDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldUpdatePatient() {
        PatientDto expected = patientDto;

        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));
        doNothing().when(patientMapper).updatePatientFromPatientDto(patientDto, patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.patientToPatientDto(patient)).thenReturn(patientDto);

        PatientDto actual = patientService.updatePatient(patientDto, PATIENT_ID);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenUpdateNotExistPatient() {
        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.updatePatient(patientDto, PATIENT_ID)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldDeletePatientById() {
        when(patientRepository.existsById(PATIENT_ID)).thenReturn(true);

        patientService.deletePatientById(PATIENT_ID);

        verify(patientRepository, times(1)).deleteById(PATIENT_ID);
    }

    @Test
    void shouldThrowsExceptionWhenDeleteNotExistPatient() {
        when(patientRepository.existsById(PATIENT_ID)).thenReturn(false);

        assertThatThrownBy(() -> patientService.deletePatientById(PATIENT_ID)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldMakeAppointment() {
        AppointmentDto expected = appointmentDto;

        when(appointmentRepository.findAppointmentByIdAndPatientIsNull(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.appointmentToAppointmentDto(appointment)).thenReturn(appointmentDto);

        AppointmentDto actual = patientService.makeAppointment(PATIENT_ID, APPOINTMENT_ID);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenMakeNotExistAppointment() {
        when(appointmentRepository.findAppointmentByIdAndPatientIsNull(APPOINTMENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.makeAppointment(PATIENT_ID, APPOINTMENT_ID)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowsExceptionWhenMakeAppointmentForNotExistPatient() {
        when(appointmentRepository.findAppointmentByIdAndPatientIsNull(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.makeAppointment(PATIENT_ID, APPOINTMENT_ID)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldGetAllPatientAppointments() {
        List<Appointment> appointmentList = List.of(appointment);
        List<AppointmentDto> expected = List.of(appointmentDto);

        when(patientRepository.existsById(PATIENT_ID)).thenReturn(true);
        when(appointmentRepository.findAppointmentsByPatientId(PATIENT_ID)).thenReturn(appointmentList);
        when(appointmentMapper.appointmentToAppointmentDto(appointment)).thenReturn(appointmentDto);

        List<AppointmentDto> actual = patientService.getAllPatientAppointments(PATIENT_ID);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenGetAllAppointmentsForNotExistPatient() {
        when(patientRepository.existsById(PATIENT_ID)).thenReturn(false);

        assertThatThrownBy(() -> patientService.getAllPatientAppointments(PATIENT_ID)).isInstanceOf(EntityNotFoundException.class);
    }
}

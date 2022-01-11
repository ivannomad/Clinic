package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.dto.mapper.AppointmentMapper;
import com.softserve.clinic.dto.mapper.PatientMapper;
import com.softserve.clinic.model.Appointment;
import com.softserve.clinic.model.Patient;
import com.softserve.clinic.repository.AppointmentRepository;
import com.softserve.clinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @InjectMocks
    private PatientServiceImpl patientService;

    private static UUID patientId;
    private static UUID appointmentId;
    private static Patient patient;
    private static PatientDto patientDto;
    private static Appointment appointment;
    private static AppointmentDto appointmentDto;

    @BeforeAll
    static void init() {
        patientId = UUID.randomUUID();
        patient = new Patient();
        patient.setId(patientId);
        patientDto = new PatientDto(patientId, "", "", "", "", "", "", LocalDate.MAX);
        appointmentId = UUID.randomUUID();
        appointment = new Appointment();
        appointment.setId(appointmentId);
        appointmentDto = new AppointmentDto(
                appointmentId,
                LocalDateTime.MAX,
                new DoctorDto(UUID.randomUUID(), "", "", "", "", "", ""),
                patientDto);
    }

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

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientMapper.patientToPatientDto(patient)).thenReturn(patientDto);

        PatientDto actual = patientService.getPatientById(patientId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenPatientNotExist() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.getPatientById(patientId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldCreatePatient() {
        when(patientMapper.patientDtoToPatient(patientDto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);

        patientService.createPatient(patientDto);

        verify(patientMapper, times(1)).patientDtoToPatient(patientDto);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void shouldUpdatePatient() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        doNothing().when(patientMapper).updatePatientFromPatientDto(patientDto, patient);
        when(patientRepository.save(patient)).thenReturn(patient);

        patientService.updatePatient(patientDto, patientId);

        verify(patientRepository, times(1)).findById(patientId);
        verify(patientMapper, times(1)).updatePatientFromPatientDto(patientDto, patient);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void shouldThrowsExceptionWhenUpdateNotExistPatient() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.updatePatient(patientDto, patientId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldDeletePatientById() {
        when(patientRepository.existsById(patientId)).thenReturn(true);

        patientService.deletePatientById(patientId);

        verify(patientRepository, times(1)).deleteById(patientId);
    }

    @Test
    void shouldThrowsExceptionWhenDeleteNotExistPatient() {
        when(patientRepository.existsById(patientId)).thenReturn(false);

        assertThatThrownBy(() -> patientService.deletePatientById(patientId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldMakeAppointment() {
        when(appointmentRepository.findAppointmentByIdAndPatientIsNull(appointmentId)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        patientService.makeAppointment(patientId, appointmentId);

        verify(appointmentRepository, times(1)).findAppointmentByIdAndPatientIsNull(appointmentId);
        verify(patientRepository, times(1)).findById(patientId);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void shouldThrowsExceptionWhenMakeNotExistAppointment() {
        when(appointmentRepository.findAppointmentByIdAndPatientIsNull(appointmentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.makeAppointment(patientId, appointmentId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowsExceptionWhenMakeAppointmentForNotExistPatient(){
        when(appointmentRepository.findAppointmentByIdAndPatientIsNull(appointmentId)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.makeAppointment(patientId, appointmentId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldGetAllPatientAppointments() {
        List<Appointment> appointmentList = List.of(appointment);
        List<AppointmentDto> expected = List.of(appointmentDto);

        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(appointmentRepository.findAppointmentsByPatientId(patientId)).thenReturn(appointmentList);
        when(appointmentMapper.appointmentToAppointmentDto(appointment)).thenReturn(appointmentDto);

        List<AppointmentDto> actual = patientService.getAllPatientAppointments(patientId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenGetAllAppointmentsForNotExistPatient() {
        when(patientRepository.existsById(patientId)).thenReturn(false);

        assertThatThrownBy(() -> patientService.getAllPatientAppointments(patientId)).isInstanceOf(EntityNotFoundException.class);
    }
}

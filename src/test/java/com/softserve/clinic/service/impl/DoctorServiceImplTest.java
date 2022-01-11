package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.dto.mapper.AppointmentMapper;
import com.softserve.clinic.dto.mapper.DoctorMapper;
import com.softserve.clinic.model.Appointment;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.repository.AppointmentRepository;
import com.softserve.clinic.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private DoctorMapper doctorMapper;
    @Mock
    private Doctor doctor;
    @Mock
    private DoctorDto doctorDto;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AppointmentMapper appointmentMapper;
    @Mock
    private AppointmentDto appointmentDto;
    @Mock
    private Appointment appointment;
    @InjectMocks
    private DoctorServiceImpl doctorService;

    private static final UUID DOCTOR_ID = UUID.randomUUID();

    @Test
    void shouldGetAllDoctors() {
        List<Doctor> doctorList = List.of(doctor);
        List<DoctorDto> expected = List.of(doctorDto);

        when(doctorRepository.findAll()).thenReturn(doctorList);
        when(doctorMapper.doctorToDoctorDto(doctor)).thenReturn(doctorDto);

        List<DoctorDto> actual = doctorService.getAllDoctors();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetDoctorById() {
        DoctorDto expected = doctorDto;

        when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
        when(doctorMapper.doctorToDoctorDto(doctor)).thenReturn(expected);

        DoctorDto actual = doctorService.getDoctorById(DOCTOR_ID);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowsExceptionWhenGetDoctorByWrongId() {
        when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> doctorService.getDoctorById(DOCTOR_ID));
    }

    @Test
    void shouldCreateDoctor() {
        DoctorDto expected = doctorDto;

        when(doctorMapper.doctorDtoToDoctor(expected)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        doctorService.createDoctor(expected);

        verify(doctorMapper, times(1)).doctorDtoToDoctor(expected);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void shouldUpdateDoctorById() {
        when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorMapper).updateDoctorFromDoctorDto(doctorDto, doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        doctorService.updateDoctorById(doctorDto, DOCTOR_ID);

        verify(doctorRepository, times(1)).findById(DOCTOR_ID);
        verify(doctorMapper, times(1)).updateDoctorFromDoctorDto(doctorDto, doctor);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void shouldThrowsExceptionWhenUpdateNotExistDoctor() {
        when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> doctorService.updateDoctorById(doctorDto, DOCTOR_ID));
    }

    @Test
    void shouldDeleteDoctorById() {
        when(doctorRepository.existsById(DOCTOR_ID)).thenReturn(true);

        doctorService.deleteDoctorById(DOCTOR_ID);

        verify(doctorRepository, times(1)).deleteById(DOCTOR_ID);
    }

    @Test
    void shouldThrowsExceptionWhenDeleteNotExistDoctor() {
        when(doctorRepository.existsById(DOCTOR_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> doctorService.deleteDoctorById(DOCTOR_ID));
    }

    @Test
    void shouldGetDoctorFreeAppointments() {
        List<Appointment> appointmentList = List.of(appointment);
        List<AppointmentDto> expected = List.of(appointmentDto);

        when(doctorRepository.existsById(DOCTOR_ID)).thenReturn(true);
        when(appointmentRepository.findAppointmentsByDoctorIdAndPatientIsNull(DOCTOR_ID)).thenReturn(appointmentList);
        when(appointmentMapper.appointmentToAppointmentDto(appointment)).thenReturn(appointmentDto);

        List<AppointmentDto> actual = doctorService.getDoctorFreeAppointments(DOCTOR_ID);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowsExceptionWhenGetFreeAppointmentsForNotExistDoctor() {
        when(doctorRepository.existsById(DOCTOR_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> doctorService.getDoctorFreeAppointments(DOCTOR_ID));
    }

    @Test
    void shouldGetDoctorAppointments() {
        List<Appointment> appointmentList = List.of(appointment);
        List<AppointmentDto> expected = List.of(appointmentDto);

        when(doctorRepository.existsById(DOCTOR_ID)).thenReturn(true);
        when(appointmentRepository.findAppointmentsByDoctorIdAndPatientNotNull(DOCTOR_ID)).thenReturn(appointmentList);
        when(appointmentMapper.appointmentToAppointmentDto(appointment)).thenReturn(appointmentDto);

        List<AppointmentDto> actual = doctorService.getDoctorAppointments(DOCTOR_ID);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowsExceptionWhenGetDoctorAppointmentForNotExistDoctor() {
        when(doctorRepository.existsById(DOCTOR_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> doctorService.getDoctorAppointments(DOCTOR_ID));
    }

    @Test
    void shouldCreateAppointment() {
        when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.of(doctor));
        when(appointmentMapper.appointmentDtoToAppointment(appointmentDto)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        doctorService.createAppointment(DOCTOR_ID, appointmentDto);

        verify(doctorRepository, times(1)).findById(DOCTOR_ID);
        verify(appointmentMapper, times(1)).appointmentDtoToAppointment(appointmentDto);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void shouldThrowsExceptionWhenCreateAppointmentToNotExistDoctor(){
        when(doctorRepository.findById(DOCTOR_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> doctorService.createAppointment(DOCTOR_ID, appointmentDto));
    }
}
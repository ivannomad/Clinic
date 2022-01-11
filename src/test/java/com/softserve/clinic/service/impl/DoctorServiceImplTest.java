package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.dto.mapper.AppointmentMapper;
import com.softserve.clinic.dto.mapper.DoctorMapper;
import com.softserve.clinic.model.Appointment;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.repository.AppointmentRepository;
import com.softserve.clinic.repository.DoctorRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    DoctorRepository doctorRepository;
    @Mock
    DoctorMapper doctorMapper;
    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    AppointmentMapper appointmentMapper;
    @InjectMocks
    DoctorServiceImpl testable;

    private static UUID doctorId;
    private static UUID appointmentId;
    private static Doctor doctor;
    private static DoctorDto doctorDto;
    private static Appointment appointment;
    private static AppointmentDto appointmentDto;

    @BeforeAll
    static void init() {
        doctorId = UUID.randomUUID();
        doctor = new Doctor();
        doctor.setId(doctorId);
        doctorDto = new DoctorDto(doctorId, "", "", "", "", "", "");
        appointmentId = UUID.randomUUID();
        appointment = new Appointment();
        appointment.setId(appointmentId);
        appointmentDto = new AppointmentDto(
                appointmentId,
                LocalDateTime.MAX,
                doctorDto,
                new PatientDto(UUID.randomUUID(), "", "", "", "", "", "", LocalDate.MAX));
    }

    @Test
    void shouldGetAllDoctors() {
        List<Doctor> doctorList = List.of(doctor);
        List<DoctorDto> expected = List.of(doctorDto);

        when(doctorRepository.findAll()).thenReturn(doctorList);
        when(doctorMapper.doctorToDoctorDto(doctor)).thenReturn(doctorDto);

        List<DoctorDto> actual = testable.getAllDoctors();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetDoctorById() {
        DoctorDto expected = doctorDto;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorMapper.doctorToDoctorDto(doctor)).thenReturn(expected);

        DoctorDto actual = testable.getDoctorById(doctorId);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowsExceptionWhenGetDoctorByWrongId() {
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> testable.getDoctorById(doctorId));
    }

    @Test
    void shouldCreateDoctor() {
        DoctorDto expected = doctorDto;

        when(doctorMapper.doctorDtoToDoctor(expected)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        testable.createDoctor(expected);

        verify(doctorMapper, times(1)).doctorDtoToDoctor(expected);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void shouldUpdateDoctorById() {
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        doNothing().when(doctorMapper).updateDoctorFromDoctorDto(doctorDto, doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        testable.updateDoctorById(doctorDto, doctorId);

        verify(doctorRepository, times(1)).findById(doctor.getId());
        verify(doctorMapper, times(1)).updateDoctorFromDoctorDto(doctorDto, doctor);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void shouldThrowsExceptionWhenUpdateNotExistDoctor() {
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> testable.updateDoctorById(doctorDto, doctorId));
    }

    @Test
    void shouldDeleteDoctorById() {
        when(doctorRepository.existsById(doctorId)).thenReturn(true);

        testable.deleteDoctorById(doctorId);

        verify(doctorRepository, times(1)).deleteById(doctorId);
    }

    @Test
    void shouldThrowsExceptionWhenDeleteNotExistDoctor() {
        when(doctorRepository.existsById(doctorId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> testable.deleteDoctorById(doctorId));
    }

    @Test
    void shouldGetDoctorFreeAppointments() {
        List<Appointment> appointmentList = List.of(appointment);
        List<AppointmentDto> expected = List.of(appointmentDto);

        when(doctorRepository.existsById(doctorId)).thenReturn(true);
        when(appointmentRepository.findAppointmentsByDoctorIdAndPatientIsNull(doctorId)).thenReturn(appointmentList);
        when(appointmentMapper.appointmentToAppointmentDto(appointment)).thenReturn(appointmentDto);

        List<AppointmentDto> actual = testable.getDoctorFreeAppointments(doctorId);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowsExceptionWhenGetFreeAppointmentsForNotExistDoctor() {
        when(doctorRepository.existsById(doctorId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> testable.getDoctorFreeAppointments(doctorId));
    }

    @Test
    void shouldGetDoctorAppointments() {
        List<Appointment> appointmentList = List.of(appointment);
        List<AppointmentDto> expected = List.of(appointmentDto);

        when(doctorRepository.existsById(doctorId)).thenReturn(true);
        when(appointmentRepository.findAppointmentsByDoctorIdAndPatientNotNull(doctorId)).thenReturn(appointmentList);
        when(appointmentMapper.appointmentToAppointmentDto(appointment)).thenReturn(appointmentDto);

        List<AppointmentDto> actual = testable.getDoctorAppointments(doctorId);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowsExceptionWhenGetDoctorAppointmentForNotExistDoctor() {
        when(doctorRepository.existsById(doctorId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> testable.getDoctorAppointments(doctorId));
    }

    @Test
    void createAppointment() {
        when(doctorRepository.getById(doctorId)).thenReturn(doctor);
        when(appointmentMapper.appointmentDtoToAppointment(appointmentDto)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        testable.createAppointment(doctorId, appointmentDto);

        verify(doctorRepository, times(1)).getById(doctorId);
        verify(appointmentMapper, times(1)).appointmentDtoToAppointment(appointmentDto);
        verify(appointmentRepository, times(1)).save(appointment);
    }
}
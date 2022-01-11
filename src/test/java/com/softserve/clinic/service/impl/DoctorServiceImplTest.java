package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.dto.mapper.DoctorMapper;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeAll;
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
    DoctorRepository doctorRepository;

    @Mock
    DoctorMapper doctorMapper;

    @InjectMocks
    DoctorServiceImpl testable;

    private static UUID doctorId;
    private static String username;
    private static String password;
    private static String firstName;
    private static String secondName;
    private static String email;
    private static String contactNumber;
    private static Doctor doctor;
    private static DoctorDto doctorDto;

    @BeforeAll
    static void init() {
        doctorId = UUID.randomUUID();
        username = "ivannomad";
        password = "password";
        firstName = "Ivan";
        secondName = "Ivanov";
        email = "ivan@mail.com";
        contactNumber = "380501112233";
        doctorDto = new DoctorDto(doctorId, username, password, firstName, secondName, email, contactNumber);
        doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setUsername(username);
        doctor.setPassword(password);
        doctor.setFirstName(firstName);
        doctor.setSecondName(secondName);
        doctor.setEmail(email);
        doctor.setContactNumber(contactNumber);
        doctor.setRole(null);
        doctor.setHospitals(null);
        doctor.setSpecializations(null);
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
}
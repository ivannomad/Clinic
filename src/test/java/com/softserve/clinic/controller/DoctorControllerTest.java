package com.softserve.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private DoctorDto doctorDto;

    @MockBean
    private AppointmentDto appointmentDto;

    // Doctor
    private static final UUID DOCTOR_ID = UUID.randomUUID();
    private static final String USERNAME = "ivannomad";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "Ivan";
    private static final String SECOND_NAME = "Ivanov";
    private static final String EMAIL = "ivan@mail.com";
    private static final String CONTACT_NUMBER = "380501112233";

    // Appointment
    private static final UUID APPOINTMENT_ID = UUID.randomUUID();
    private static final LocalDateTime FUTURE_DATE_AND_TIME = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime PAST_DATE_AND_TIME = LocalDateTime.now().minusDays(1);

    @BeforeEach
    void init() {
        doctorDto = new DoctorDto(
                DOCTOR_ID, USERNAME, PASSWORD, FIRST_NAME, SECOND_NAME, EMAIL, CONTACT_NUMBER);
    }

    @Test
    void shouldGetAllDoctors() throws Exception {
        List<DoctorDto> doctorDtoList = List.of(doctorDto);

        when(doctorService.getAllDoctors()).thenReturn(doctorDtoList);

        mockMvc.perform(get("/doctors"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].id").isNotEmpty(),
                        jsonPath("$[0].username").value(USERNAME),
                        jsonPath("$[0].password").value(PASSWORD),
                        jsonPath("$[0].firstName").value(FIRST_NAME),
                        jsonPath("$[0].secondName").value(SECOND_NAME),
                        jsonPath("$[0].email").value(EMAIL),
                        jsonPath("$[0].contactNumber").value(CONTACT_NUMBER)
                );
    }

    @Test
    void shouldGetDoctorById() throws Exception {
        when(doctorService.getDoctorById(DOCTOR_ID)).thenReturn(doctorDto);

        mockMvc.perform(get("/doctors/{doctorId}", DOCTOR_ID))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.username").value(USERNAME),
                        jsonPath("$.password").value(PASSWORD),
                        jsonPath("$.firstName").value(FIRST_NAME),
                        jsonPath("$.secondName").value(SECOND_NAME),
                        jsonPath("$.email").value(EMAIL),
                        jsonPath("$.contactNumber").value(CONTACT_NUMBER)
                );
    }

    @Test
    void shouldCreateDoctor() throws Exception {
        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest(name = "{index} argument validation")
    @CsvFileSource(resources = "/doctors.csv")
    void creatingFailsWhenDoctorDataIsNotValid(ArgumentsAccessor accessor) throws Exception {
        DoctorDto doctorDto = new DoctorDto(DOCTOR_ID,
                accessor.getString(0),
                accessor.getString(1),
                accessor.getString(2),
                accessor.getString(3),
                accessor.getString(4),
                accessor.getString(5));

        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateDoctorById() throws Exception {
        mockMvc.perform(put("/doctors/{doctorId}", DOCTOR_ID)
                        .content(objectMapper.writeValueAsString(doctorDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest(name = "{index} argument validation")
    @CsvFileSource(resources = "/doctors.csv")
    void updatingFailsWhenDoctorsDataIsNotValid(ArgumentsAccessor accessor) throws Exception {
        DoctorDto doctorDto = new DoctorDto(DOCTOR_ID,
                accessor.getString(0),
                accessor.getString(1),
                accessor.getString(2),
                accessor.getString(3),
                accessor.getString(4),
                accessor.getString(5));

        mockMvc.perform(put("/doctors/{doctorId}", DOCTOR_ID)
                        .content(objectMapper.writeValueAsString(doctorDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteDoctorById() throws Exception {
        mockMvc.perform(delete("/doctors/{doctorId}", DOCTOR_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCreateAppointment() throws Exception {
        AppointmentDto appointmentDto = new AppointmentDto(
                APPOINTMENT_ID, FUTURE_DATE_AND_TIME, doctorDto, null);

        mockMvc.perform(post("/doctors/{doctorId}/appointments", DOCTOR_ID)
                        .content(objectMapper.writeValueAsString(appointmentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void creatingFailsWhenAppointmentsDateIsPast() throws Exception {
        AppointmentDto appointmentDto = new AppointmentDto(
                APPOINTMENT_ID, PAST_DATE_AND_TIME, doctorDto, null);

        mockMvc.perform(post("/doctors/{doctorId}/appointments", DOCTOR_ID)
                        .content(objectMapper.writeValueAsString(appointmentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creatingFailsWhenAppointmentsDateIsNull() throws Exception {
        AppointmentDto appointmentDto = new AppointmentDto(
                APPOINTMENT_ID, null, doctorDto, null);

        mockMvc.perform(post("/doctors/{doctorId}/appointments", DOCTOR_ID)
                        .content(objectMapper.writeValueAsString(appointmentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creatingFailsWhenAppointmentsDoctorIsNull() throws Exception {
        AppointmentDto appointmentDto = new AppointmentDto(
                APPOINTMENT_ID, FUTURE_DATE_AND_TIME, null, null);

        mockMvc.perform(post("/doctors/{doctorId}/appointments", DOCTOR_ID)
                        .content(objectMapper.writeValueAsString(appointmentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getFreeDoctorAppointments() throws Exception {
        List<AppointmentDto> appointmentDtoList = List.of(appointmentDto);

        when(doctorService.getDoctorFreeAppointments(DOCTOR_ID)).thenReturn(appointmentDtoList);

        mockMvc.perform(get("/doctors/{doctorId}/appointments/free", DOCTOR_ID))
                .andExpect(status().isOk());
    }

    @Test
    void getDoctorAppointments() throws Exception {
        List<AppointmentDto> appointmentDtoList = List.of(appointmentDto);

        when(doctorService.getDoctorAppointments(DOCTOR_ID)).thenReturn(appointmentDtoList);

        mockMvc.perform(get("/doctors/{doctorId}/appointments/not-free", DOCTOR_ID))
                .andExpect(status().isOk());
    }
}

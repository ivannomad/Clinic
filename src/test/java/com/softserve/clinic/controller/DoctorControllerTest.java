package com.softserve.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.service.DoctorService;
import org.junit.jupiter.api.BeforeAll;
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
    private AppointmentDto appointmentDto;

    // Doctor
    private static DoctorDto doctorDto;
    private static final UUID DOCTOR_ID = UUID.randomUUID();
    private static final String FIRST_NAME = "Ivan";
    private static final String SECOND_NAME = "Ivanov";
    private static final String CONTACT_NUMBER = "380501112233";

    // Appointment
    private static final UUID APPOINTMENT_ID = UUID.randomUUID();
    private static final LocalDateTime FUTURE_DATE_AND_TIME = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime PAST_DATE_AND_TIME = LocalDateTime.now().minusDays(1);

    @BeforeAll
    static void init() {
        doctorDto = new DoctorDto(
                DOCTOR_ID, FIRST_NAME, SECOND_NAME, CONTACT_NUMBER);
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
                        jsonPath("$[0].firstName").value(FIRST_NAME),
                        jsonPath("$[0].secondName").value(SECOND_NAME),
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
                        jsonPath("$.firstName").value(FIRST_NAME),
                        jsonPath("$.secondName").value(SECOND_NAME),
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
        DoctorDto doctorDto = new DoctorDto(
                UUID.fromString(accessor.getString(0)),
                accessor.getString(1),
                accessor.getString(2),
                accessor.getString(3));

        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creatingFailsWhenDoctorIdIsNull() throws Exception {
        DoctorDto doctorDto = new DoctorDto(
                null,
                FIRST_NAME,
                SECOND_NAME,
                CONTACT_NUMBER);

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
        DoctorDto doctorDto = new DoctorDto(
                UUID.fromString(accessor.getString(0)),
                accessor.getString(1),
                accessor.getString(2),
                accessor.getString(3));

        mockMvc.perform(put("/doctors/{doctorId}", DOCTOR_ID)
                        .content(objectMapper.writeValueAsString(doctorDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatingFailsWhenDoctorIdIsNull() throws Exception {
        DoctorDto doctorDto = new DoctorDto(
                null,
                FIRST_NAME,
                SECOND_NAME,
                CONTACT_NUMBER);

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
    void shouldGetFreeDoctorAppointments() throws Exception {
        List<AppointmentDto> appointmentDtoList = List.of(appointmentDto);

        when(doctorService.getDoctorFreeAppointments(DOCTOR_ID)).thenReturn(appointmentDtoList);

        mockMvc.perform(get("/doctors/{doctorId}/appointments?free=true", DOCTOR_ID))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetDoctorAppointments() throws Exception {
        List<AppointmentDto> appointmentDtoList = List.of(appointmentDto);

        when(doctorService.getDoctorAppointments(DOCTOR_ID)).thenReturn(appointmentDtoList);

        mockMvc.perform(get("/doctors/{doctorId}/appointments?free=false", DOCTOR_ID))
                .andExpect(status().isOk());
    }
}

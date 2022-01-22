package com.softserve.clinic.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.service.PatientService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @MockBean
    private PatientService patientService;
    @MockBean
    private PatientDto patientDto;
    @MockBean
    private AppointmentDto appointmentDto;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    // Patient
    private static final UUID PATIENT_ID = UUID.randomUUID();
    private static final String FIRST_NAME = "Ivan";
    private static final String SECOND_NAME = "Ivanov";
    private static final String CONTACT_NUMBER = "380501112233";
    private static final LocalDate BIRTH_DATE = LocalDate.parse("1960-01-01");

    private static final UUID APPOINTMENT_ID = UUID.randomUUID();

    @BeforeEach
    void init() {
        patientDto = new PatientDto(
                PATIENT_ID, FIRST_NAME, SECOND_NAME, CONTACT_NUMBER, BIRTH_DATE);
    }

    @Test
    void shouldGetAllPatients() throws Exception {
        List<PatientDto> expected = List.of(patientDto);

        when(patientService.getAllPatients()).thenReturn(expected);

        String response = mockMvc.perform(get("/patients"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PatientDto> actual = objectMapper.readValue(response, new TypeReference<>() {
        });

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetPatientById() throws Exception {
        PatientDto expected = patientDto;

        when(patientService.getPatientById(PATIENT_ID)).thenReturn(patientDto);

        String response = mockMvc.perform(get("/patients/{patientId}", PATIENT_ID))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PatientDto actual = objectMapper.readValue(response, PatientDto.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenDataIsValidShouldCreatePatient() throws Exception {
        when(patientService.createPatient(patientDto)).thenReturn(patientDto);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest(name = "{index} argument validation")
    @CsvFileSource(resources = "/patients.csv")
    void creatingFailsWhenDataIsNotValid(ArgumentsAccessor argumentsAccessor) throws Exception {
        PatientDto patient = new PatientDto(
                UUID.fromString(argumentsAccessor.getString(0)),
                argumentsAccessor.getString(1),
                argumentsAccessor.getString(2),
                argumentsAccessor.getString(3),
                argumentsAccessor.get(4, LocalDate.class));

        String request = objectMapper.writeValueAsString(patient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDataIsValidShouldUpdatePatient() throws Exception {
        mockMvc.perform(put("/patients/{patientId}", PATIENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest(name = "{index} argument validation")
    @CsvFileSource(resources = "/patients.csv")
    void updatingFailsWhenDataIsNotValid(ArgumentsAccessor argumentsAccessor) throws Exception {
        PatientDto patient = new PatientDto(
                UUID.fromString(argumentsAccessor.getString(0)),
                argumentsAccessor.getString(1),
                argumentsAccessor.getString(2),
                argumentsAccessor.getString(3),
                argumentsAccessor.get(4, LocalDate.class));

        String request = objectMapper.writeValueAsString(patient);

        mockMvc.perform(put("/patients/{patientId}", PATIENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeletePatientById() throws Exception {
        mockMvc.perform(delete("/patients/{patientId}", PATIENT_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldMakeAppointment() throws Exception {
        AppointmentDto expected = appointmentDto;

        when(patientService.makeAppointment(PATIENT_ID, APPOINTMENT_ID)).thenReturn(appointmentDto);

        String response = mockMvc.perform(post("/patients/{patientId}/appointments/{appId}", PATIENT_ID, APPOINTMENT_ID))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AppointmentDto actual = objectMapper.readValue(response, AppointmentDto.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetAllAppointments() throws Exception {
        List<AppointmentDto> expected = List.of(appointmentDto);

        when(patientService.getAllPatientAppointments(PATIENT_ID)).thenReturn(expected);

        String response = mockMvc.perform(get("/patients/{patientId}/appointments", PATIENT_ID))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<AppointmentDto> actual = objectMapper.readValue(response, new TypeReference<>() {
        });

        assertThat(actual).isEqualTo(expected);
    }
}
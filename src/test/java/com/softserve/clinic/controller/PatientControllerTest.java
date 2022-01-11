package com.softserve.clinic.controller;

import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @MockBean
    private PatientService patientService;

    @Autowired
    private MockMvc mockMvc;

    private static final UUID ID = UUID.randomUUID();
    private static final String USERNAME = "ivannomad";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "Ivan";
    private static final String SECOND_NAME = "Ivanov";
    private static final String EMAIL = "ivan@mail.com";
    private static final String CONTACT_NUMBER = "380501112233";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1960, 01, 01);
    private static final String FORMATTED_BIRTH_DATE = BIRTH_DATE.format(ofPattern("yyyy-MM-dd"));

    @Test
    void shouldGetAllPatients() throws Exception {
        List<PatientDto> patientDtoList = List.of(new PatientDto(
                ID, USERNAME, PASSWORD, FIRST_NAME, SECOND_NAME, EMAIL, CONTACT_NUMBER, BIRTH_DATE));

        when(patientService.getAllPatients()).thenReturn(patientDtoList);

        mockMvc.perform(get("/patients"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].id").isNotEmpty(),
                        jsonPath("$[0].username").value(USERNAME),
                        jsonPath("$[0].password").value(PASSWORD),
                        jsonPath("$[0].firstName").value(FIRST_NAME),
                        jsonPath("$[0].secondName").value(SECOND_NAME),
                        jsonPath("$[0].email").value(EMAIL),
                        jsonPath("$[0].contactNumber").value(CONTACT_NUMBER),
                        jsonPath("$[0].birthDate").value(FORMATTED_BIRTH_DATE)
                );
    }

    @Test
    void shouldGetPatientById() throws Exception {
        PatientDto patientDto = new PatientDto(
                ID, USERNAME, PASSWORD, FIRST_NAME, SECOND_NAME, EMAIL, CONTACT_NUMBER, BIRTH_DATE);

        when(patientService.getPatientById(ID)).thenReturn(patientDto);

        mockMvc.perform(get("/patients/{id}", ID))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.username").value(USERNAME),
                        jsonPath("$.password").value(PASSWORD),
                        jsonPath("$.firstName").value(FIRST_NAME),
                        jsonPath("$.secondName").value(SECOND_NAME),
                        jsonPath("$.email").value(EMAIL),
                        jsonPath("$.contactNumber").value(CONTACT_NUMBER),
                        jsonPath("$.birthDate").value(FORMATTED_BIRTH_DATE)
                );
    }

    @Test
    void shouldCreatePatient() throws Exception {
        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ivannomad\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"ivan@mail.com\"," +
                                "\"contactNumber\":\"38051112233\"," +
                                "\"birthDate\":\"1960-01-01\"" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void creatingFailsWhenPatientUsernameIsBlank() throws Exception {
        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"ivan@mail.com\"," +
                                "\"contactNumber\":\"38051112233\"," +
                                "\"birthDate\":\"1960-01-01\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creatingFailsWhenPatientEmailIsNotValid() throws Exception {
        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ivannomad\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"email\"," +
                                "\"contactNumber\":\"38051112233\"," +
                                "\"birthDate\":\"1960-01-01\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        mockMvc.perform(put("/patients/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ivannomad\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"ivan@mail.com\"," +
                                "\"contactNumber\":\"38051112233\"," +
                                "\"birthDate\":\"1960-01-01\"" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void updatingFailsWhenPatientUsernameIsBlank() throws Exception {
        mockMvc.perform(put("/patients/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\"," +
                                "\"password\":\"\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"ivan@mail.com\"," +
                                "\"contactNumber\":\"38051112233\"," +
                                "\"birthDate\":\"1960-01-01\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatingFailsWhenPatientEmailIsNotValid() throws Exception {
        mockMvc.perform(put("/patients/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ivannomad\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"email\"," +
                                "\"contactNumber\":\"38051112233\"," +
                                "\"birthDate\":\"1960-01-01\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeletePatientById() throws Exception {
        mockMvc.perform(delete("/patients/{id}", ID))
                .andExpect(status().isNoContent());
    }
}
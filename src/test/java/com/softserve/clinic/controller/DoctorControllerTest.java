package com.softserve.clinic.controller;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @MockBean
    private DoctorService doctorService;
    @MockBean
    private AppointmentDto appointmentDto;

    @Autowired
    private MockMvc mockMvc;

    private static final UUID ID = UUID.randomUUID();
    private static final String USERNAME = "ivannomad";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "Ivan";
    private static final String SECOND_NAME = "Ivanov";
    private static final String EMAIL = "ivan@mail.com";
    private static final String CONTACT_NUMBER = "380501112233";

    @Test
    void shouldGetAllDoctors() throws Exception {
        List<DoctorDto> doctorDtoList = List.of(new DoctorDto(
                ID, USERNAME, PASSWORD, FIRST_NAME, SECOND_NAME, EMAIL, CONTACT_NUMBER));

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
        DoctorDto doctorDto = new DoctorDto(
                ID, USERNAME, PASSWORD, FIRST_NAME, SECOND_NAME, EMAIL, CONTACT_NUMBER);

        when(doctorService.getDoctorById(ID)).thenReturn(doctorDto);

        mockMvc.perform(get("/doctors/{doctorId}", ID))
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ivannomad\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"ivan@mail.com\"," +
                                "\"contactNumber\":\"38051112233\"" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void creatingFailsWhenDoctorUsernameIsBlank() throws Exception {
        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"ivan@mail.com\"," +
                                "\"contactNumber\":\"38051112233\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creatingFailsWhenDoctorEmailIsNotValid() throws Exception {
        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ivannomad\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"email\"," +
                                "\"contactNumber\":\"38051112233\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateDoctorById() throws Exception {
        mockMvc.perform(put("/doctors/{doctorId}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ivannomad\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"ivan@mail.com\"," +
                                "\"contactNumber\":\"38051112233\"" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void updatingFailsWhenDoctorUsernameIsBlank() throws Exception {
        mockMvc.perform(put("/doctors/{doctorId}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\"," +
                                "\"password\":\"password\"," +
                                "\"firstName\":\"Ivan\"," +
                                "\"secondName\":\"Ivanov\"," +
                                "\"email\":\"ivan@mail.com\"," +
                                "\"contactNumber\":\"38051112233\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteDoctorById() throws Exception {
        mockMvc.perform(delete("/doctors/{doctorId}", ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCreateAppointment() throws Exception {
        mockMvc.perform(post("/doctors/{doctorId}/appointments", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateAndTime\":\"2999-01-09T20:45:08.3408987\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void creatingFailsWhenDateIsPast() throws Exception {
        mockMvc.perform(post("/doctors/{doctorId}/appointments", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateAndTime\":\"1000-01-09T20:45:08.3408987\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void creatingFailsWhenDateIsNull() throws Exception {
        mockMvc.perform(post("/doctors/{doctorId}/appointments", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateAndTime\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getFreeDoctorAppointments() throws Exception {
        List<AppointmentDto> appointmentDtoList = List.of(appointmentDto);

        when(doctorService.getDoctorFreeAppointments(ID)).thenReturn(appointmentDtoList);

        mockMvc.perform(get("/doctors/{doctorId}/appointments/free", ID))
                .andExpect(status().isOk());
    }

    @Test
    void getDoctorAppointments() throws Exception {
        List<AppointmentDto> appointmentDtoList = List.of(appointmentDto);

        when(doctorService.getDoctorAppointments(ID)).thenReturn(appointmentDtoList);

        mockMvc.perform(get("/doctors/{doctorId}/appointments/not-free", ID))
                .andExpect(status().isOk());
    }
}

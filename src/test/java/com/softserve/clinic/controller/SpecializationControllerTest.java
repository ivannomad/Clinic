package com.softserve.clinic.controller;

import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.service.SpecializationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpecializationController.class)
class SpecializationControllerTest {

    @MockBean
    private SpecializationService specializationService;

    @Autowired
    private MockMvc mockMvc;

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Surgeon";
    private static final String DESCRIPTION = "Description";

    @Test
    void shouldGetAllSpecializations() throws Exception {
        List<SpecializationDto> specializationDtoList = List.of(new SpecializationDto(ID, NAME, DESCRIPTION));

        when(specializationService.getAllSpecializations()).thenReturn(specializationDtoList);

        mockMvc.perform(get("/specializations"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].id").isNotEmpty(),
                        jsonPath("$[0].name").value(NAME),
                        jsonPath("$[0].description").value(DESCRIPTION)
                );
    }

    @Test
    void shouldGetSpecializationByName() throws Exception {
        SpecializationDto specializationDto = new SpecializationDto(ID, NAME, DESCRIPTION);

        when(specializationService.getSpecializationByName(NAME)).thenReturn(specializationDto);

        mockMvc.perform(get("/specializations/{specName}", NAME))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.name").value(NAME),
                        jsonPath("$.description").value(DESCRIPTION)
                );
    }

    @Test
    void shouldThrowsExceptionWhenEntityNotFound() throws Exception {
        when(specializationService.getSpecializationByName(NAME)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/specializations/{specName}", NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateSpecialization() throws Exception {
        mockMvc.perform(post("/specializations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"Surgeon\"," +
                                "\"description\":\"Description\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void creatingFailsWhenSpecializationNameNotGiven() throws Exception {
        mockMvc.perform(post("/specializations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":,"+
                                "\"description\":\"Description\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateSpecializationById() throws Exception {
        mockMvc.perform(put("/specializations/{specId}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"Surgeon\"," +
                                 "\"description\":\"Description\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updatingFailsWhenSpecializationNameNotGiven() throws Exception {
        mockMvc.perform(put("/specializations/{specId}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Surgeon\"},"+
                                "{\"description\":}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldDeleteSpecialization() throws Exception {
        mockMvc.perform(delete("/specializations/{specId}", ID))
                .andExpect(status().isNoContent());
    }
}
package com.softserve.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.service.SpecializationService;
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

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpecializationController.class)
class SpecializationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SpecializationService specializationService;

    private static SpecializationDto specDto;
    private static final UUID SPEC_ID = UUID.randomUUID();
    private static final String NAME = "Surgeon";
    private static final String DESCRIPTION = "Description";

    @BeforeAll
    static void init() {
        specDto = new SpecializationDto(SPEC_ID, NAME, DESCRIPTION);
    }

    @Test
    void shouldGetAllSpecializations() throws Exception {
        List<SpecializationDto> specializationDtoList = List.of(new SpecializationDto(SPEC_ID, NAME, DESCRIPTION));

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
        SpecializationDto specializationDto = new SpecializationDto(SPEC_ID, NAME, DESCRIPTION);

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
                        .content(objectMapper.writeValueAsString(specDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void creatingFailsWhenSpecializationIdIsNull() throws Exception {
        SpecializationDto specDto = new SpecializationDto(null, NAME, DESCRIPTION);

        mockMvc.perform(post("/specializations")
                        .content(objectMapper.writeValueAsString(specDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest(name = "{index} argument validation")
    @CsvFileSource(resources = "/specialization.csv")
    void creatingFailsWhenSpecializationDataIsNotValid(ArgumentsAccessor accessor) throws Exception {
        SpecializationDto specDto = new SpecializationDto(
                UUID.fromString(accessor.getString(0)),
                accessor.getString(1),
                accessor.getString(2));

        mockMvc.perform(post("/specializations")
                        .content(objectMapper.writeValueAsString(specDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateSpecializationById() throws Exception {
        mockMvc.perform(put("/specializations/{specId}", SPEC_ID)
                        .content(objectMapper.writeValueAsString(specDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updatingFailsWhenSpecializationIdIsNull() throws Exception {
        SpecializationDto specDto = new SpecializationDto(null, NAME, DESCRIPTION);

        mockMvc.perform(put("/specializations/{specId}", SPEC_ID)
                        .content(objectMapper.writeValueAsString(specDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest(name = "{index} argument validation")
    @CsvFileSource(resources = "/specialization.csv")
    void updatingFailsWhenSpecializationDataIsNotValid(ArgumentsAccessor accessor) throws Exception {
        SpecializationDto specDto = new SpecializationDto(
                UUID.fromString(accessor.getString(0)),
                accessor.getString(1),
                accessor.getString(2));

        mockMvc.perform(put("/specializations/{specId}", SPEC_ID)
                        .content(objectMapper.writeValueAsString(specDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldDeleteSpecialization() throws Exception {
        mockMvc.perform(delete("/specializations/{specId}", SPEC_ID))
                .andExpect(status().isNoContent());
    }
}
package com.softserve.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.softserve.clinic.dto.HospitalDto;
import com.softserve.clinic.service.HospitalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HospitalController.class)
class HospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HospitalService hospitalService;

    @Autowired
    private ObjectMapper objectMapper;

    public static final UUID ID = UUID.randomUUID();
    public static final String HOSPITAL_NAME = "OnClinic";
    public static final String CITY = "Kharkiv";
    public static final String STREET = "Yaroslava Mudrogo";
    public static final String ADDRESS_NUMBER = "52";

    @Test
    void shouldGetAllHospitals() throws Exception {
        List<HospitalDto> hospitalDtoList = List.of(new HospitalDto(ID, HOSPITAL_NAME, CITY, STREET, ADDRESS_NUMBER));

        when(hospitalService.getAllHospitals()).thenReturn(hospitalDtoList);

        mockMvc.perform(get("/hospitals"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$[0].id").isNotEmpty(),
                        jsonPath("$[0].hospitalName").value(HOSPITAL_NAME),
                        jsonPath("$[0].city").value(CITY),
                        jsonPath("$[0].street").value(STREET),
                        jsonPath("$[0].addressNumber").value(ADDRESS_NUMBER)
                );
    }


    @Test
    void shouldGetHospitalById() throws Exception {
        ObjectReader objectReader = objectMapper.readerFor(HospitalDto.class);
        HospitalDto expected = new HospitalDto(ID, HOSPITAL_NAME, CITY, STREET, ADDRESS_NUMBER);

        when(hospitalService.getHospitalById(ID)).thenReturn(expected);

        String result = mockMvc.perform(get("/hospitals/{id}", ID))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        HospitalDto actual = objectReader.readValue(result);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void shouldCreateHospital() throws Exception {

        ObjectWriter objectWriter = objectMapper.writerFor(HospitalDto.class);
        HospitalDto hospitalDto = new HospitalDto(ID, HOSPITAL_NAME, CITY, STREET, ADDRESS_NUMBER);

        when(hospitalService.createHospital(hospitalDto)).thenReturn(hospitalDto);

        String jsonRequest = objectWriter.writeValueAsString(hospitalDto);

        mockMvc.perform(post("/hospitals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }


}
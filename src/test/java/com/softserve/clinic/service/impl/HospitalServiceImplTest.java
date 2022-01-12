package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.HospitalDto;
import com.softserve.clinic.dto.mapper.HospitalMapper;
import com.softserve.clinic.model.Hospital;
import com.softserve.clinic.repository.HospitalRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HospitalServiceImplTest {

    @Mock
    HospitalRepository hospitalRepository;

    @Mock
    HospitalMapper hospitalMapper;

    @InjectMocks
    HospitalServiceImpl hospitalService;

    private static UUID hospitalId;
    private static String hospitalName;
    private static Hospital hospital;
    private static HospitalDto hospitalDto;

    @BeforeAll
    static void init() {
        hospital = new Hospital();
        hospitalId = UUID.randomUUID();
        hospitalName = "OnClinic";
        hospital.setId(hospitalId);
        hospital.setName(hospitalName);
        hospitalDto = new HospitalDto(hospitalId, hospitalName, "", "", "");
    }

    @Test
    void shouldCreateHospital() {
        HospitalDto expected = hospitalDto;

        when(hospitalMapper.hospitalDtoToHospital(expected)).thenReturn(hospital);
        when(hospitalRepository.save(hospital)).thenReturn(hospital);

        hospitalService.createHospital(expected);

        verify(hospitalMapper, times(1)).hospitalDtoToHospital(expected);
        verify(hospitalRepository, times(1)).save(hospital);
    }

    @Test
    void shouldUpdateHospital() {
        when(hospitalRepository.findById(hospital.getId())).thenReturn(Optional.of(hospital));
        doNothing().when(hospitalMapper).updateHospitalFromHospitalDto(hospitalDto, hospital);
        when(hospitalRepository.save(hospital)).thenReturn(hospital);

        hospitalService.updateHospital(hospitalDto, hospitalId);

        verify(hospitalRepository, times(1)).findById(hospitalId);
        verify(hospitalMapper, times(1)).updateHospitalFromHospitalDto(hospitalDto, hospital);
        verify(hospitalRepository, times(1)).save(hospital);
    }

    @Test
    void shouldDeleteHospitalById() {
        when(hospitalRepository.existsById(hospitalId)).thenReturn(true);

        hospitalService.deleteHospitalById(hospitalId);

        verify(hospitalRepository, times(1)).deleteById(hospitalId);
    }

    @Test
    void shouldThrowExceptionWhenDeleteHospitalIdDoesNotExist() {
        when(hospitalRepository.existsById(hospitalId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> hospitalService.deleteHospitalById(hospitalId));
    }

    @Test
    void shouldGetAllHospitals() {
        List<Hospital> hospitalList = List.of(hospital);
        List<HospitalDto> expected = List.of(hospitalDto);

        when(hospitalRepository.findAll()).thenReturn(hospitalList);
        when(hospitalMapper.hospitalToHospitalDto(hospital)).thenReturn(hospitalDto);

        List<HospitalDto> actual = hospitalService.getAllHospitals();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetHospitalById() {
        HospitalDto expected = hospitalDto;

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));
        when(hospitalMapper.hospitalToHospitalDto(hospital)).thenReturn(expected);

        HospitalDto actual = hospitalService.getHospitalById(hospitalId);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenHospitalIdIsNotCorrect() {
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> hospitalService.getHospitalById(hospitalId));
    }

    @Test
    void shouldGetHospitalByName() {
        HospitalDto expected = hospitalDto;

        when(hospitalRepository.findByName(hospitalName)).thenReturn(Optional.of(hospital));
        when(hospitalMapper.hospitalToHospitalDto(hospital)).thenReturn(expected);

        HospitalDto actual = hospitalService.getHospitalByName(hospitalName);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenHospitalNameIsNotCorrect() {
        when(hospitalRepository.findByName(hospitalName)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> hospitalService.getHospitalByName(hospitalName));
    }


}
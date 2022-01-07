package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.mapper.SpecializationMapper;
import com.softserve.clinic.model.Specialization;
import com.softserve.clinic.repository.SpecializationRepository;
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
class SpecializationServiceImplTest {

    @Mock
    SpecializationRepository specializationRepository;
    @Mock
    SpecializationMapper specializationMapper;
    @InjectMocks
    SpecializationServiceImpl testable;

    private static UUID specId;
    private static String specName;
    private static Specialization specialization;
    private static SpecializationDto specializationDto;

    @BeforeAll
    static void init() {
        specId = UUID.randomUUID();
        specName = "Surgeon";
        specializationDto = new SpecializationDto(specId, specName, "description");
        specialization = new Specialization();
        specialization.setId(specId);
        specialization.setName(specName);
    }

    @Test
    void shouldGetAllSpecializations() {
        List<Specialization> specializationList = List.of(specialization);
        List<SpecializationDto> expected = List.of(specializationDto);

        when(specializationRepository.findAll()).thenReturn(specializationList);
        when(specializationMapper.specToSpecDto(specialization)).thenReturn(specializationDto);

        List<SpecializationDto> actual = testable.getAllSpecializations();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetSpecializationById() {
        SpecializationDto expected = specializationDto;

        when(specializationRepository.findById(specId)).thenReturn(Optional.of(specialization));
        when(specializationMapper.specToSpecDto(specialization)).thenReturn(expected);

        SpecializationDto actual = testable.getSpecializationById(specId);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowsExceptionWhenGetSpecializationByWrongId() {
        when(specializationRepository.findById(specId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> testable.getSpecializationById(specId));
    }

    @Test
    void shouldGetSpecializationByName() {
        SpecializationDto expected = specializationDto;

        when(specializationRepository.findByName(specName)).thenReturn(Optional.of(specialization));
        when(specializationMapper.specToSpecDto(specialization)).thenReturn(expected);

        SpecializationDto actual = testable.getSpecializationByName(specName);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowsExceptionWhenGetSpecializationByWrongName() {
        when(specializationRepository.findByName(specName)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> testable.getSpecializationByName(specName));
    }

    @Test
    void shouldCreateSpecialization() {
        SpecializationDto expected = specializationDto;

        when(specializationMapper.specDtoToSpec(expected)).thenReturn(specialization);
        when(specializationRepository.save(specialization)).thenReturn(specialization);

        testable.createSpecialization(expected);

        verify(specializationMapper, times(1)).specDtoToSpec(expected);
        verify(specializationRepository, times(1)).save(specialization);
    }

    @Test
    void shouldUpdateSpecialization() {
        when(specializationMapper.specDtoToSpec(specializationDto)).thenReturn(specialization);
        when(specializationRepository.findById(specialization.getId())).thenReturn(Optional.of(specialization));
        when(specializationRepository.save(specialization)).thenReturn(specialization);

        testable.updateSpecialization(specializationDto, specId);

        verify(specializationMapper, times(1)).specDtoToSpec(specializationDto);
        verify(specializationRepository, times(1)).findById(specialization.getId());
        verify(specializationRepository, times(1)).save(specialization);
    }

    @Test
    void shouldThrowsExceptionWhenUpdateNotExistSpecialization() {
        SpecializationDto expected = specializationDto;

        when(specializationMapper.specDtoToSpec(expected)).thenReturn(specialization);
        when(specializationRepository.findById(specId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> testable.updateSpecialization(expected, specId));
    }

    @Test
    void shouldDeleteSpecialization() {
        when(specializationRepository.existsById(specId)).thenReturn(true);

        testable.deleteSpecialization(specId);

        verify(specializationRepository, times(1)).deleteById(specId);
    }

    @Test
    void shouldThrowsExceptionWhenDeleteNotExistSpecialization() {
        when(specializationRepository.existsById(specId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> testable.deleteSpecialization(specId));
    }
}
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetSpecializationById() {
        SpecializationDto expected = specializationDto;

        when(specializationRepository.findById(specId)).thenReturn(Optional.of(specialization));
        when(specializationMapper.specToSpecDto(specialization)).thenReturn(expected);

        SpecializationDto actual = testable.getSpecializationById(specId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenGetSpecializationByWrongId() {
        when(specializationRepository.findById(specId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testable.getSpecializationById(specId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldGetSpecializationByName() {
        SpecializationDto expected = specializationDto;

        when(specializationRepository.findByName(specName)).thenReturn(Optional.of(specialization));
        when(specializationMapper.specToSpecDto(specialization)).thenReturn(expected);

        SpecializationDto actual = testable.getSpecializationByName(specName);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenGetSpecializationByWrongName() {
        when(specializationRepository.findByName(specName)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testable.getSpecializationByName(specName)).isInstanceOf(EntityNotFoundException.class);
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
        when(specializationRepository.findById(specialization.getId())).thenReturn(Optional.of(specialization));
        doNothing().when(specializationMapper).updateSpecializationFromSpecializationDto(specializationDto, specialization);
        when(specializationRepository.save(specialization)).thenReturn(specialization);

        testable.updateSpecialization(specializationDto, specId);

        verify(specializationRepository, times(1)).findById(specialization.getId());
        verify(specializationMapper, times(1)).updateSpecializationFromSpecializationDto(specializationDto, specialization);
        verify(specializationRepository, times(1)).save(specialization);
    }

    @Test
    void shouldThrowsExceptionWhenUpdateNotExistSpecialization() {
        when(specializationRepository.findById(specId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testable.updateSpecialization(specializationDto, specId)).isInstanceOf(EntityNotFoundException.class);
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

        assertThatThrownBy(() -> testable.deleteSpecialization(specId)).isInstanceOf(EntityNotFoundException.class);
    }
}
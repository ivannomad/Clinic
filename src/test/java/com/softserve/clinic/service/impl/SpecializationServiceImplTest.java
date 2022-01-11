package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.dto.mapper.SpecializationMapper;
import com.softserve.clinic.model.Specialization;
import com.softserve.clinic.repository.SpecializationRepository;
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
    private SpecializationRepository specializationRepository;
    @Mock
    private SpecializationMapper specializationMapper;
    @Mock
    private Specialization specialization;
    @Mock
    private SpecializationDto specializationDto;
    @InjectMocks
    private SpecializationServiceImpl specializationService;

    private static final UUID SPEC_ID = UUID.randomUUID();

    @Test
    void shouldGetAllSpecializations() {
        List<Specialization> specializationList = List.of(specialization);
        List<SpecializationDto> expected = List.of(specializationDto);

        when(specializationRepository.findAll()).thenReturn(specializationList);
        when(specializationMapper.specToSpecDto(specialization)).thenReturn(specializationDto);

        List<SpecializationDto> actual = specializationService.getAllSpecializations();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetSpecializationById() {
        SpecializationDto expected = specializationDto;

        when(specializationRepository.findById(SPEC_ID)).thenReturn(Optional.of(specialization));
        when(specializationMapper.specToSpecDto(specialization)).thenReturn(expected);

        SpecializationDto actual = specializationService.getSpecializationById(SPEC_ID);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenGetSpecializationByWrongId() {
        when(specializationRepository.findById(SPEC_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> specializationService.getSpecializationById(SPEC_ID)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldGetSpecializationByName() {
        SpecializationDto expected = specializationDto;

        when(specializationRepository.findByName(anyString())).thenReturn(Optional.of(specialization));
        when(specializationMapper.specToSpecDto(specialization)).thenReturn(expected);

        SpecializationDto actual = specializationService.getSpecializationByName(anyString());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionWhenGetSpecializationByWrongName() {
        when(specializationRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> specializationService.getSpecializationByName("name")).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldCreateSpecialization() {
        when(specializationMapper.specDtoToSpec(specializationDto)).thenReturn(specialization);
        when(specializationRepository.save(specialization)).thenReturn(specialization);

        specializationService.createSpecialization(specializationDto);

        verify(specializationMapper, times(1)).specDtoToSpec(specializationDto);
        verify(specializationRepository, times(1)).save(specialization);
    }

    @Test
    void shouldUpdateSpecialization() {
        when(specializationRepository.findById(SPEC_ID)).thenReturn(Optional.of(specialization));
        doNothing().when(specializationMapper).updateSpecializationFromSpecializationDto(specializationDto, specialization);
        when(specializationRepository.save(specialization)).thenReturn(specialization);

        specializationService.updateSpecialization(specializationDto, SPEC_ID);

        verify(specializationRepository, times(1)).findById(SPEC_ID);
        verify(specializationMapper, times(1)).updateSpecializationFromSpecializationDto(specializationDto, specialization);
        verify(specializationRepository, times(1)).save(specialization);
    }

    @Test
    void shouldThrowsExceptionWhenUpdateNotExistSpecialization() {
        when(specializationRepository.findById(SPEC_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> specializationService.updateSpecialization(specializationDto, SPEC_ID)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldDeleteSpecialization() {
        when(specializationRepository.existsById(SPEC_ID)).thenReturn(true);

        specializationService.deleteSpecialization(SPEC_ID);

        verify(specializationRepository, times(1)).deleteById(SPEC_ID);
    }

    @Test
    void shouldThrowsExceptionWhenDeleteNotExistSpecialization() {
        when(specializationRepository.existsById(SPEC_ID)).thenReturn(false);

        assertThatThrownBy(() -> specializationService.deleteSpecialization(SPEC_ID)).isInstanceOf(EntityNotFoundException.class);
    }
}
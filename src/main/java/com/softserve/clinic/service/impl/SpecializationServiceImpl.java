package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.mapper.SpecializationMapper;
import com.softserve.clinic.model.Specialization;
import com.softserve.clinic.repository.SpecializationRepository;
import com.softserve.clinic.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;

    @Override
    public List<SpecializationDto> getAllSpecializations() {
        return specializationRepository.findAll().stream()
                .map(specializationMapper::specToSpecDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecializationDto getSpecializationById(UUID specId) {
        return specializationRepository.findById(specId)
                .map(specializationMapper::specToSpecDto)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Doctor with id " + specId));
    }

    @Override
    public SpecializationDto getSpecializationByName(String specName) {
        return specializationRepository.findByName(specName)
                .map(specializationMapper::specToSpecDto)
                .orElseThrow(() -> new EntityNotFoundException("Could not find specialization " + specName));
    }

    @Override
    public void createSpecialization(SpecializationDto specializationDto) {
        Specialization specialization = specializationMapper.specDtoToSpec(specializationDto);
        specializationRepository.save(specialization);
    }

    @Override
    public void updateSpecialization(SpecializationDto specializationDto, UUID specId) {
        Specialization specialization = specializationRepository.findById(specId).orElseThrow(
                () -> new EntityNotFoundException("Could not find specialization " + specId));
        specializationMapper.updateSpecializationFromSpecializationDto(specializationDto, specialization);
        specializationRepository.save(specialization);
    }

    @Override
    public void deleteSpecialization(UUID specId) {
        if (specializationRepository.existsById(specId)) {
            specializationRepository.deleteById(specId);
        } else {
            throw new EntityNotFoundException("Could not find specialization " + specId);
        }
    }
}

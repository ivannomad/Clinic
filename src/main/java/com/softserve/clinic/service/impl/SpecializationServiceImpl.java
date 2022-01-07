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
    public SpecializationDto getSpecializationById(UUID id) {
        return specializationRepository.findById(id)
                .map(specializationMapper::specToSpecDto)
                .orElseThrow(() -> new EntityNotFoundException("Could not find specialization " + id));
    }

    @Override
    public SpecializationDto getSpecializationByName(String name) {
        return specializationRepository.findByName(name)
                .map(specializationMapper::specToSpecDto)
                .orElseThrow(() -> new EntityNotFoundException("Could not find specialization " + name));
    }

    @Override
    public void createSpecialization(SpecializationDto specializationDto) {
        Specialization specialization = specializationMapper.specDtoToSpec(specializationDto);
        specializationRepository.save(specialization);
    }

    @Override
    public void updateSpecialization(SpecializationDto specializationDto, UUID id) {
        Specialization specialization = specializationMapper.specDtoToSpec(specializationDto);
        specializationRepository.findById(id)
                .map(spec -> specializationRepository.save(specialization))
                .orElseThrow(() -> new EntityNotFoundException("Could not find specialization " + id));
    }

    @Override
    public void deleteSpecialization(UUID id) {
        if (specializationRepository.existsById(id)) {
            specializationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Could not find specialization" + id);
        }
    }
}

package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.mapper.DoctorMapper;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.repository.DoctorRepository;
import com.softserve.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::doctorToDoctorDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto getDoctorById(UUID uuid) {
        Doctor doctor = doctorRepository.findById(uuid)
                .orElseThrow(
                        () -> new EntityNotFoundException("Doctor with id: " + uuid + " is not found"));
        return doctorMapper.doctorToDoctorDto(doctor);
    }

    @Override
    public void createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToDoctor(doctorDto);
        doctorRepository.save(doctor);
    }

    @Override
    public void updateDoctorById(DoctorDto doctorDto, UUID uuid) {
        Doctor doctor = doctorMapper.doctorDtoToDoctor(doctorDto);
        doctorRepository.findById(uuid)
                .map(obj -> doctorRepository.save(doctor))
                .orElseThrow(
                        () -> new EntityNotFoundException("Doctor with id: " + uuid + " is not found"));
    }

    @Override
    public void deleteDoctorById(UUID uuid) {
        if (doctorRepository.existsById(uuid)) {
            doctorRepository.deleteById(uuid);
        } else {
            throw new EntityNotFoundException("Doctor with id: " + uuid + " is not found");
        }
    }
}

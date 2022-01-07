package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.exception.NullEntityReferenceException;
import com.softserve.clinic.mapper.DoctorMapper;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.repository.DoctorRepository;
import com.softserve.clinic.repository.UserRepository;
import com.softserve.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        if (doctorDto != null) {
            userRepository.save(doctorMapper.doctorDtoToDoctor(doctorDto));
            return doctorDto;
        }
        throw new NullEntityReferenceException("Doctor is not found");
    }

    @Override
    public List<Doctor> getAllDoctors(){


        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(UUID id) {
        return doctorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Doctor with id: " + id + " is not found"));
    }

    @Override
    public void deleteDoctorById(UUID id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        }
        throw new NullEntityReferenceException("Doctor is not found by id");
    }

    @Override
    public Doctor getDoctorBySpecialization(UUID id) {
        return null; // TODO
    }

}

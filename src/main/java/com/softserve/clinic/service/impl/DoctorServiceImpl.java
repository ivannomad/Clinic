package com.softserve.clinic.service.impl;

import com.softserve.clinic.exception.NullEntityReferenceException;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.repository.DoctorRepository;
import com.softserve.clinic.repository.UserRepository;
import com.softserve.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public Doctor createDoctor(Doctor doctor) {
        if (doctor != null) {
            return userRepository.save(doctor);
        }
        throw new NullEntityReferenceException("Doctor is not found");
    }

    @Override
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }
}

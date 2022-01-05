package com.softserve.clinic.service.impl;

import com.softserve.clinic.exception.NullEntityReferenceException;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.repository.DoctorRepository;
import com.softserve.clinic.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        if (doctor != null) {
            return doctorRepository.save(doctor);
        }
        throw new NullEntityReferenceException("Doctor is not found");
    }
}

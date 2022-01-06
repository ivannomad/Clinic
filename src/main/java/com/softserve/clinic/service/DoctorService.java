package com.softserve.clinic.service;

import com.softserve.clinic.model.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor createDoctor(Doctor doctor);

    List<Doctor> getAllDoctors();
}



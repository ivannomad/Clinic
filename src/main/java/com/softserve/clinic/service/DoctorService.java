package com.softserve.clinic.service;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.model.Specialization;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    DoctorDto createDoctor(DoctorDto doctorDto);

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(UUID id);

    void deleteDoctorById(UUID id);

    Doctor getDoctorBySpecialization(UUID id);

//    Doctor getAppointmentByDoctorId(UUID id);

}



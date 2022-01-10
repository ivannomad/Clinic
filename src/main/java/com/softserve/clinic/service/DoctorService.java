package com.softserve.clinic.service;

import com.softserve.clinic.dto.DoctorDto;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    List<DoctorDto> getAllDoctors();

    DoctorDto getDoctorById(UUID uuid);

    void createDoctor(DoctorDto doctorDto);

    void updateDoctorById(DoctorDto doctorDto, UUID uuid);

    void deleteDoctorById(UUID uuid);

}



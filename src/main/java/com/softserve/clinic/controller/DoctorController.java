package com.softserve.clinic.controller;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.model.Specialization;
import com.softserve.clinic.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorServiceImpl doctorService;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        return doctorService.createDoctor(doctorDto);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(UUID id) {
        return doctorService.getDoctorById(id);
    }

}

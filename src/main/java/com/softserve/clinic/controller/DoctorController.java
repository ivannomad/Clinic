package com.softserve.clinic.controller;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.model.Specialization;
import com.softserve.clinic.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorServiceImpl doctorService;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public Doctor createDoctor(DoctorDto newDoctor) {
        Doctor doctor = new Doctor();
        Specialization specialization = new Specialization();
        specialization.setName(newDoctor.getSpecialization());
        doctor.addSpec(specialization);
        return doctorService.createDoctor(doctor);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
}

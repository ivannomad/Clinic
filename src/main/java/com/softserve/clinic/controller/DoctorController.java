package com.softserve.clinic.controller;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorServiceImpl doctorService;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/")
    public DoctorDto createDoctor(@PathVariable Doctor doctor) {
       doctorService.createDoctor(doctor);
       return new DoctorDto(doctor.getId());
    }

    @GetMapping("/all")
    public String getDocs(){
        return "Hi";
    }

}

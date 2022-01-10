package com.softserve.clinic.controller;


import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.model.Patient;
import com.softserve.clinic.service.impl.PatientServiceImpl;
import com.softserve.clinic.service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientServiceImpl patientService;

    @Autowired
    public PatientController(PatientServiceImpl PatientService) {
        this.patientService = PatientService;
    }

    @PostMapping
    public PatientDto createPatient(PatientDto patientDto) {
        return patientService.createPatient(patientDto);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(UUID id) {
        return patientService.getPatientById(id);
    }

}

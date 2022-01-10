package com.softserve.clinic.controller;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{uuid}")
    public DoctorDto getDoctorById(@PathVariable UUID uuid) {
        return doctorService.getDoctorById(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDoctor(@RequestBody @Valid DoctorDto doctorDto) {
        doctorService.createDoctor(doctorDto);
    }

    @PutMapping("/{uuid}")
    public void updateDoctorById(@RequestBody @Valid DoctorDto doctorDto, @PathVariable UUID uuid) {
        doctorService.updateDoctorById(doctorDto, uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctorById(@PathVariable UUID uuid) {
        doctorService.deleteDoctorById(uuid);
    }

}

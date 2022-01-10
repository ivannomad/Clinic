package com.softserve.clinic.controller;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{doctorId}")
    public DoctorDto getDoctorById(@PathVariable UUID doctorId) {
        return doctorService.getDoctorById(doctorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDoctor(@RequestBody @Valid DoctorDto doctorDto) {
        doctorService.createDoctor(doctorDto);
    }

    @PutMapping("/{doctorId}")
    public void updateDoctorById(@RequestBody @Valid DoctorDto doctorDto, @PathVariable UUID doctorId) {
        doctorService.updateDoctorById(doctorDto, doctorId);
    }

    @DeleteMapping("/{doctorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctorById(@PathVariable UUID doctorId) {
        doctorService.deleteDoctorById(doctorId);
    }

    @PostMapping("/{doctorId}/schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAppointment(@PathVariable UUID doctorId, @RequestBody LocalDateTime localDateTime) {
        doctorService.createAppointment(doctorId, localDateTime);
    }

    @GetMapping("/{doctorId}/schedule")
    public List<AppointmentDto> getDoctorSchedule(@PathVariable UUID doctorId) {
        return doctorService.getDoctorSchedule(doctorId);
    }

    @GetMapping("/{doctorId}/app")
    public List<AppointmentDto> getDoctorAppointments(@PathVariable UUID doctorId) {
        return doctorService.getDoctorAppointments(doctorId);
    }
}

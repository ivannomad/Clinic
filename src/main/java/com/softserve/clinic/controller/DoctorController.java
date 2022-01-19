package com.softserve.clinic.controller;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody @Valid DoctorDto doctorDto) {
        DoctorDto newDoctor = doctorService.createDoctor(doctorDto);

        return new ResponseEntity<>(newDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> updateDoctorById(@RequestBody @Valid DoctorDto doctorDto, @PathVariable UUID doctorId) {
        DoctorDto updatedDoctor = doctorService.updateDoctorById(doctorDto, doctorId);

        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    @DeleteMapping("/{doctorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctorById(@PathVariable UUID doctorId) {
        doctorService.deleteDoctorById(doctorId);
    }

    @PostMapping("/{doctorId}/appointments")
    public ResponseEntity<AppointmentDto> createAppointment(@PathVariable UUID doctorId, @RequestBody @Valid AppointmentDto appointmentDto) {
        AppointmentDto newAppointment = doctorService.createAppointment(doctorId, appointmentDto);

        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/{doctorId}/appointments/free")
    public List<AppointmentDto> getFreeDoctorAppointments(@PathVariable UUID doctorId) {
        return doctorService.getDoctorFreeAppointments(doctorId);
    }

    @GetMapping("/{doctorId}/appointments/not-free")
    public List<AppointmentDto> getDoctorAppointments(@PathVariable UUID doctorId) {
        return doctorService.getDoctorAppointments(doctorId);
    }
}

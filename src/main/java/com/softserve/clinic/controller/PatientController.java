package com.softserve.clinic.controller;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{patientId}")
    public PatientDto getPatientById(@PathVariable UUID patientId) {
        return patientService.getPatientById(patientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto createPatient(@RequestBody @Valid PatientDto patientDto) {
        return patientService.createPatient(patientDto);
    }

    @DeleteMapping("/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientById(@PathVariable UUID patientId) {
        patientService.deletePatientById(patientId);
    }

    @PutMapping("/{patientId}")
    public void updatePatient(@RequestBody @Valid PatientDto patientDto, @PathVariable UUID patientId) {
        patientService.updatePatient(patientDto, patientId);
    }

    @PostMapping("/{patientId}/appointments/{appId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeAppointment(@PathVariable UUID patientId, @PathVariable UUID appId) {
        patientService.makeAppointment(patientId, appId);
    }

    @GetMapping("/{patientId}/appointments")
    public List<AppointmentDto> getAllAppointments(@PathVariable UUID patientId) {
        return patientService.getAllPatientAppointments(patientId);
    }
}

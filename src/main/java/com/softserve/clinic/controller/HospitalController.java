package com.softserve.clinic.controller;

import com.softserve.clinic.dto.HospitalDto;
import com.softserve.clinic.dto.PatientDto;
import com.softserve.clinic.model.Hospital;
import com.softserve.clinic.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping
    public List<HospitalDto> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/{hospitalId}")
    public HospitalDto getHospitalById(@PathVariable UUID hospitalId) {
        return hospitalService.getHospitalById(hospitalId);
    }

    @GetMapping("/hospitalName")
    public HospitalDto getHospitalByName(@RequestParam String hospitalName) {
        return hospitalService.getHospitalByName(hospitalName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createHospital(@RequestBody @Valid HospitalDto hospitalDto) {
        HospitalDto hospital = hospitalService.createHospital(hospitalDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{hospitalId}")
                .buildAndExpand(hospital.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{hospitalId}")
    public void updateHospital(@RequestBody @Valid HospitalDto hospitalDto, @PathVariable UUID hospitalId) {
        hospitalService.updateHospital(hospitalDto, hospitalId);
    }

    @DeleteMapping("/{hospitalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHospitalById(@PathVariable UUID hospitalId) {
        hospitalService.deleteHospitalById(hospitalId);
    }
}

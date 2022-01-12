package com.softserve.clinic.controller;

import com.softserve.clinic.dto.HospitalDto;
import com.softserve.clinic.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/{hospitalName}")
    public HospitalDto getHospitalByName(@PathVariable String hospitalName) {
        return hospitalService.getHospitalByName(hospitalName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHospital(@RequestBody @Valid HospitalDto hospitalDto) {
        hospitalService.createHospital(hospitalDto);
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

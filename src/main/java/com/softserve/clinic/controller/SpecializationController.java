package com.softserve.clinic.controller;

import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.model.Specialization;
import com.softserve.clinic.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping
    public List<SpecializationDto> getAllSpecializations() {

        return specializationService.getAllSpecializations();
    }

    @GetMapping("/{specName}")
    public SpecializationDto getSpecializationByName(@PathVariable String specName) {
        return specializationService.getSpecializationByName(specName);
    }

    @PostMapping
    public ResponseEntity<SpecializationDto> createSpecialization(@RequestBody @Valid SpecializationDto specializationDto) {
        SpecializationDto newSpecDto = specializationService.createSpecialization(specializationDto);

        return new ResponseEntity<>(newSpecDto, HttpStatus.CREATED);
    }

    @PutMapping("/{specId}")
    public ResponseEntity<SpecializationDto> updateSpecialization(@RequestBody @Valid SpecializationDto specializationDto, @PathVariable UUID specId) {
        SpecializationDto updatedSpecDto = specializationService.updateSpecialization(specializationDto, specId);

        return new ResponseEntity<>(updatedSpecDto, HttpStatus.OK);
    }

    @DeleteMapping("/{specId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialization(@PathVariable UUID specId) {

        specializationService.deleteSpecialization(specId);
    }

}

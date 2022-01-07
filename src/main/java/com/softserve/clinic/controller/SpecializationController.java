package com.softserve.clinic.controller;

import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/specialization")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping
    public List<SpecializationDto> getAllSpecializations() {
        return specializationService.getAllSpecializations();
    }

    @GetMapping("/{name}")
    public SpecializationDto getSpecializationByName(@PathVariable String name) {
        return specializationService.getSpecializationByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSpecialization(@RequestBody @Valid SpecializationDto specializationDto) {
        specializationService.createSpecialization(specializationDto);
    }

    @PutMapping("/{id}")
    public void updateSpecialization(@RequestBody SpecializationDto specializationDto, @PathVariable UUID id) {
        specializationService.updateSpecialization(specializationDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialization(@PathVariable UUID id) {
        specializationService.deleteSpecialization(id);
    }

}

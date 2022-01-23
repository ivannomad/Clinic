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
    @ResponseStatus(HttpStatus.CREATED)
    public void createSpecialization(@RequestBody @Valid SpecializationDto specializationDto) {
        specializationService.createSpecialization(specializationDto);
    }

    @PutMapping("/{specId}")
    public void updateSpecialization(@RequestBody @Valid SpecializationDto specializationDto, @PathVariable UUID specId) {
        specializationService.updateSpecialization(specializationDto, specId);
    }

    @DeleteMapping("/{specId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialization(@PathVariable UUID specId) {

        specializationService.deleteSpecialization(specId);
    }

}

package com.softserve.clinic.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Value
public class SpecializationDto implements Serializable {
    UUID id;
    @NotBlank String name;
    String description;
}

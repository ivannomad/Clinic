package com.softserve.clinic.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Value
public class SpecializationDto implements Serializable {
    UUID id;
    @NotBlank(message = "Specialization name may not be blank")
    String name;
    String description;
}

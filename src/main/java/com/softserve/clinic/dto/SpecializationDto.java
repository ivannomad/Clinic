package com.softserve.clinic.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Value
public class SpecializationDto implements Serializable {

    @NotNull
    UUID id;

    @NotBlank(message = "Specialization name may not be blank")
    String name;

    @NotBlank(message = "Description may not be blank")
    String description;
}

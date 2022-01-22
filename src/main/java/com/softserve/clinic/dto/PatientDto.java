package com.softserve.clinic.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Value
public class PatientDto implements Serializable {

    @NotNull
    UUID id;

    @NotBlank(message = "First name may not be blank")
    String firstName;

    @NotBlank(message = "Second name may not be blank")
    String secondName;

    @NotBlank(message = "Contact number may not be blank")
    String contactNumber;

    @NotNull(message = "Birth date may not be empty")
    @Past(message = "Birth date can not be in future")
    LocalDate birthDate;
}

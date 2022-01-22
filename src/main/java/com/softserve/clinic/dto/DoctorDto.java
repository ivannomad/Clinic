package com.softserve.clinic.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Value
public class DoctorDto implements Serializable {

    @NotNull
    UUID id;

    @NotBlank (message = "First name may not be blank")
    String firstName;

    @NotBlank (message = "Second name may not be blank")
    String secondName;

    @NotBlank (message = "Contact number may not be blank")
    String contactNumber;
}

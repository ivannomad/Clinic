package com.softserve.clinic.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Value
public class HospitalDto implements Serializable {

    UUID id;

    @NotBlank(message = "Hospital name must not be blank")
    String hospitalName;

    @NotBlank(message = "City may must be blank")
    String city;

    @NotBlank(message = "Street name must not be blank")
    String street;

    @NotBlank(message = "Address number must not be blank")
    String addressNumber;
}

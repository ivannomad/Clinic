package com.softserve.clinic.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Value
public class PatientDto implements Serializable {
    UUID id;

    @NotBlank(message = "Username may not be blank")
    String username;

    @NotBlank(message = "Password may not be blank")
    String password;

    @NotBlank(message = "First name may not be blank")
    String firstName;

    @NotBlank(message = "Second name may not be blank")
    String secondName;

    @NotBlank
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Contact number may not be blank")
    String contactNumber;
  
    LocalDate birthDate;
}

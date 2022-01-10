package com.softserve.clinic.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Value
public class PatientDto implements Serializable {
    UUID id;
    String username;
    String password;
    String firstName;
    String secondName;
    String email;
    String contactNumber;
    LocalDate birthDate;
}

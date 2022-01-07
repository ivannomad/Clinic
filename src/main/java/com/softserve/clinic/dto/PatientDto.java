package com.softserve.clinic.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PatientDto implements Serializable {
    private final UUID id;
    private final String username;
    private final String password;
    private final String firstName;
    private final String secondName;
    private final String email;
    private final String contactNumber;
    private final LocalDate birthDate;
}

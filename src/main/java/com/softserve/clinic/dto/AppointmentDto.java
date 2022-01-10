package com.softserve.clinic.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class AppointmentDto implements Serializable {
    UUID id;
    LocalDateTime dateAndTime;
    DoctorDto doctor;
    PatientDto patient;
}

package com.softserve.clinic.dto;

import lombok.Value;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class AppointmentDto implements Serializable {

    UUID id;

    @Future @NotNull (message = "Date and Time may not be null")
    LocalDateTime dateAndTime;

    DoctorDto doctor;

    PatientDto patient;
}

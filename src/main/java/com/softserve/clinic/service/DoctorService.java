package com.softserve.clinic.service;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DoctorService {

    List<DoctorDto> getAllDoctors();

    DoctorDto getDoctorById(UUID doctorId);

    DoctorDto createDoctor(DoctorDto doctorDto);

    DoctorDto updateDoctorById(DoctorDto doctorDto, UUID doctorId);

    void deleteDoctorById(UUID doctorId);

    List<AppointmentDto> getDoctorFreeAppointments(UUID doctorId);

    List<AppointmentDto> getDoctorAppointments(UUID doctorId);

    AppointmentDto createAppointment(UUID doctorId, AppointmentDto appointmentDto);
}



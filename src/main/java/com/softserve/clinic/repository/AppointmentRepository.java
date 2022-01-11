package com.softserve.clinic.repository;

import com.softserve.clinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    
    List<Appointment> findAppointmentsByDoctorIdAndPatientIsNull(UUID id);

    List<Appointment> findAppointmentsByDoctorIdAndPatientNotNull(UUID id);

    List<Appointment> findAppointmentsByPatientId(UUID id);

    Optional<Appointment> findAppointmentByIdAndPatientIsNull(UUID id);

}
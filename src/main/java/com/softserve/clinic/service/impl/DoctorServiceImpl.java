package com.softserve.clinic.service.impl;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.mapper.AppointmentMapper;
import com.softserve.clinic.mapper.DoctorMapper;
import com.softserve.clinic.model.Appointment;
import com.softserve.clinic.model.Doctor;
import com.softserve.clinic.repository.AppointmentRepository;
import com.softserve.clinic.repository.DoctorRepository;
import com.softserve.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::doctorToDoctorDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto getDoctorById(UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Unable to find Doctor with id " + doctorId));
        return doctorMapper.doctorToDoctorDto(doctor);
    }

    @Override
    public void createDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.doctorDtoToDoctor(doctorDto);
        doctorRepository.save(doctor);
    }

    @Override
    public void updateDoctorById(DoctorDto doctorDto, UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Doctor with id " + doctorId));
        doctorMapper.updateDoctorFromDoctorDto(doctorDto, doctor);
        doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctorById(UUID doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            doctorRepository.deleteById(doctorId);
        } else {
            throw new EntityNotFoundException("Unable to find Doctor with id " + doctorId);
        }
    }

    @Override
    public List<AppointmentDto> getDoctorSchedule(UUID doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            return appointmentRepository.findAppointmentsByDoctorIdAndPatientIsNull(doctorId).stream()
                    .map(appointmentMapper::appointmentToAppointmentDto)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Unable to find Doctor with id " + doctorId);
        }
    }

    @Override
    public List<AppointmentDto> getDoctorAppointments(UUID doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            return appointmentRepository.findAppointmentsByDoctorIdAndPatientNotNull(doctorId).stream()
                    .map(appointmentMapper::appointmentToAppointmentDto)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Unable to find Doctor with id " + doctorId);
        }
    }

    @Override
    public void createAppointment(UUID doctorId, LocalDateTime localDateTime) {
        Doctor doctor = doctorRepository.getById(doctorId);
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setDateAndTime(localDateTime);
        appointmentRepository.save(appointment);
    }
}

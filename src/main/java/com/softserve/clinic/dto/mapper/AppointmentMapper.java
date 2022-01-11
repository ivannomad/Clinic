package com.softserve.clinic.dto.mapper;

import com.softserve.clinic.dto.AppointmentDto;
import com.softserve.clinic.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AppointmentMapper {
    Appointment appointmentDtoToAppointment(AppointmentDto appointmentDto);

    AppointmentDto appointmentToAppointmentDto(Appointment appointment);
}

package com.softserve.clinic.mapper;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.model.Doctor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DoctorMapper {

    Doctor doctorDtoToDoctor(DoctorDto doctorDto);

    DoctorDto doctorToDoctorDto(Doctor doctor);

}

package com.softserve.clinic.dto.mapper;

import com.softserve.clinic.dto.DoctorDto;
import com.softserve.clinic.model.Doctor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DoctorMapper {

    Doctor doctorDtoToDoctor(DoctorDto doctorDto);

    DoctorDto doctorToDoctorDto(Doctor doctor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDoctorFromDoctorDto(DoctorDto doctorDto, @MappingTarget Doctor doctor);
}

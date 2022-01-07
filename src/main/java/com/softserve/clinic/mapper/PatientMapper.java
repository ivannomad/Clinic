package com.softserve.clinic.mapper;

import com.softserve.clinic.model.Patient;
import com.softserve.clinic.dto.PatientDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PatientMapper {
    Patient patientDtoToPatient(PatientDto patientDto);

    PatientDto patientToPatientDto(Patient patient);

/*    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePatientFromPatientDto(PatientDto patientDto, @MappingTarget Patient patient);*/
}

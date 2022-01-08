package com.softserve.clinic.mapper;

import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.model.Specialization;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpecializationMapper {

    Specialization specDtoToSpec(SpecializationDto specializationDto);

    SpecializationDto specToSpecDto(Specialization specialization);

}


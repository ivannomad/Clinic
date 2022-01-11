package com.softserve.clinic.dto.mapper;

import com.softserve.clinic.dto.SpecializationDto;
import com.softserve.clinic.model.Specialization;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpecializationMapper {

    Specialization specDtoToSpec(SpecializationDto specializationDto);

    SpecializationDto specToSpecDto(Specialization specialization);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSpecializationFromSpecializationDto(SpecializationDto specializationDto, @MappingTarget Specialization specialization);
}


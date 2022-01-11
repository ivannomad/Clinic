package com.softserve.clinic.dto.mapper;

import com.softserve.clinic.dto.HospitalDto;
import com.softserve.clinic.model.Hospital;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HospitalMapper {
    Hospital hospitalDtoToHospital(HospitalDto hospitalDto);

    HospitalDto hospitalToHospitalDto(Hospital hospital);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateHospitalFromHospitalDto(HospitalDto hospitalDto, @MappingTarget Hospital hospital);
}

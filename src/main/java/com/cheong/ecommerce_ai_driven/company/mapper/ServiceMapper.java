package com.cheong.ecommerce_ai_driven.company.mapper;

import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityInput;
import com.cheong.ecommerce_ai_driven.speciality.model.Speciality;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceMapper {

    SpecialityDTO mapToServiceDTO(Speciality speciality);

    Speciality mapToService(SpecialityDTO specialityDTO);

    Speciality mapToService(SpecialityInput service);

    List<Speciality> mapToServices(List<SpecialityDTO> services);
}

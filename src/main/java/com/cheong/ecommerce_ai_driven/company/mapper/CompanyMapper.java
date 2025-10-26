package com.cheong.ecommerce_ai_driven.company.mapper;

import com.cheong.ecommerce_ai_driven.company.dto.BusinessDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessInput;
import com.cheong.ecommerce_ai_driven.company.entity.Business;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BusinessDTO mapToCompanyDTO(Business business);

    Business mapToBusiness(BusinessInput businessInput);
}

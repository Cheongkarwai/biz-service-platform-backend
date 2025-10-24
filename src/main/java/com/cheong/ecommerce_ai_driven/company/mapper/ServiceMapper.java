package com.cheong.ecommerce_ai_driven.company.mapper;

import com.cheong.ecommerce_ai_driven.company.dto.ServiceDTO;
import com.cheong.ecommerce_ai_driven.company.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceMapper {

    ServiceDTO mapToServiceDTO(Service service);

    Service mapToService(ServiceDTO serviceDTO);

    List<Service> mapToServices(List<ServiceDTO> services);
}

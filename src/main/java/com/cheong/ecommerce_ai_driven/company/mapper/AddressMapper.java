package com.cheong.ecommerce_ai_driven.company.mapper;

import com.cheong.ecommerce_ai_driven.company.dto.AddressDTO;
import com.cheong.ecommerce_ai_driven.company.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    AddressDTO mapToAddressDTO(Address address);

    Address mapToAddress(AddressDTO addressDTO);

    List<Address> mapToAddresses(List<AddressDTO> addresses);
}

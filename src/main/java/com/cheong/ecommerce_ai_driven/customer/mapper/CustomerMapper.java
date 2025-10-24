package com.cheong.ecommerce_ai_driven.customer.mapper;

import com.cheong.ecommerce_ai_driven.customer.dto.CustomerDTO;
import com.cheong.ecommerce_ai_driven.customer.entity.Customer;
import com.cheong.ecommerce_ai_driven.customer.input.CustomerInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerDTO mapToCustomerDTO(Customer customer);

    Customer mapToCustomer(CustomerInput customerInput);
}

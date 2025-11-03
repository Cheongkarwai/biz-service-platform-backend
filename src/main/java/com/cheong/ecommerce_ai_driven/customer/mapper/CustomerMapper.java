package com.cheong.ecommerce_ai_driven.customer.mapper;

import com.cheong.ecommerce_ai_driven.account.entity.Account;
import com.cheong.ecommerce_ai_driven.customer.dto.CustomerDTO;
import com.cheong.ecommerce_ai_driven.customer.entity.Customer;
import com.cheong.ecommerce_ai_driven.customer.input.CustomerInput;
import com.cheong.ecommerce_ai_driven.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerDTO mapToCustomerDTO(Customer customer);

    @Mapping(target = "id", source = "customer.id")
    @Mapping(target = "accountDetails", source = "userDTO")
    CustomerDTO mapToCustomerDTO(Customer customer, Account account, UserDTO userDTO);

    @Mapping(target = "emailAddress", source = "accountDetails.email")
    Customer mapToCustomer(CustomerInput customerInput);


}

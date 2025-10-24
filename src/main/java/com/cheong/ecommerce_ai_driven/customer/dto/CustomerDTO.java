package com.cheong.ecommerce_ai_driven.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String id;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String emailAddress;
}

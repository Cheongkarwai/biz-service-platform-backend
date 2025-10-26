package com.cheong.ecommerce_ai_driven.customer.dto;

import com.cheong.ecommerce_ai_driven.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO{

    private String id;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String emailAddress;

    private UserDTO accountDetails;

}

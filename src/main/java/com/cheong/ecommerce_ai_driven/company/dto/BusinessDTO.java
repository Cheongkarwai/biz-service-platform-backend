package com.cheong.ecommerce_ai_driven.company.dto;

import com.cheong.ecommerce_ai_driven.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDTO extends UserDTO {

    private String id;

    private String name;

    private String mobileNumber;

    private String email;

    private String overview;

    private List<AddressDTO> addresses;
}

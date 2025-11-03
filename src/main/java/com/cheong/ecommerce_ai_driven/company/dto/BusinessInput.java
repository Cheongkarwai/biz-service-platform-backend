package com.cheong.ecommerce_ai_driven.company.dto;

import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessInput {

    private String name;

    private String mobileNumber;

    private String email;

    private String overview;

    private List<AddressDTO> addresses;

    private List<SpecialityDTO> services;
}

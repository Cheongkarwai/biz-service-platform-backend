package com.cheong.ecommerce_ai_driven.company.dto;

import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessInput {

    @NotBlank(message = "constraints.business.name.required.error")
    private String name;

    @NotBlank(message = "constraints.business.mobile-number.required.error")
    private String mobileNumber;

    @NotBlank(message = "constraints.business.email.required.error")
    private String email;

    @NotBlank(message = "constraints.business.overview.required.error")
    private String overview;

    @Valid
    @NotEmpty(message = "constraints.address.required.error")
    private List<@NotNull(message = "constraints.address.required.error") AddressDTO> addresses;

    private List<SpecialityDTO> services;
}

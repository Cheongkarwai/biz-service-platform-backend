package com.cheong.ecommerce_ai_driven.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotBlank(message = "{constraints.address.street.required.error}")
    private String street;

    @NotBlank(message = "{constraints.address.city.required.error}")
    private String city;

    @NotBlank(message = "{constraints.address.state.required.error}")
    private String state;

    @NotBlank(message = "{constraints.address.zip.required.error}")
    private String zip;

    @NotBlank(message = "{constraints.address.type.required.error}")
    private AddressType type;
}

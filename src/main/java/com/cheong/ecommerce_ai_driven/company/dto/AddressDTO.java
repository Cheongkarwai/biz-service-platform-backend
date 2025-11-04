package com.cheong.ecommerce_ai_driven.company.dto;

import com.cheong.ecommerce_ai_driven.common.web.BasicValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotBlank(message = "{constraints.address.street.required.error}", groups = {BasicValidation.class})
    private String street;

    @NotBlank(message = "{constraints.address.city.required.error}", groups = {BasicValidation.class})
    private String city;

    @NotBlank(message = "{constraints.address.state.required.error}", groups = {BasicValidation.class})
    private String state;

    @NotBlank(message = "{constraints.address.zip.required.error}", groups = {BasicValidation.class})
    private String zip;

    @NotNull(message = "{constraints.address.type.required.error}", groups = {BasicValidation.class})
    private AddressType type;
}

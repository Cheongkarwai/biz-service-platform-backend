package com.cheong.ecommerce_ai_driven.company.dto;

import com.cheong.ecommerce_ai_driven.common.web.AdvanceValidation;
import com.cheong.ecommerce_ai_driven.common.web.BasicValidation;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessInput {

    @NotBlank(message = "constraints.business.name.required.error", groups = {BasicValidation.class})
    private String name;

    @NotBlank(message = "constraints.business.mobile-number.required.error", groups = {BasicValidation.class})
    private String mobileNumber;

    @NotBlank(message = "constraints.business.email.required.error", groups = {BasicValidation.class})
    @Email(message = "constraints.business.email.invalid.error", groups = {AdvanceValidation.class})
    private String email;

    @NotBlank(message = "constraints.business.overview.required.error", groups = {BasicValidation.class})
    private String overview;

    @Valid
    @NotEmpty(message = "constraints.address.required.error", groups = {BasicValidation.class})
    private List<@NotNull(message = "constraints.address.required.error") AddressDTO> addresses;

    private List<SpecialityDTO> services;
}

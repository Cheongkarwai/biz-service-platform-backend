package com.cheong.ecommerce_ai_driven.customer.input;

import com.cheong.ecommerce_ai_driven.common.web.AdvanceValidation;
import com.cheong.ecommerce_ai_driven.common.web.BasicValidation;
import com.cheong.ecommerce_ai_driven.customer.validation.MinAge;
import com.cheong.ecommerce_ai_driven.user.dto.UserInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInput {

    @NotBlank(message = "{constraints.firstName.required.error}", groups = {BasicValidation.class})
    private String firstName;

    @NotBlank(message = "{constraints.lastName.required.error}", groups = {BasicValidation.class})
    private String lastName;

    @NotNull(message = "{constraints.birthDate.required.error}", groups = {BasicValidation.class})
    @MinAge(groups = {AdvanceValidation.class})
    private LocalDate birthDate;

    @NotBlank(message = "{constraints.mobileNumber.required.error}", groups = {BasicValidation.class})
    private String mobileNumber;

    @Valid
    @NotNull(message = "{constraints.accountDetails.required.error}", groups = {BasicValidation.class})
    private UserInput accountDetails;
}

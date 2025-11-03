package com.cheong.ecommerce_ai_driven.user.dto;

import com.cheong.ecommerce_ai_driven.common.AdvanceValidation;
import com.cheong.ecommerce_ai_driven.common.BasicValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoverPasswordRequest {

    @Email(message = "{constraints.emailAddress.invalid.error}", groups = {AdvanceValidation.class})
    @NotBlank(message = "{constraints.emailAddress.required.error}", groups = {BasicValidation.class})
    private String email;
}

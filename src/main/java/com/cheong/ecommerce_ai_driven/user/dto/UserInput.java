package com.cheong.ecommerce_ai_driven.user.dto;

import com.cheong.ecommerce_ai_driven.common.web.AdvanceValidation;
import com.cheong.ecommerce_ai_driven.common.web.BasicValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {

    @NotBlank(message = "{constraints.emailAddress.required.error}", groups = {BasicValidation.class})
    @Email(message = "{constraints.emailAddress.invalid.error}", groups = {AdvanceValidation.class})
    private String email;

    @NotBlank(message = "{constraints.password.required.error}", groups = {BasicValidation.class})
    private String password;

    private Map<String, Object> data;
}

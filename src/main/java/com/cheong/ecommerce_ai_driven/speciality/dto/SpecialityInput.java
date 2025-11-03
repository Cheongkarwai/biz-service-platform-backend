package com.cheong.ecommerce_ai_driven.speciality.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityInput {

    @NotBlank(message = "{constraints.service-name.required.error}")
    private String name;
}

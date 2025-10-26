package com.cheong.ecommerce_ai_driven.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpOtpRequest {

    private String phone;

    private String email;

    private Map<String, Object> data;
}

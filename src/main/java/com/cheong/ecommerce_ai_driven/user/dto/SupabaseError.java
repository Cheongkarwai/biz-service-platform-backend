package com.cheong.ecommerce_ai_driven.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupabaseError extends RuntimeException{

    private String code;
    @JsonProperty("error_code")
    private String errorCode;
    private String msg;
}

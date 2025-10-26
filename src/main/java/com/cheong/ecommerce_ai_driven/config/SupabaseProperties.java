package com.cheong.ecommerce_ai_driven.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "supabase")
public class SupabaseProperties {
    private String baseUrl;
    private String apiKey;
}

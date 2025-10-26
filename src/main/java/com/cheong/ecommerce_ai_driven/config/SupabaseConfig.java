package com.cheong.ecommerce_ai_driven.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(SupabaseProperties.class)
public class SupabaseConfig {

    @Bean
    public WebClient webClient(SupabaseProperties supabaseProperties) {
        return WebClient.builder()
                .baseUrl(supabaseProperties.getBaseUrl())
                .defaultHeader("apiKey", supabaseProperties.getApiKey())
                .build();
    }
}

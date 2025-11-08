package com.cheong.ecommerce_ai_driven.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "s3")
public class S3Properties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String region;
}

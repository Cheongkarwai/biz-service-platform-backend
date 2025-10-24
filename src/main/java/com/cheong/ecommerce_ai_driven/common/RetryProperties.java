package com.cheong.ecommerce_ai_driven.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "spring.application.retry")
public class RetryProperties {

    private int maxAttempts;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration minBackoff;

    private List<Class<? extends Throwable>> retryableExceptions;
}

package com.cheong.ecommerce_ai_driven.common.util;

import com.cheong.ecommerce_ai_driven.common.properties.RetryProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@Component
@EnableConfigurationProperties(RetryProperties.class)
public class RetryUtil {

    private final RetryProperties retryProperties;

    public RetryUtil(RetryProperties retryProperties) {
        this.retryProperties = retryProperties;
    }

    public RetryBackoffSpec getRetrySpec() {
        return Retry.backoff(retryProperties.getMaxAttempts(), retryProperties.getMinBackoff())
                .filter(this::isRetryableException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    public  boolean isRetryableException(Throwable throwable) {
        return retryProperties.getRetryableExceptions().stream().anyMatch(exception -> exception.isAssignableFrom(throwable.getClass()));
    }
}

package com.cheong.ecommerce_ai_driven.webhook.strategy;

import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import reactor.core.publisher.Mono;

public interface OutboxEventStrategy {

    Mono<Void> execute(OutboxEvent outboxEvent);

    boolean supports(OutboxEvent outboxEvent);
}

package com.cheong.ecommerce_ai_driven.webhook.strategy;

import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import com.cheong.ecommerce_ai_driven.webhook.dto.EventType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SupabaseUserCreationStrategy implements OutboxEventStrategy {

    @Override
    public Mono<Void> execute(OutboxEvent outboxEvent) {
        return null;
    }

    @Override
    public boolean supports(OutboxEvent outboxEvent) {
        return EventType.CREATE_ACCOUNT.equals(outboxEvent.getEventType());
    }
}

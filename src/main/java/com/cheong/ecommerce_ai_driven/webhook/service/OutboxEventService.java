package com.cheong.ecommerce_ai_driven.webhook.service;

import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import com.cheong.ecommerce_ai_driven.common.data.OutboxEventRepository;
import com.cheong.ecommerce_ai_driven.company.entity.Business;
import com.cheong.ecommerce_ai_driven.webhook.dto.EventType;
import com.cheong.ecommerce_ai_driven.webhook.dto.SupabaseCdcWebhookPayload;
import com.cheong.ecommerce_ai_driven.webhook.factory.OutboxEventFactory;
import com.cheong.ecommerce_ai_driven.webhook.strategy.OutboxEventStrategyContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Service responsible for handling operations related to outbox events.
 * This service is designed to facilitate the creation and saving of events
 * to an outbox table for eventual processing.
 */
@Service
public class OutboxEventService {

    private final OutboxEventStrategyContext outboxEventStrategyContext;

    public OutboxEventService(OutboxEventStrategyContext outboxEventStrategyContext) {
        this.outboxEventStrategyContext = outboxEventStrategyContext;
    }

    public Mono<Void> notify(SupabaseCdcWebhookPayload<OutboxEvent> payload){
        if("INSERT".equals(payload.getType())){
            return outboxEventStrategyContext.execute(payload.getRecord());
        }
        return Mono.empty();
    }

    public Mono<OutboxEvent> save(String aggregateId, String payload, EventType eventType){
        return Mono.just(Objects.requireNonNull(OutboxEventFactory.createOutboxEvent(aggregateId, payload, eventType)));
    }

}

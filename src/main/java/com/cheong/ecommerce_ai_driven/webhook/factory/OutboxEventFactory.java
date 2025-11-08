package com.cheong.ecommerce_ai_driven.webhook.factory;

import com.cheong.ecommerce_ai_driven.account.entity.Account;
import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import com.cheong.ecommerce_ai_driven.company.entity.Business;
import com.cheong.ecommerce_ai_driven.customer.entity.Customer;
import com.cheong.ecommerce_ai_driven.webhook.dto.EventType;
import reactor.core.publisher.Mono;

public class OutboxEventFactory {

    public static OutboxEvent createOutboxEvent(String aggregateId, String payload, EventType eventType) {
        return switch (eventType) {
            case CREATE_COMPANY ->{
                OutboxEvent outboxEvent = new OutboxEvent();
                outboxEvent.setAggregateId(aggregateId);
                outboxEvent.setAggregateType(Business.class.getSimpleName());
                outboxEvent.setEventType(EventType.CREATE_COMPANY);
                outboxEvent.setPayload(payload);
                yield outboxEvent;
            }
            case CREATE_ACCOUNT -> {
                OutboxEvent outboxEvent = new OutboxEvent();
                outboxEvent.setAggregateId(aggregateId);
                outboxEvent.setAggregateType(Account.class.getSimpleName());
                outboxEvent.setEventType(EventType.CREATE_ACCOUNT);
                outboxEvent.setPayload(payload);
                yield outboxEvent;
            }
            default -> null;
        };
    }
}

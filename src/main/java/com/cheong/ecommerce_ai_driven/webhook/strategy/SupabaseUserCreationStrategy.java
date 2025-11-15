package com.cheong.ecommerce_ai_driven.webhook.strategy;

import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import com.cheong.ecommerce_ai_driven.user.adapter.SupabaseUserAdapter;
import com.cheong.ecommerce_ai_driven.user.dto.UserInput;
import com.cheong.ecommerce_ai_driven.webhook.dto.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SupabaseUserCreationStrategy implements OutboxEventStrategy {

    private final SupabaseUserAdapter supabaseUserAdapter;

    private final ObjectMapper objectMapper;

    public SupabaseUserCreationStrategy(SupabaseUserAdapter supabaseUserAdapter,
                                        ObjectMapper objectMapper) {
        this.supabaseUserAdapter = supabaseUserAdapter;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> execute(OutboxEvent outboxEvent) {
        UserInput userInput = objectMapper.convertValue(outboxEvent.getPayload(), UserInput.class);
        return supabaseUserAdapter.signUp(userInput)
                .doOnNext(userDTO -> log.info("User created: {}", userDTO))
                .then();
    }

    @Override
    public boolean supports(OutboxEvent outboxEvent) {
        return EventType.CREATE_ACCOUNT.equals(outboxEvent.getEventType());
    }
}

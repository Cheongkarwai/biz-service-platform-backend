package com.cheong.ecommerce_ai_driven.webhook.strategy;

import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class OutboxEventStrategyContext {

    private final List<OutboxEventStrategy> strategies;

    public OutboxEventStrategyContext(List<OutboxEventStrategy> strategies) {
        this.strategies = strategies;
    }

    public Mono<Void> execute(OutboxEvent outboxEvent){
        return Flux.fromIterable(strategies)
                .filter(strategy -> strategy.supports(outboxEvent))
                .next()
                .flatMap(strategy -> strategy.execute(outboxEvent));
    }
}

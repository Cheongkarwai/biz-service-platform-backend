package com.cheong.ecommerce_ai_driven.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageRelayScheduler {


    public MessageRelayScheduler() {}

    @Scheduled(fixedDelay = 1000)
    public void relayOutboxEvents() {

    }
}

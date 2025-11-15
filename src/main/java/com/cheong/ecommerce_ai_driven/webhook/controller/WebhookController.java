package com.cheong.ecommerce_ai_driven.webhook.controller;

import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import com.cheong.ecommerce_ai_driven.user.dto.UserDTO;
import com.cheong.ecommerce_ai_driven.user.dto.UserInput;
import com.cheong.ecommerce_ai_driven.webhook.dto.SupabaseCdcWebhookPayload;
import com.cheong.ecommerce_ai_driven.webhook.service.CallbackService;
import com.cheong.ecommerce_ai_driven.webhook.service.OutboxEventService;
import io.debezium.engine.ChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/webhook")
public class WebhookController {

   private final CallbackService callbackService;

   public WebhookController(CallbackService callbackService) {
       this.callbackService = callbackService;
   }

    @PostMapping("/outbox")
    public Mono<Void> outbox(@RequestBody SupabaseCdcWebhookPayload<OutboxEvent> payload) {
        //Implement this to store in kafka for asynchronous operation to prevent timeout
        return callbackService.handleOutboxEvent(payload);
    }

    @PostMapping("/user/created")
    public Mono<Void> userCreated(@RequestBody SupabaseCdcWebhookPayload<UserDTO> payload){
        return callbackService.handleUserCreatedEvent(payload);
    }
}

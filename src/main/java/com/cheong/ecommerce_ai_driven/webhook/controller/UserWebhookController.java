package com.cheong.ecommerce_ai_driven.webhook.controller;

import com.cheong.ecommerce_ai_driven.user.dto.UserDTO;
import com.cheong.ecommerce_ai_driven.webhook.dto.SupabaseCdcWebhookPayload;
import com.cheong.ecommerce_ai_driven.webhook.service.CallbackService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/webhook/user")
public class UserWebhookController {

    private final CallbackService callbackService;

    public UserWebhookController(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @PostMapping("/created")
    public Mono<Void> userCreated(@RequestBody SupabaseCdcWebhookPayload<UserDTO> payload){
        return callbackService.handleUserCreatedEvent(payload);
    }
}

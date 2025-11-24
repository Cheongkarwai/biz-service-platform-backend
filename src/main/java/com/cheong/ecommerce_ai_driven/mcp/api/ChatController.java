package com.cheong.ecommerce_ai_driven.mcp.api;

import com.cheong.ecommerce_ai_driven.mcp.dto.ChatInput;
import com.cheong.ecommerce_ai_driven.mcp.dto.UserIntentDTO;
import com.cheong.ecommerce_ai_driven.mcp.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Flux<UserIntentDTO> chat(@RequestBody ChatInput chatInput){
        return chatService.chat(chatInput);
    }
}

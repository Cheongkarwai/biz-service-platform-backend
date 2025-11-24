package com.cheong.ecommerce_ai_driven.mcp.service;

import com.cheong.ecommerce_ai_driven.mcp.dto.ChatInput;
import com.cheong.ecommerce_ai_driven.mcp.dto.UserIntentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final ChatClient chatClient;

    private final ObjectMapper objectMapper;

    public ChatService(ChatModel chatModel,
                       ObjectMapper objectMapper) {
        this.chatClient = ChatClient.create(chatModel);
        this.objectMapper = objectMapper;
    }

    public Flux<UserIntentDTO> chat(ChatInput chatInput) {
        return chatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec.text(
                        """
                                        You are an intent classifier and helper.
                                
                                                       Following intents are supported:
                                                       - show_services  // when the user clearly wants to see available services
                                                       - unknown        // when the request is unclear, incomplete, or does not match known intents
                                
                                                       Rules:
                                                       - If the user input clearly matches a supported intent, set `intent` accordingly and return a helpful `message`.
                                                       - If the user input is ambiguous, incomplete, or not related to supported intents,
                                                         set `intent` = "unknown" AND in `message` ask a short, specific clarifying question
                                                         to get more relevant information. Example questions:
                                                         - "Which type of service are you interested in?"
                                                         - "Could you describe what you want to do in more detail?"
                                
                                                       Respond in NDJSON format.
                                                       Each line MUST be a valid JSON object with the following shape:
                                                       {
                                                         "intent": string,  // one of: "show_services", "unknown"
                                                         "message": string   // response or clarifying question to the user
                                                       }
                                                       Do not include any additional fields.
                                """
                ))
                .user(promptUserSpec -> promptUserSpec.text(chatInput.getPrompt()))
                .stream()
                .content()
                .transform(this::toJsonChunk)
                .transform(this::toDtoFlux);
    }

    private Flux<String> toJsonChunk(Flux<String> tokenFlux) {
        return Flux.create(sink -> {
            StringBuilder buffer = new StringBuilder();

            tokenFlux.subscribe(
                    token -> {
                        buffer.append(token);

                        int newlineIndex;
                        // Handle all complete lines currently in the buffer
                        while ((newlineIndex = buffer.indexOf("\n")) >= 0) {
                            String candidate = buffer.substring(0, newlineIndex).trim();
                            buffer.delete(0, newlineIndex + 1);

                            if (candidate.isEmpty()) {
                                continue;
                            }

                            // Only emit if it looks like a complete JSON object
                            if (candidate.startsWith("{") && candidate.endsWith("}")) {
                                sink.next(candidate);
                            } else {
                                // Not a complete JSON object yet; prepend back to buffer
                                buffer.insert(0, candidate);
                                // Break to wait for more tokens to complete this object
                                break;
                            }
                        }
                    },
                    sink::error,
                    () -> {
                        String remaining = buffer.toString().trim();
                        if (!remaining.isEmpty()) {
                            // Last chance: if the remaining buffer looks like a full JSON object, emit it.
                            if (remaining.startsWith("{") && remaining.endsWith("}")) {
                                sink.next(remaining);
                            }
                            // If it doesn't look complete, we drop it instead of emitting broken JSON.
                        }
                        sink.complete();
                    }
            );
        });
    }

    private Flux<UserIntentDTO> toDtoFlux(Flux<String> ndjsonLines) {
        return ndjsonLines
                .filter(line -> !line.isBlank())
                .handle((line, sink) -> {
                    try {
                        UserIntentDTO dto = objectMapper.readValue(line, UserIntentDTO.class);
                        sink.next(dto);
                    } catch (JsonProcessingException e) {
                        // Log and skip malformed JSON instead of failing the whole stream.
                        System.err.println("Skipping invalid NDJSON line: " + line);
                        e.printStackTrace();
                    }
                });
    }
}

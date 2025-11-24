package com.cheong.ecommerce_ai_driven.mcp.tool;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TestTool {

    @McpTool(name = "tool", description = "Adds two numbers")
    public Mono<Integer> addReactive(
            @McpToolParam(description = "First number", required = true)
            int a,
            @McpToolParam(description = "Second number", required = true)
            int b) {

        // The addition is performed immediately on the calling thread (non-blocking).
        int result = a + b;

        // Wrap the result in a Mono
        return Mono.just(result);
    }
}

package com.cheong.ecommerce_ai_driven.mcp.tool;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ToolsConfig {

    @McpTool(name = "getBusinessesByServiceId")
    public Mono<String> getBusinessesByServiceId(String serviceId) {
        return Mono.just("Hello World");
    }
}

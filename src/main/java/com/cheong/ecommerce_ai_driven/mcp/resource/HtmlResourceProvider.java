package com.cheong.ecommerce_ai_driven.mcp.resource;

import io.modelcontextprotocol.spec.McpSchema;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class HtmlResourceProvider {

    @McpResource(uri = "ui://widgets/show_services", name = "HTML Page", description = "Provides a specific HTML page")
    public Mono<McpSchema.ReadResourceResult> getServiceListingPage() {
        return Mono.just(new McpSchema.ReadResourceResult(List.of(
                new McpSchema.TextResourceContents(
                        "ui://widgets/show_services",
                        "text/uri-list",
                        "http://localhost:8080/api/v1/ui/index"
                )
        )));

    }
}

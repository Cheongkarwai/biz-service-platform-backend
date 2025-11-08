package com.cheong.ecommerce_ai_driven.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupabaseCdcWebhookPayload<T> {

    private String type;

    private String table;

    private T record;

    @JsonProperty("old_record")
    private T oldRecord; // For UPDATE and DELETE operations

    private String schema;
}
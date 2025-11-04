package com.cheong.ecommerce_ai_driven.common.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("outbox_event")
public class OutboxEvent {

    @Id
    private String id;

    @Column("aggregate_id")
    private String aggregateId;

    @Column("aggregate_type")
    private String aggregateType;

    @Column("event_type")
    private String eventType;

    private String payload;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("is_published")
    private boolean isPublished;

}

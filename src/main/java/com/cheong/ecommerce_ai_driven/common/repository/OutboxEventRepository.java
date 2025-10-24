package com.cheong.ecommerce_ai_driven.common.repository;


import com.cheong.ecommerce_ai_driven.common.dto.OutboxEvent;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface OutboxEventRepository extends R2dbcRepository<OutboxEvent, String> {
}

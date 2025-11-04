package com.cheong.ecommerce_ai_driven.common.data;


import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface OutboxEventRepository extends R2dbcRepository<OutboxEvent, String> {
}

package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.company.entity.Service;
import reactor.core.publisher.Mono;

public interface CustomServiceRepository {

    Mono<Connection<Service>> findAll(String after, String before, int limit);
}

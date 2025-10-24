package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.company.entity.Business;
import reactor.core.publisher.Mono;

public interface CustomCompanyRepository {

    Mono<Connection<Business>> findAll(String after, String before, int limit);
}

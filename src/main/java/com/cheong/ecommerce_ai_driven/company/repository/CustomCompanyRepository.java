package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.company.entity.Business;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CustomCompanyRepository {

    Mono<Connection<Business>> findAll(String after, String before, int limit, List<String> serviceIds);
}

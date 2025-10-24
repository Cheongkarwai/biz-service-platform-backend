package com.cheong.ecommerce_ai_driven.customer.repository;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.customer.entity.Customer;
import reactor.core.publisher.Mono;

public interface CustomCustomerRepository {

    Mono<Connection<Customer>> findAll(String after, String before, int limit);
}

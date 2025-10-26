package com.cheong.ecommerce_ai_driven.account.repository;

import com.cheong.ecommerce_ai_driven.account.entity.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends R2dbcRepository<Account, String> {
    Mono<Account> findByCustomerId(String customerId);
}

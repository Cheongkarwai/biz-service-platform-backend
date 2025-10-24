package com.cheong.ecommerce_ai_driven.account.repository;

import com.cheong.ecommerce_ai_driven.account.entity.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AccountRepository extends R2dbcRepository<Account, String> {
}

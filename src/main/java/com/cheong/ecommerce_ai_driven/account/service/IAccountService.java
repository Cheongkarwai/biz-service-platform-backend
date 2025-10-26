package com.cheong.ecommerce_ai_driven.account.service;

import com.cheong.ecommerce_ai_driven.account.dto.AccountDTO;
import com.cheong.ecommerce_ai_driven.account.entity.Account;
import com.cheong.ecommerce_ai_driven.account.input.AccountInput;
import reactor.core.publisher.Mono;

public interface IAccountService {

    Mono<AccountDTO> suspend(String accountId);
    Mono<AccountDTO> deactivate(String accountId);
    Mono<AccountDTO> activate(String accountId);

    Mono<AccountDTO> getAccountById(String accountId);
    Mono<Account> findById(String accountId);
    Mono<Account> findByCustomerId(String customerId);
    Mono<AccountDTO> save(AccountInput accountInput);
    Mono<Account> save(Account account);
}

package com.cheong.ecommerce_ai_driven.account.service;

import com.cheong.ecommerce_ai_driven.account.dto.AccountDTO;
import com.cheong.ecommerce_ai_driven.account.input.AccountInput;
import com.cheong.ecommerce_ai_driven.account.mapper.AccountMapper;
import com.cheong.ecommerce_ai_driven.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public Mono<AccountDTO> getAccountById(String accountId){
        return accountRepository.findById(accountId)
                .map(accountMapper::mapToAccountDTO);

    }

    @Override
    public Mono<AccountDTO> suspend(String accountId) {
        return getAccountById(accountId)
                .flatMap(AccountDTO::suspend);
    }

    @Override
    public Mono<AccountDTO> deactivate(String accountId) {
        return getAccountById(accountId)
                .flatMap(AccountDTO::deactivate);
    }

    @Override
    public Mono<AccountDTO> activate(String accountId){
        return getAccountById(accountId)
                .flatMap(AccountDTO::activate);
    }

    @Override
    public Mono<AccountDTO> save(AccountInput accountInput) {
        return Mono.just(accountInput)
                .map(accountMapper::mapToAccount)
                .doOnNext(System.out::println)
                .flatMap(accountRepository::save)
                .map(accountMapper::mapToAccountDTO);
    }
}

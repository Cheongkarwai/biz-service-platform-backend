package com.cheong.ecommerce_ai_driven.account.state;

import com.cheong.ecommerce_ai_driven.account.dto.AccountDTO;
import com.cheong.ecommerce_ai_driven.account.dto.AccountStatus;
import com.cheong.ecommerce_ai_driven.account.exception.AccountStateInvalidException;
import com.cheong.ecommerce_ai_driven.account.mapper.AccountMapper;
import com.cheong.ecommerce_ai_driven.account.repository.AccountRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ActiveAccountState implements AccountState{

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public ActiveAccountState(AccountRepository accountRepository,
                              AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Mono<AccountDTO> activate(AccountDTO account) {
        return Mono.error(new AccountStateInvalidException("Account is already active"));
    }

    @Override
    public Mono<AccountDTO> suspend(AccountDTO accountDTO) {
        return accountRepository.findById(accountDTO.getId())
                .map(accountMapper::mapToSuspendedAccount)
                .flatMap(accountRepository::save)
                .map(accountMapper::mapToAccountDTO);
    }

    @Override
    public Mono<AccountDTO> deactivate(AccountDTO accountDTO) {
        return accountRepository.findById(accountDTO.getId())
                .map(accountMapper::mapToInactiveAccount)
                .flatMap(accountRepository::save)
                .map(accountMapper::mapToAccountDTO);
    }

    @Override
    public AccountStatus getName() {
        return AccountStatus.ACTIVE;
    }

}

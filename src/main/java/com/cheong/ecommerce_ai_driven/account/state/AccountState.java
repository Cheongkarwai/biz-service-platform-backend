package com.cheong.ecommerce_ai_driven.account.state;

import com.cheong.ecommerce_ai_driven.account.dto.AccountDTO;
import com.cheong.ecommerce_ai_driven.account.dto.AccountStatus;
import reactor.core.publisher.Mono;

public interface AccountState {

    Mono<AccountDTO> activate(AccountDTO account);

    Mono<AccountDTO> suspend(AccountDTO account);

    Mono<AccountDTO> deactivate(AccountDTO account);

    AccountStatus getName();
}

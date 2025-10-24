package com.cheong.ecommerce_ai_driven.account.dto;

import com.cheong.ecommerce_ai_driven.account.state.AccountState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import reactor.core.publisher.Mono;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String id;

    private AccountStatus status;

    @JsonIgnore
    private AccountState accountState;

    public Mono<AccountDTO> activate() {
        return accountState.activate(this);
    }

    public Mono<AccountDTO> suspend() {
        return accountState.suspend(this);
    }

    public Mono<AccountDTO> deactivate() {
        return accountState.deactivate(this);
    }

}

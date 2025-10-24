package com.cheong.ecommerce_ai_driven.account.factory;

import com.cheong.ecommerce_ai_driven.account.dto.AccountStatus;
import com.cheong.ecommerce_ai_driven.account.state.AccountState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountStateFactory {

    private final List<AccountState> accountStates;

    public AccountStateFactory(List<AccountState> accountStates) {
        this.accountStates = accountStates;
    }

    public AccountState mapAccountState(AccountStatus name) {
        return accountStates.stream()
                .filter(accountState -> accountState.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account state not found"));
    }
}

package com.cheong.ecommerce_ai_driven.account.mapper;

import com.cheong.ecommerce_ai_driven.account.dto.AccountDTO;
import com.cheong.ecommerce_ai_driven.account.dto.AccountStatus;
import com.cheong.ecommerce_ai_driven.account.entity.Account;
import com.cheong.ecommerce_ai_driven.account.factory.AccountStateFactory;
import com.cheong.ecommerce_ai_driven.account.input.AccountInput;
import com.cheong.ecommerce_ai_driven.account.state.AccountState;
import com.cheong.ecommerce_ai_driven.account.state.ActiveAccountState;
import com.cheong.ecommerce_ai_driven.account.state.InactiveAccountState;
import com.cheong.ecommerce_ai_driven.account.state.SuspendedAccountState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AccountMapper {

    @Autowired
    private ApplicationContext applicationContext;

    @Mapping(target = "accountState", source = "status", qualifiedByName = "mapAccountState")
    public abstract AccountDTO mapToAccountDTO(Account account);

    public abstract Account mapToAccount(AccountDTO accountDTO);

    @Mapping(target = "status", constant = "SUSPENDED")
    public abstract Account mapToSuspendedAccount(Account account);

    @Mapping(target = "status", constant = "INACTIVE")
    public abstract Account mapToInactiveAccount(Account account);

    @Mapping(target = "status", constant = "ACTIVE")
    public abstract Account mapToActiveAccount(Account account);

    @Named("mapAccountState")
    public AccountState mapAccountState(AccountStatus status) {
        return switch (status) {
            case ACTIVE -> applicationContext.getBean(ActiveAccountState.class);
            case INACTIVE -> applicationContext.getBean(InactiveAccountState.class);
            case SUSPENDED -> applicationContext.getBean(SuspendedAccountState.class);
            default -> null;
        };
    }

    @Mapping(source = "status", target = "status")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "businessId", target = "businessId")
    public abstract Account mapToAccount(AccountInput accountInput);

}

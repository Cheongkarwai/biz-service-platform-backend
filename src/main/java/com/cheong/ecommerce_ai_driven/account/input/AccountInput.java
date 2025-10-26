package com.cheong.ecommerce_ai_driven.account.input;

import com.cheong.ecommerce_ai_driven.account.dto.AccountStatus;
import com.cheong.ecommerce_ai_driven.account.dto.AccountType;
import com.cheong.ecommerce_ai_driven.account.state.AccountState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInput {

    private String id;

    private AccountStatus status;

    private String customerId;

    private String businessId;

    private AccountType type;

}

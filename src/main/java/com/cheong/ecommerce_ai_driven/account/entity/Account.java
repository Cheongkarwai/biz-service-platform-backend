package com.cheong.ecommerce_ai_driven.account.entity;

import com.cheong.ecommerce_ai_driven.account.dto.AccountStatus;
import com.cheong.ecommerce_ai_driven.account.dto.AccountType;
import com.cheong.ecommerce_ai_driven.common.dto.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("account")
public class Account implements BaseEntity {

    @Id
    private String id;

    private AccountType type;

    private AccountStatus status;

    @Column("customer_id")
    private UUID customerId;

    @Column("business_id")
    private UUID businessId;

}

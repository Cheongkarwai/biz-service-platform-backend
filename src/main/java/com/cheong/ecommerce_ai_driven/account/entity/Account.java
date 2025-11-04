package com.cheong.ecommerce_ai_driven.account.entity;

import com.cheong.ecommerce_ai_driven.account.dto.AccountStatus;
import com.cheong.ecommerce_ai_driven.account.dto.AccountType;
import com.cheong.ecommerce_ai_driven.common.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
    private String customerId;

    @Column("business_id")
    private String businessId;

    @Column("supabase_user_id")
    private String supabaseUserId;

    @Version
    private Long version;
}

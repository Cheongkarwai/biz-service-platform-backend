package com.cheong.ecommerce_ai_driven.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("business_address")
public class BusinessAddress implements Persistable<UUID> {

    @Id
    @Column("business_id")
    private UUID businessId;

    @Column("address_id")
    private UUID addressId;

    @Transient
    private boolean isNew;

    @Override
    public UUID getId() {
        return businessId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

}

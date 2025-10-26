package com.cheong.ecommerce_ai_driven.company.entity;

import com.cheong.ecommerce_ai_driven.common.dto.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("business_address")
public class BusinessAddress implements BaseEntity {

    @Id
    @Column("business_id")
    private String id;

    @Column("address_id")
    private String addressId;

    @Version
    private Long version;

    public BusinessAddress(String businessId, String addressId) {
        this.id = businessId;
        this.addressId = addressId;
    }
}

package com.cheong.ecommerce_ai_driven.company.entity;

import com.cheong.ecommerce_ai_driven.common.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("business_address")
public class BusinessAddress implements BaseEntity {

    @Id
    private String id;

    @Column("business_id")
    private String businessId;

    @Column("address_id")
    private String addressId;

    @Version
    private Long version;

    public BusinessAddress(String businessId, String addressId) {
        this.businessId = businessId;
        this.addressId = addressId;
    }
}

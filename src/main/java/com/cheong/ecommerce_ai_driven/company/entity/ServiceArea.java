package com.cheong.ecommerce_ai_driven.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("service_area")
public class ServiceArea {

    @Id
    private String id;

    @Column("business_service_id")
    private String businessServiceId;

    private String area;
}

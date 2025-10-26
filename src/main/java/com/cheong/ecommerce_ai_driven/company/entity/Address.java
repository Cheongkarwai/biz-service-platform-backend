package com.cheong.ecommerce_ai_driven.company.entity;

import com.cheong.ecommerce_ai_driven.company.dto.AddressType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("address")
public class Address {

    @Id
    private String id;

    private String street;

    private String city;

    private String state;

    private String zip;

    private AddressType type;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}

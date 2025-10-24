package com.cheong.ecommerce_ai_driven.company.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("business")
public class Business {

    @Id
    private UUID id;

    private String name;

    @Column("mobile_number")
    private String mobileNumber;

    private String email;

    private String overview;

    @Column("joined_at")
    private LocalDateTime joinedAt;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

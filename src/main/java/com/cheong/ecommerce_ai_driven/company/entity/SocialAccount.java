package com.cheong.ecommerce_ai_driven.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( "social_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccount {

    @Id
    private String username;

    private String url;

    @Column("business_id")
    private String business_Id;
}

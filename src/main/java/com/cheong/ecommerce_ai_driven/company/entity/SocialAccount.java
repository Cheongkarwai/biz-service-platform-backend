package com.cheong.ecommerce_ai_driven.company.entity;

import com.cheong.ecommerce_ai_driven.common.dto.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( "social_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccount implements BaseEntity {

    @Id
    @Column("username")
    private String id;

    private String url;

    @Column("business_id")
    private String business_Id;

    @Version
    private Long version;
}

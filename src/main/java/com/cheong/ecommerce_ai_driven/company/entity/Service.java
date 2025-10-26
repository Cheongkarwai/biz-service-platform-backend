package com.cheong.ecommerce_ai_driven.company.entity;

import com.cheong.ecommerce_ai_driven.common.dto.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("service")
public class Service implements BaseEntity {

    @Id
    private String id;

    private String name;

    @Version
    private Long version;

}

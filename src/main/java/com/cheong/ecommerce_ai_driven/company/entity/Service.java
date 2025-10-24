package com.cheong.ecommerce_ai_driven.company.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("service")
public class Service {

    @Id
    private String id;

    private String name;

}

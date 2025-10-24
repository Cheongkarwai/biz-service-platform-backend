package com.cheong.ecommerce_ai_driven.product.entity;

import com.cheong.ecommerce_ai_driven.product.dto.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("product")
public class Product {

    @Id
    private String id;

    private String name;

    private String description;

    private Status status;
}

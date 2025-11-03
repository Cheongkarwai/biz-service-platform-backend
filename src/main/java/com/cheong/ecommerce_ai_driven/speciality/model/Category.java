package com.cheong.ecommerce_ai_driven.speciality.model;

import com.cheong.ecommerce_ai_driven.common.dto.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements BaseEntity {

    @Id
    private String id;

    private String name;

    @Version
    private Long version;
}

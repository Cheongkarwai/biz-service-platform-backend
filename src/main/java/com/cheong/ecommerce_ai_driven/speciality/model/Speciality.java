package com.cheong.ecommerce_ai_driven.speciality.model;

import com.cheong.ecommerce_ai_driven.common.data.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("service")
public class Speciality implements BaseEntity {

    @Id
    private String id;

    private String name;

    @Version
    private Long version;

}

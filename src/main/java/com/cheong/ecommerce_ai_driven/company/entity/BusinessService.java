package com.cheong.ecommerce_ai_driven.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("company_service")
public class BusinessService {

    private String id;

    @Id
    @Embedded.Nullable(prefix = "id_")
    private BusinessServiceId businessServiceId;

}

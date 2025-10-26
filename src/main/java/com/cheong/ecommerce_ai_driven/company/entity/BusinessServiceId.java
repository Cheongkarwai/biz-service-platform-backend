package com.cheong.ecommerce_ai_driven.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

@Data
@NoArgsConstructor
public class BusinessServiceId {

    private String companyId;

    private String serviceId;

    @Version
    private Long version;

    public BusinessServiceId(String companyId, String serviceId) {
        this.companyId = companyId;
        this.serviceId = serviceId;
    }
}

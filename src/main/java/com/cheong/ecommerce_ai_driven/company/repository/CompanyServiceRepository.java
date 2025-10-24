package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.company.entity.BusinessService;
import com.cheong.ecommerce_ai_driven.company.entity.BusinessServiceId;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CompanyServiceRepository extends R2dbcRepository<BusinessService, BusinessServiceId> {
}

package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.company.entity.Business;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface CompanyRepository extends R2dbcRepository<Business, String>, CustomCompanyRepository{
}

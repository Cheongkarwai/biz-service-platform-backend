package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.company.entity.Service;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface ServiceRepository extends R2dbcRepository<Service, UUID>, CustomServiceRepository {
}

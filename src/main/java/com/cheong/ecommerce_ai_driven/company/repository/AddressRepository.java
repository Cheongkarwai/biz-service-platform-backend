package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.company.entity.Address;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface AddressRepository extends R2dbcRepository<Address, UUID> {
}

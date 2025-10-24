package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.company.entity.BusinessAddress;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface BusinessAddressRepository extends R2dbcRepository<BusinessAddress, String> {

    Flux<BusinessAddress> findAllByBusinessIdAndAddressId(String businessId, String addressId);

    Flux<BusinessAddress> findAllByBusinessId(UUID id);
}

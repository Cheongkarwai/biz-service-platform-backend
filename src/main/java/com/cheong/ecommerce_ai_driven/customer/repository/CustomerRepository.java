package com.cheong.ecommerce_ai_driven.customer.repository;

import com.cheong.ecommerce_ai_driven.customer.dto.CustomerDTO;
import com.cheong.ecommerce_ai_driven.customer.entity.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends R2dbcRepository<Customer, String>, CustomCustomerRepository {

    Mono<Boolean> existsByEmailAddress(String email);

    Mono<Boolean> existsByMobileNumber(String mobileNumber);

    Mono<Customer> findByEmailAddress(String emailAddress);
}

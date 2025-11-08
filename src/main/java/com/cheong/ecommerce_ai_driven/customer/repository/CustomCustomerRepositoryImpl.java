package com.cheong.ecommerce_ai_driven.customer.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.data.CursorPaginationRepository;
import com.cheong.ecommerce_ai_driven.customer.entity.Customer;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository, CursorPaginationRepository<Customer> {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public CustomCustomerRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public R2dbcEntityTemplate getR2dbcEntityTemplate() {
        return r2dbcEntityTemplate;
    }

    @Override
    public Mono<Connection<Customer>> findAll(String after, String before, int limit) {
        Criteria criteria = Criteria.empty();
        return findAll(after, before, limit, criteria, "id", Customer.class, Customer::getId);
    }
}

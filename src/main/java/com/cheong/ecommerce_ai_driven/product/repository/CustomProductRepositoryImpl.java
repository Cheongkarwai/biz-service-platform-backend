package com.cheong.ecommerce_ai_driven.product.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.data.CursorPaginationRepository;
import com.cheong.ecommerce_ai_driven.product.entity.Product;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository, CursorPaginationRepository<Product> {
    
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    
    public CustomProductRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Connection<Product>> findAll(String after, String before, int limit) {
        Criteria criteria = Criteria.empty();
        return findAll(after, before, limit, criteria, "id", Product.class, Product::getId);
    }

    @Override
    public R2dbcEntityTemplate getR2dbcEntityTemplate() {
        return r2dbcEntityTemplate;
    }

}

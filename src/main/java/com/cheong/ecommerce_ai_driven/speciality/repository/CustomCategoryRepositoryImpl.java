package com.cheong.ecommerce_ai_driven.speciality.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.data.CursorPaginationRepository;
import com.cheong.ecommerce_ai_driven.speciality.model.Category;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;

public class CustomCategoryRepositoryImpl implements CustomCategoryRepository, CursorPaginationRepository<Category> {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public CustomCategoryRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Connection<Category>> findAll(String after,
                                              String before,
                                              int limit) {
        return findAll(after, before, limit, "id", Category.class, Category::getId);
    }

    @Override
    public R2dbcEntityTemplate getR2dbcEntityTemplate() {
        return r2dbcEntityTemplate;
    }
}

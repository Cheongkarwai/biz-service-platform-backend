package com.cheong.ecommerce_ai_driven.speciality.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.data.CursorPaginationRepository;
import com.cheong.ecommerce_ai_driven.speciality.model.Speciality;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomSpecialityRepositoryImpl implements CustomSpecialityRepository, CursorPaginationRepository<Speciality> {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public CustomSpecialityRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Connection<Speciality>> findAll(String after,
                                                String before,
                                                int limit) {
        return findAll(after, before, limit, "id", Speciality.class, Speciality::getId);
    }

    @Override
    public R2dbcEntityTemplate getR2dbcEntityTemplate() {
        return r2dbcEntityTemplate;
    }
}

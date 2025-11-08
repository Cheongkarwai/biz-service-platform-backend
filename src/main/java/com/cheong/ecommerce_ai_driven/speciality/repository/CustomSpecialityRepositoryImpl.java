package com.cheong.ecommerce_ai_driven.speciality.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.data.CursorPaginationRepository;
import com.cheong.ecommerce_ai_driven.speciality.model.Speciality;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

import java.util.List;

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
        Criteria criteria = Criteria.empty();
        return findAll(after, before, limit, criteria, "id", Speciality.class, Speciality::getId);
    }

    @Override
    public Mono<Connection<Speciality>> findAll(String after, String before, int limit, List<String> categoryIds) {
        Criteria criteria = Criteria.empty();
        if(!CollectionUtils.isEmpty(categoryIds)){
            criteria = criteria.and("category_id").in(categoryIds);
        }
        return findAll(after, before, limit, criteria, "id", Speciality.class, Speciality::getId);
    }

    @Override
    public R2dbcEntityTemplate getR2dbcEntityTemplate() {
        return r2dbcEntityTemplate;
    }
}

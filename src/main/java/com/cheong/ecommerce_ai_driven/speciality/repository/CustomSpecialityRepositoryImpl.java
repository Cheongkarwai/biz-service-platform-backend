package com.cheong.ecommerce_ai_driven.speciality.repository;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.speciality.model.Speciality;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Repository
public class CustomSpecialityRepositoryImpl implements CustomSpecialityRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public CustomSpecialityRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Connection<Speciality>> findAll(String after,
                                                String before,
                                                int limit) {

        Criteria criteria = Criteria.empty();

        if(StringUtils.hasText(after)){
            criteria = criteria.and("id").greaterThan(after);
        }

        if(StringUtils.hasText(before)){
            criteria = criteria.and("id").lessThan(before);
        }

        Sort sort = StringUtils.hasText(before) ?
                Sort.by(Sort.Order.desc("id")) :
                Sort.by(Sort.Order.asc("id"));

        Query query = Query.query(criteria).sort(sort).limit(limit + 1);

        return r2dbcEntityTemplate.select(query, Speciality.class)
                .collectList()
                .map(services ->
                        Connection.createConnection(services, before, limit, service-> service.getId().toString()));
    }
}

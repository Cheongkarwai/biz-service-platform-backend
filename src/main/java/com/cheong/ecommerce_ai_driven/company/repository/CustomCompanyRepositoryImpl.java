package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.data.CursorPaginationRepository;
import com.cheong.ecommerce_ai_driven.company.entity.Business;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class CustomCompanyRepositoryImpl implements CustomCompanyRepository, CursorPaginationRepository<Business> {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public CustomCompanyRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Connection<Business>> findAll(String after,
                                              String before,
                                              int limit,
                                              List<String> serviceIds) {
        Criteria criteria = Criteria.empty();
        if(!CollectionUtils.isEmpty(serviceIds)){
            criteria = criteria.and("service_id").in(serviceIds);
        }
        return findAll(after, before, limit, criteria, "id", Business.class, Business::getId);
    }

    @Override
    public R2dbcEntityTemplate getR2dbcEntityTemplate() {
        return r2dbcEntityTemplate;
    }
}

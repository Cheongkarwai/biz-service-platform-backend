package com.cheong.ecommerce_ai_driven.company.repository;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.company.entity.Business;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Repository
public class CustomCompanyRepositoryImpl implements CustomCompanyRepository{

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public CustomCompanyRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Connection<Business>> findAll(String after,
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

        return r2dbcEntityTemplate.select(query, Business.class)
                .collectList()
                .map(companies ->
                        Connection.createConnection(companies, before, limit, business -> business.getId().toString()));
    }

}

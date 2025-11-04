package com.cheong.ecommerce_ai_driven.common.repository;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface CursorPaginationRepository<T> {

   R2dbcEntityTemplate getR2dbcEntityTemplate();

    default Mono<Connection<T>> findAll(
            String after,
            String before,
            int limit,
            String cursorFieldName,
            Class<T> entityClass,
            Function<T, String> idExtractor) {

        Criteria criteria = Criteria.empty();

        if (StringUtils.hasText(after)) {
            criteria = criteria.and(cursorFieldName).greaterThan(after);
        }

        if (StringUtils.hasText(before)) {
            criteria = criteria.and(cursorFieldName).lessThan(before);
        }

        Sort sort = StringUtils.hasText(before) ?
                Sort.by(Sort.Order.desc(cursorFieldName)) :
                Sort.by(Sort.Order.asc(cursorFieldName));

        Query query = Query.query(criteria).sort(sort).limit(limit + 1);

        return getR2dbcEntityTemplate().select(query, entityClass)
                .collectList()
                .map(entities ->
                        Connection.createConnection(entities, after, before, limit, idExtractor));
    }
}

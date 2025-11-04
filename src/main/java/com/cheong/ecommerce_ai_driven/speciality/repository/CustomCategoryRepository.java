package com.cheong.ecommerce_ai_driven.speciality.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.speciality.model.Category;
import reactor.core.publisher.Mono;

public interface CustomCategoryRepository{

    Mono<Connection<Category>> findAll(String after, String before, int limit);
}

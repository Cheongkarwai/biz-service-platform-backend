package com.cheong.ecommerce_ai_driven.product.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.product.entity.Product;
import reactor.core.publisher.Mono;

public interface CustomProductRepository {

    Mono<Connection<Product>> findAll(String after, String before, int limit);
}

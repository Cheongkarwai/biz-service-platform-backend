package com.cheong.ecommerce_ai_driven.product.repository;

import com.cheong.ecommerce_ai_driven.product.dto.ProductDTO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ProductRepository extends R2dbcRepository<ProductDTO, String>, CustomProductRepository {
}

package com.cheong.ecommerce_ai_driven.product.service;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.util.ConnectionUtil;
import com.cheong.ecommerce_ai_driven.product.dto.ProductDTO;
import com.cheong.ecommerce_ai_driven.product.mapper.ProductMapper;
import com.cheong.ecommerce_ai_driven.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Mono<ProductDTO> findById(String id){
        return productRepository.findById(id);
    }

    public Mono<Connection<ProductDTO>> findAll(String after, String before, int limit) {
        return productRepository.findAll(after, before, limit)
                .flatMap(productConnection -> ConnectionUtil.mapConnection(productConnection, productMapper::mapToProductDTO));

    }

}

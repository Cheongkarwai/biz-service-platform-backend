package com.cheong.ecommerce_ai_driven.product.service;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.product.dto.ProductDTO;
import com.cheong.ecommerce_ai_driven.product.dto.ProductInput;
import com.cheong.ecommerce_ai_driven.product.dto.Status;
import com.cheong.ecommerce_ai_driven.product.mapper.ProductMapper;
import com.cheong.ecommerce_ai_driven.product.repository.CustomProductRepository;
import com.cheong.ecommerce_ai_driven.product.repository.ProductRepository;
import com.cheong.ecommerce_ai_driven.specification.ProductSpecification;
import com.cheong.ecommerce_ai_driven.specification.Specification;
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
                .flatMap(productConnection -> {
                    Connection<ProductDTO> connection = new Connection<>();
                    connection.setPageInfo(productConnection.getPageInfo());
                    return Flux.fromIterable(productConnection.getEdges())
                            .map(edge -> {
                                Connection<ProductDTO>.Edge connectionEdge = connection.new Edge();
                                connectionEdge.setNode(productMapper.mapToProductDTO(edge.getNode()));
                                connectionEdge.setCursor(edge.getCursor());
                                return connectionEdge;
                            })
                            .collectList()
                            .map(edges -> {
                                connection.setEdges(edges);
                                return connection;
                            });
                });

    }

//    public Mono<Void> create(ProductInput productInput){
//
//    }
}

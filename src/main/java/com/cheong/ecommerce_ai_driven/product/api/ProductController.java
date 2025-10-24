package com.cheong.ecommerce_ai_driven.product.api;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.product.dto.ProductDTO;
import com.cheong.ecommerce_ai_driven.product.dto.ProductInput;
import com.cheong.ecommerce_ai_driven.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> findById(@PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<Connection<ProductDTO>> findAll(@RequestParam(required = false) String after,
                              @RequestParam(required = false) String before,
                              @RequestParam int limit
                                    ) {
        return productService.findAll(after, before, limit);
    }

    @PostMapping
    public Mono<Void> create(@RequestBody ProductInput productInput){
        return Mono.empty();
    }
}

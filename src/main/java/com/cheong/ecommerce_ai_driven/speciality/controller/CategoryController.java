package com.cheong.ecommerce_ai_driven.speciality.controller;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.speciality.dto.CategoryDTO;
import com.cheong.ecommerce_ai_driven.speciality.dto.CategoryInput;
import com.cheong.ecommerce_ai_driven.speciality.model.Category;
import com.cheong.ecommerce_ai_driven.speciality.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public Mono<CategoryDTO> findById(@PathVariable String id){
        return categoryService.findById(id);
    }

    @GetMapping
    public Mono<Connection<CategoryDTO>> getAll(@RequestParam int limit,
                                                @RequestParam(required = false) String before,
                                                @RequestParam(required = false) String after){
        return categoryService.findAll(after, before, limit);
    }

    @PostMapping
    public Mono<Category> create(@Valid @RequestBody CategoryInput categoryInput){
        return categoryService.save(categoryInput);
    }
}

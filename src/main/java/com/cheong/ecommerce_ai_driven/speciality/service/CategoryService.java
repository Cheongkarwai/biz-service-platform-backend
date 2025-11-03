package com.cheong.ecommerce_ai_driven.speciality.service;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.common.util.ConnectionUtil;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessDTO;
import com.cheong.ecommerce_ai_driven.speciality.dto.CategoryDTO;
import com.cheong.ecommerce_ai_driven.speciality.dto.CategoryInput;
import com.cheong.ecommerce_ai_driven.speciality.model.Category;
import com.cheong.ecommerce_ai_driven.speciality.model.CategoryMapper;
import com.cheong.ecommerce_ai_driven.speciality.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Mono<Connection<CategoryDTO>> findAll(String after, String before, int limit) {
        return categoryRepository.findAll(after, before, limit)
                .flatMap(categoryConnection ->
                        ConnectionUtil.mapConnection(categoryConnection, categoryMapper::mapToCategoryDTO)
                );
    }

    public Mono<Category> save(CategoryInput categoryInput) {
        return categoryRepository.save(categoryMapper.mapToCategory(categoryInput));
    }
}

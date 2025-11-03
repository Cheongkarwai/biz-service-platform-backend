package com.cheong.ecommerce_ai_driven.speciality.model;

import com.cheong.ecommerce_ai_driven.speciality.dto.CategoryDTO;
import com.cheong.ecommerce_ai_driven.speciality.dto.CategoryInput;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    CategoryDTO mapToCategoryDTO(Category category);

    Category mapToCategory(CategoryInput categoryInput);
}

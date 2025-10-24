package com.cheong.ecommerce_ai_driven.product.mapper;

import com.cheong.ecommerce_ai_driven.product.dto.ProductDTO;
import com.cheong.ecommerce_ai_driven.product.dto.ProductInput;
import com.cheong.ecommerce_ai_driven.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDTO mapToProductDTO(Product product);

    Product mapToProduct(ProductInput productInput);
}

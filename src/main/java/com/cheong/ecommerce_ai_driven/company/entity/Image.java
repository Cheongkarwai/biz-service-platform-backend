package com.cheong.ecommerce_ai_driven.company.entity;

import com.cheong.ecommerce_ai_driven.company.dto.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("image")
public class Image {

    private String id;

    private String bucket;

    private String key;

    private String url;

    private String businessId;

    private ImageType type;

}

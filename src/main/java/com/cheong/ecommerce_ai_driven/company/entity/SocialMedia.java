package com.cheong.ecommerce_ai_driven.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("social_media")
public class SocialMedia {

    @Id
    private String id;

    private Platform platform;

    public enum Platform {
        FACEBOOK,
        INSTAGRAM,
        TWITTER
    }
}

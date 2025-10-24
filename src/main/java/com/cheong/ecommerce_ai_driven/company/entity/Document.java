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
@Table("document")
public class Document {

    @Id
    private String id;

    private Type type;

    @Column("business_id")
    private String businessId;


    public enum Type {
        DRIVING_LICENSE,
        IC,
        SSM
    }
}

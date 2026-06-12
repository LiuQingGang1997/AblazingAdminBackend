package com.xingdongadmin.backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "product_type_relation", indexes = {
    @Index(name = "idx_product_id_type", columnList = "product_id")
})
@Comment("产品与类型关联中间表")
public class ProductTypeRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键ID")
    private Long id;

    @Column(name = "product_id", nullable = false)
    @Comment("产品ID")
    private Long productId;

    @Column(name = "type_id", nullable = false)
    @Comment("类型ID")
    private Long typeId;

    public ProductTypeRelation() {}

    public ProductTypeRelation(Long productId, Long typeId) {
        this.productId = productId;
        this.typeId = typeId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getTypeId() { return typeId; }
    public void setTypeId(Long typeId) { this.typeId = typeId; }
}
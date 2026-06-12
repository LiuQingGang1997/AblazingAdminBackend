package com.xingdongadmin.backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "product_brand_relation", indexes = {
    @Index(name = "idx_product_id_brand", columnList = "product_id")
})
@Comment("产品与品牌关联中间表")
public class ProductBrandRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键ID")
    private Long id;

    @Column(name = "product_id", nullable = false)
    @Comment("产品ID")
    private Long productId;

    @Column(name = "brand_id", nullable = false)
    @Comment("品牌ID")
    private Long brandId;

    public ProductBrandRelation() {}

    public ProductBrandRelation(Long productId, Long brandId) {
        this.productId = productId;
        this.brandId = brandId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }
}
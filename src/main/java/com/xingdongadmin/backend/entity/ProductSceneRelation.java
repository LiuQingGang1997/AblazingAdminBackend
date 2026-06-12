package com.xingdongadmin.backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "product_scene_relation", indexes = {
    @Index(name = "idx_product_id_scene", columnList = "product_id")
})
@Comment("产品与场景关联中间表")
public class ProductSceneRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键ID")
    private Long id;

    @Column(name = "product_id", nullable = false)
    @Comment("产品ID")
    private Long productId;

    @Column(name = "scene_id", nullable = false)
    @Comment("场景ID")
    private Long sceneId;

    public ProductSceneRelation() {}

    public ProductSceneRelation(Long productId, Long sceneId) {
        this.productId = productId;
        this.sceneId = sceneId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getSceneId() { return sceneId; }
    public void setSceneId(Long sceneId) { this.sceneId = sceneId; }
}
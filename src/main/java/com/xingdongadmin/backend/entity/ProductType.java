package com.xingdongadmin.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "product_types", indexes = {
    @Index(name = "idx_brand_id", columnList = "brand_id"),
    @Index(name = "idx_sort_order", columnList = "sort_order")
})
@Comment("产品类型表")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键ID")
    private Long id;

    @Column(nullable = false)
    @Comment("产品类型名称")
    private String name;

    @Column(length = 512)
    @Comment("类型照片")
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    @Comment("产品类型描述")
    private String description;

    // 关联商城品牌
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    @Comment("关联的商城品牌ID")
    private MallBrand mallBrand;

    @Column(nullable = false)
    @Comment("状态：是否启用")
    private Boolean enabled = true;

    @Column(nullable = false)
    @Comment("排序权重")
    private Integer sortOrder = 0;

    @Comment("创建时间")
    private LocalDateTime createdAt;
    
    @Comment("更新时间")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MallBrand getMallBrand() { return mallBrand; }
    public void setMallBrand(MallBrand mallBrand) { this.mallBrand = mallBrand; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
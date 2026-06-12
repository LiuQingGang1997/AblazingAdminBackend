package com.xingdongadmin.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "products")
@Comment("产品主表")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键ID")
    private Long id;

    @Column(nullable = false)
    @Comment("产品名称")
    private String name;

    @Comment("产品型号")
    private String model;
    
    @Column(length = 1024)
    @Comment("产品简介")
    private String summary;
    
    @Column(columnDefinition = "TEXT")
    @Comment("产品详情介绍（富文本）")
    private String detailDescription;
    
    @Column(precision = 10, scale = 2)
    @Comment("产品价格")
    private BigDecimal price;
    
    @Column(length = 512)
    @Comment("产品封面图片")
    private String coverImageUrl;

    @Comment("所属分类ID")
    private Long categoryId;

    @Comment("所属系列ID")
    private Long seriesId;

    // 产品详情页图片（多张），单独建表 product_detail_images
    @ElementCollection
    @CollectionTable(name = "product_detail_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url", length = 512)
    @Comment("产品详情页图片URL")
    private List<String> detailImages = new ArrayList<>();

    // 产品参数介绍（如颜色：蓝色、黑色等），单独建表 product_parameters
    @ElementCollection
    @CollectionTable(name = "product_parameters", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "param_key")
    @Column(name = "param_value", length = 512)
    @Comment("产品参数值")
    private Map<String, String> parameters = new HashMap<>();

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

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getDetailDescription() { return detailDescription; }
    public void setDetailDescription(String detailDescription) { this.detailDescription = detailDescription; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Long getSeriesId() { return seriesId; }
    public void setSeriesId(Long seriesId) { this.seriesId = seriesId; }

    public List<String> getDetailImages() { return detailImages; }
    public void setDetailImages(List<String> detailImages) { this.detailImages = detailImages; }

    public Map<String, String> getParameters() { return parameters; }
    public void setParameters(Map<String, String> parameters) { this.parameters = parameters; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
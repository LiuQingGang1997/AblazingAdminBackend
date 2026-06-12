package com.xingdongadmin.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "mall_brands")
@Comment("商城入驻品牌表")
public class MallBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键ID")
    private Long id;

    @Column(nullable = false)
    @Comment("品牌名称")
    private String name;

    @Column(nullable = false, length = 512)
    @Comment("品牌logo")
    private String logoUrl;

    @Comment("品牌slogan (宣传标语)")
    private String slogan;

    @Column(columnDefinition = "TEXT")
    @Comment("品牌介绍")
    private String introduction;

    @Column(length = 512)
    @Comment("品牌宣传大图")
    private String promoImageUrl;

    @Column(length = 512)
    @Comment("品牌宣传视频")
    private String promoVideoUrl;

    @Column(length = 512)
    @Comment("移动端品牌宣传视频")
    private String mobilePromoVideoUrl;

    @Column(columnDefinition = "TEXT")
    @Comment("品牌详情介绍")
    private String detailDescription;

    @Column(nullable = false)
    @Comment("状态：是否启用（上架/下架）")
    private Boolean enabled = true;

    @Column(nullable = false)
    @Comment("排序权重（升序排列，数值越小越靠前）")
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

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getSlogan() { return slogan; }
    public void setSlogan(String slogan) { this.slogan = slogan; }

    public String getIntroduction() { return introduction; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }

    public String getPromoImageUrl() { return promoImageUrl; }
    public void setPromoImageUrl(String promoImageUrl) { this.promoImageUrl = promoImageUrl; }

    public String getPromoVideoUrl() { return promoVideoUrl; }
    public void setPromoVideoUrl(String promoVideoUrl) { this.promoVideoUrl = promoVideoUrl; }

    public String getMobilePromoVideoUrl() { return mobilePromoVideoUrl; }
    public void setMobilePromoVideoUrl(String mobilePromoVideoUrl) { this.mobilePromoVideoUrl = mobilePromoVideoUrl; }

    public String getDetailDescription() { return detailDescription; }
    public void setDetailDescription(String detailDescription) { this.detailDescription = detailDescription; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
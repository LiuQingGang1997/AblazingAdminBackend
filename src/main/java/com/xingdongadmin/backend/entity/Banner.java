package com.xingdongadmin.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "banners")
@Comment("官网Banner轮播图表")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("主键ID")
    private Long id;

    @Column(name = "image_url", nullable = false)
    @Comment("Banner图片地址")
    private String imageUrl;

    @Column(name = "link_url")
    @Comment("Banner跳转地址")
    private String linkUrl;

    @Column(name = "modifier")
    @Comment("修改用户")
    private String modifier;

    @Column(name = "updated_at")
    @Comment("修改日期")
    private LocalDateTime updatedAt;

    @Column(name = "enabled", nullable = false)
    @Comment("是否启用")
    private Boolean enabled = true;

    @Column(name = "priority", nullable = false)
    @Comment("优先级（数字越大越靠前或视业务而定）")
    private Integer priority = 0;

    @PrePersist
    @PreUpdate
    public void updateTimeStamps() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
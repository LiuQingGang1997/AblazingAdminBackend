package com.xingdongadmin.backend.entity;

import com.xingdongadmin.backend.config.MapConverter;
import com.xingdongadmin.backend.config.StringListConverter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "cases")
public class CaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 案例名称
    private String name;

    // 案例场所地址
    private String address;

    // 案例完成时间
    private String completionTime;

    // 案例首页图片
    private String coverImage;

    // 案例详情照片（支持多张）
    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> detailImages;

    // 案例标题介绍
    @Column(columnDefinition = "TEXT")
    private String titleDescription;

    // 案例特色介绍（案例特色介绍支持map形式，最多支持四个子map）
    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapConverter.class)
    private Map<String, Object> features;

    // 保留通用时间字段
    private LocalDateTime createdAt;
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

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCompletionTime() { return completionTime; }
    public void setCompletionTime(String completionTime) { this.completionTime = completionTime; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public List<String> getDetailImages() { return detailImages; }
    public void setDetailImages(List<String> detailImages) { this.detailImages = detailImages; }

    public String getTitleDescription() { return titleDescription; }
    public void setTitleDescription(String titleDescription) { this.titleDescription = titleDescription; }

    public Map<String, Object> getFeatures() { return features; }
    public void setFeatures(Map<String, Object> features) { this.features = features; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
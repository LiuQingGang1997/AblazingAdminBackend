package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ProductView(
    Long id,
    String name,
    String model,
    String summary,
    String detailDescription,
    BigDecimal price,
    String coverImageUrl,
    
    // 关联 ID
    Long brandId,
    Long sceneId,
    Long typeId,
    Long categoryId,
    Long seriesId,
    
    // 关联 名称 (列表展示需要)
    String brandName,
    String sceneName,
    String typeName,
    String categoryName,
    String seriesName,
    
    // 关联 集合
    List<String> detailImages,
    Map<String, String> parameters,
    
    Boolean enabled,
    Integer sortOrder,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    // 基础的 from，不包含外联表信息，主要用于非 Controller 层或简单的转换
    public static ProductView from(Product product) {
        return new ProductView(
            product.getId(),
            product.getName(),
            product.getModel(),
            product.getSummary(),
            product.getDetailDescription(),
            product.getPrice(),
            product.getCoverImageUrl(),
            null,
            null,
            null,
            product.getCategoryId(),
            product.getSeriesId(),
            null,
            null,
            null,
            null,
            null,
            product.getDetailImages(),
            product.getParameters(),
            product.getEnabled(),
            product.getSortOrder(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}
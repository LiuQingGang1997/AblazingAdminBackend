package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.ProductScene;
import java.time.LocalDateTime;

public record ProductSceneView(
    Long id,
    String name,
    String englishName,
    String imageUrl,
    String videoUrl,
    Boolean enabled,
    Integer sortOrder,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static ProductSceneView from(ProductScene scene) {
        return new ProductSceneView(
            scene.getId(),
            scene.getName(),
            scene.getEnglishName(),
            scene.getImageUrl(),
            scene.getVideoUrl(),
            scene.getEnabled(),
            scene.getSortOrder(),
            scene.getCreatedAt(),
            scene.getUpdatedAt()
        );
    }
}
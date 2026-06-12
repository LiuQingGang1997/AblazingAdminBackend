package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.Brand;
import java.time.LocalDateTime;

public record BrandView(
    Long id,
    String name,
    String logoUrl,
    Integer sortOrder,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static BrandView from(Brand brand) {
        return new BrandView(
            brand.getId(),
            brand.getName(),
            brand.getLogoUrl(),
            brand.getSortOrder(),
            brand.getCreatedAt(),
            brand.getUpdatedAt()
        );
    }
}
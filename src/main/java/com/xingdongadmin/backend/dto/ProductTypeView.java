package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.ProductType;
import java.time.LocalDateTime;

public record ProductTypeView(
    Long id,
    String name,
    String imageUrl,
    String description,
    Long brandId,
    String brandName,
    Boolean enabled,
    Integer sortOrder,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static ProductTypeView from(ProductType productType) {
        return new ProductTypeView(
            productType.getId(),
            productType.getName(),
            productType.getImageUrl(),
            productType.getDescription(),
            productType.getMallBrand() != null ? productType.getMallBrand().getId() : null,
            productType.getMallBrand() != null ? productType.getMallBrand().getName() : null,
            productType.getEnabled(),
            productType.getSortOrder(),
            productType.getCreatedAt(),
            productType.getUpdatedAt()
        );
    }
}
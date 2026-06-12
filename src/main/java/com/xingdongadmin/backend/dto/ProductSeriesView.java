package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.ProductSeries;
import java.time.LocalDateTime;

public record ProductSeriesView(
    Long id,
    String name,
    String description,
    Long brandId,
    String brandName,
    String coverImageUrl,
    Boolean enabled,
    Integer sortOrder,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static ProductSeriesView from(ProductSeries series) {
        return new ProductSeriesView(
            series.getId(),
            series.getName(),
            series.getDescription(),
            series.getBrandId(),
            null,
            series.getCoverImageUrl(),
            series.getEnabled(),
            series.getSortOrder(),
            series.getCreatedAt(),
            series.getUpdatedAt()
        );
    }

    public ProductSeriesView withBrandName(String brandName) {
        return new ProductSeriesView(
            this.id,
            this.name,
            this.description,
            this.brandId,
            brandName,
            this.coverImageUrl,
            this.enabled,
            this.sortOrder,
            this.createdAt,
            this.updatedAt
        );
    }
}
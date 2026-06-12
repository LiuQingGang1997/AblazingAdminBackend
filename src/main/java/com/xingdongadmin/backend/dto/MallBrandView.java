package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.MallBrand;
import java.time.LocalDateTime;

public record MallBrandView(
    Long id,
    String name,
    String logoUrl,
    String slogan,
    String introduction,
    String promoImageUrl,
    String promoVideoUrl,
    String mobilePromoVideoUrl,
    String detailDescription,
    Boolean enabled,
    Integer sortOrder,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static MallBrandView from(MallBrand brand) {
        return new MallBrandView(
            brand.getId(),
            brand.getName(),
            brand.getLogoUrl(),
            brand.getSlogan(),
            brand.getIntroduction(),
            brand.getPromoImageUrl(),
            brand.getPromoVideoUrl(),
            brand.getMobilePromoVideoUrl(),
            brand.getDetailDescription(),
            brand.getEnabled(),
            brand.getSortOrder(),
            brand.getCreatedAt(),
            brand.getUpdatedAt()
        );
    }
}
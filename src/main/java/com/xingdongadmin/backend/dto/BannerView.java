package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.Banner;
import java.time.LocalDateTime;

public record BannerView(
    Long id,
    String imageUrl,
    String linkUrl,
    String modifier,
    LocalDateTime updatedAt,
    Boolean enabled,
    Integer priority
) {
    public static BannerView from(Banner banner) {
        return new BannerView(
            banner.getId(),
            banner.getImageUrl(),
            banner.getLinkUrl(),
            banner.getModifier(),
            banner.getUpdatedAt(),
            banner.getEnabled(),
            banner.getPriority()
        );
    }
}
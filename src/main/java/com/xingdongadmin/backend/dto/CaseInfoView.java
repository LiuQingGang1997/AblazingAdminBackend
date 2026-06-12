package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.CaseInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record CaseInfoView(
    Long id,
    String name,
    String address,
    String completionTime,
    String coverImage,
    List<String> detailImages,
    String titleDescription,
    Map<String, Object> features,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static CaseInfoView from(CaseInfo caseInfo) {
        return new CaseInfoView(
            caseInfo.getId(),
            caseInfo.getName(),
            caseInfo.getAddress(),
            caseInfo.getCompletionTime(),
            caseInfo.getCoverImage(),
            caseInfo.getDetailImages(),
            caseInfo.getTitleDescription(),
            caseInfo.getFeatures(),
            caseInfo.getCreatedAt(),
            caseInfo.getUpdatedAt()
        );
    }
}
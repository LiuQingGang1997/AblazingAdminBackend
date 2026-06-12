package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.CaseInfo;

public record CaseInfoLightView(
    Long id,
    String name,
    String address,
    String completionTime,
    String coverImage
) {
    public static CaseInfoLightView from(CaseInfo caseInfo) {
        return new CaseInfoLightView(
            caseInfo.getId(),
            caseInfo.getName(),
            caseInfo.getAddress(),
            caseInfo.getCompletionTime(),
            caseInfo.getCoverImage()
        );
    }
}
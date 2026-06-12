package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.CustomerReview;
import java.time.LocalDateTime;

public record CustomerReviewView(
    Long id,
    String content,
    String avatarUrl,
    String companyName,
    String position,
    String customerName,
    String reviewDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static CustomerReviewView from(CustomerReview review) {
        return new CustomerReviewView(
            review.getId(),
            review.getContent(),
            review.getAvatarUrl(),
            review.getCompanyName(),
            review.getPosition(),
            review.getCustomerName(),
            review.getReviewDate(),
            review.getCreatedAt(),
            review.getUpdatedAt()
        );
    }
}
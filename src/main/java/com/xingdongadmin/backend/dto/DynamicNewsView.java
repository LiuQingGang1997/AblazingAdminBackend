package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.DynamicNews;

import java.time.LocalDateTime;

public class DynamicNewsView {
    
    public Long id;
    public String title;
    public String description;
    public String detailContent;
    public String imageUrl;
    public String dynamicType;
    public String dynamicTypeName;
    public LocalDateTime publishDate;
    public String source;
    public Boolean enabled;
    public Integer sortOrder;
    public Integer viewCount;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    
    public static DynamicNewsView from(DynamicNews news) {
        DynamicNewsView view = new DynamicNewsView();
        view.id = news.getId();
        view.title = news.getTitle();
        view.description = news.getDescription();
        view.detailContent = news.getDetailContent();
        view.imageUrl = news.getImageUrl();
        view.dynamicType = news.getDynamicType();
        view.dynamicTypeName = getDynamicTypeName(news.getDynamicType());
        view.publishDate = news.getPublishDate();
        view.source = news.getSource();
        view.enabled = news.getEnabled();
        view.sortOrder = news.getSortOrder();
        view.viewCount = news.getViewCount();
        view.createdAt = news.getCreatedAt();
        view.updatedAt = news.getUpdatedAt();
        return view;
    }
    
    private static String getDynamicTypeName(String type) {
        return switch (type) {
            case "NEWS" -> "新闻动态";
            case "ANNOUNCEMENT" -> "公告通知";
            case "EVENT" -> "活动资讯";
            case "PRESS" -> "新闻发布会";
            case "REPORT" -> "专题报道";
            default -> type;
        };
    }
}
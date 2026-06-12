package com.xingdongadmin.backend.dto;

import com.xingdongadmin.backend.entity.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryView {
    public Long id;
    public String name;
    public Long parentId;
    public Long brandId;
    public String brandName;
    public Integer level;
    public Integer sort;
    public Integer status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public List<CategoryView> children = new ArrayList<>();

    public static CategoryView from(Category category) {
        CategoryView view = new CategoryView();
        view.id = category.getId();
        view.name = category.getName();
        view.parentId = category.getParentId();
        view.brandId = category.getBrandId();
        view.level = category.getLevel();
        view.sort = category.getSort();
        view.status = category.getStatus();
        view.createdAt = category.getCreatedAt();
        view.updatedAt = category.getUpdatedAt();
        return view;
    }
}
package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.CategoryView;
import com.xingdongadmin.backend.entity.Category;
import com.xingdongadmin.backend.repository.CategoryRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static class CategoryRequest {
        @NotBlank(message = "分类名称不能为空")
        public String name;
        
        public Long parentId = 0L;
        public Long brandId;
        public Integer level = 1;
        public Integer sort = 0;
        public Integer status = 1;
    }

    @GetMapping
    public List<CategoryView> getAllCategories() {
        return categoryRepository.findAllByOrderBySortAsc().stream()
                .map(CategoryView::from)
                .toList();
    }

    @GetMapping("/tree")
    public List<CategoryView> getCategoryTree() {
        List<Category> allCategories = categoryRepository.findAllByOrderBySortAsc();
        Map<Long, CategoryView> viewMap = new HashMap<>();
        List<CategoryView> rootCategories = new ArrayList<>();

        for (Category category : allCategories) {
            CategoryView view = CategoryView.from(category);
            viewMap.put(category.getId(), view);
        }

        for (Category category : allCategories) {
            CategoryView view = viewMap.get(category.getId());
            if (category.getParentId() != null && category.getParentId() > 0) {
                CategoryView parent = viewMap.get(category.getParentId());
                if (parent != null) {
                    parent.children.add(view);
                }
            } else {
                rootCategories.add(view);
            }
        }

        return rootCategories;
    }

    @GetMapping("/frontend/tree")
    public List<CategoryView> getFrontendCategoryTree(@RequestParam(required = false) Long brandId) {
        List<Category> categories;
        if (brandId != null) {
            categories = categoryRepository.findByBrandIdAndStatusOrderBySortAsc(brandId, 1);
        } else {
            categories = categoryRepository.findByStatusOrderBySortAsc(1);
        }

        Map<Long, CategoryView> viewMap = new HashMap<>();
        List<CategoryView> rootCategories = new ArrayList<>();

        for (Category category : categories) {
            CategoryView view = CategoryView.from(category);
            viewMap.put(category.getId(), view);
        }

        for (Category category : categories) {
            CategoryView view = viewMap.get(category.getId());
            if (category.getParentId() != null && category.getParentId() > 0) {
                CategoryView parent = viewMap.get(category.getParentId());
                if (parent != null) {
                    parent.children.add(view);
                }
            } else {
                rootCategories.add(view);
            }
        }

        return rootCategories;
    }

    @GetMapping("/frontend/list")
    public List<CategoryView> getFrontendCategoryList(@RequestParam(required = false) Long brandId) {
        if (brandId != null) {
            return categoryRepository.findByBrandIdAndStatusOrderBySortAsc(brandId, 1).stream()
                    .map(CategoryView::from)
                    .toList();
        }
        return categoryRepository.findByStatusOrderBySortAsc(1).stream()
                .map(CategoryView::from)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryView getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(CategoryView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    @GetMapping("/parent/{parentId}")
    public List<CategoryView> getCategoriesByParent(@PathVariable Long parentId) {
        return categoryRepository.findByParentIdAndStatusOrderBySortAsc(parentId, 1).stream()
                .map(CategoryView::from)
                .toList();
    }

    @GetMapping("/brand/{brandId}")
    public List<CategoryView> getCategoriesByBrand(@PathVariable Long brandId) {
        return categoryRepository.findByBrandIdOrderBySortAsc(brandId).stream()
                .map(CategoryView::from)
                .toList();
    }

    @GetMapping("/level/{level}")
    public List<CategoryView> getCategoriesByLevel(@PathVariable Integer level) {
        return categoryRepository.findByLevelOrderBySortAsc(level).stream()
                .map(CategoryView::from)
                .toList();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryView createCategory(@RequestBody CategoryRequest req) {
        Category category = new Category();
        category.setName(req.name);
        category.setParentId(req.parentId != null ? req.parentId : 0L);
        category.setBrandId(req.brandId);
        category.setLevel(req.level != null ? req.level : 1);
        category.setSort(req.sort != null ? req.sort : 0);
        category.setStatus(req.status != null ? req.status : 1);

        return CategoryView.from(categoryRepository.save(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryView updateCategory(@PathVariable Long id, @RequestBody CategoryRequest req) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        if (req.name != null) existing.setName(req.name);
        if (req.parentId != null) existing.setParentId(req.parentId);
        if (req.brandId != null) existing.setBrandId(req.brandId);
        if (req.level != null) existing.setLevel(req.level);
        if (req.sort != null) existing.setSort(req.sort);
        if (req.status != null) existing.setStatus(req.status);

        return CategoryView.from(categoryRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
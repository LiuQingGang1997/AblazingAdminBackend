package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.ProductSceneView;
import com.xingdongadmin.backend.entity.ProductScene;
import com.xingdongadmin.backend.repository.ProductSceneRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/product-scenes")
public class ProductSceneController {

    private final ProductSceneRepository productSceneRepository;

    public ProductSceneController(ProductSceneRepository productSceneRepository) {
        this.productSceneRepository = productSceneRepository;
    }

    public static class ProductSceneRequest {
        @NotBlank(message = "场景名称不能为空")
        public String name;
        
        public String englishName;
        public String imageUrl;
        public String videoUrl;
        public Boolean enabled;
        public Integer sortOrder;
    }

    @GetMapping
    public List<ProductSceneView> getAllProductScenes() {
        return productSceneRepository.findAllByOrderBySortOrderAsc().stream()
                .map(ProductSceneView::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductSceneView getProductScene(@PathVariable Long id) {
        return productSceneRepository.findById(id)
                .map(ProductSceneView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Scene not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductSceneView createProductScene(@RequestBody ProductSceneRequest req) {
        ProductScene scene = new ProductScene();
        scene.setName(req.name);
        scene.setEnglishName(req.englishName);
        scene.setImageUrl(req.imageUrl);
        scene.setVideoUrl(req.videoUrl);
        
        if (req.enabled != null) scene.setEnabled(req.enabled);
        if (req.sortOrder != null) scene.setSortOrder(req.sortOrder);

        return ProductSceneView.from(productSceneRepository.save(scene));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductSceneView updateProductScene(@PathVariable Long id, @RequestBody ProductSceneRequest req) {
        ProductScene existing = productSceneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Scene not found"));

        if (req.name != null) existing.setName(req.name);
        if (req.englishName != null) existing.setEnglishName(req.englishName);
        if (req.imageUrl != null) existing.setImageUrl(req.imageUrl);
        if (req.videoUrl != null) existing.setVideoUrl(req.videoUrl);
        if (req.enabled != null) existing.setEnabled(req.enabled);
        if (req.sortOrder != null) existing.setSortOrder(req.sortOrder);

        return ProductSceneView.from(productSceneRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProductScene(@PathVariable Long id) {
        productSceneRepository.deleteById(id);
    }

    // ========== 前端公开接口 ==========

    /**
     * 获取启用的产品场景列表（前端页面使用）
     */
    @GetMapping("/frontend/list")
    public List<ProductSceneView> getEnabledProductScenes() {
        return productSceneRepository.findByEnabledTrueOrderBySortOrderAsc().stream()
                .map(ProductSceneView::from)
                .toList();
    }
}
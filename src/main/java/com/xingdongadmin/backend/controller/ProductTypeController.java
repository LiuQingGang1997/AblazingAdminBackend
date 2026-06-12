package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.ProductTypeView;
import com.xingdongadmin.backend.entity.MallBrand;
import com.xingdongadmin.backend.entity.ProductType;
import com.xingdongadmin.backend.repository.MallBrandRepository;
import com.xingdongadmin.backend.repository.ProductTypeRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/product-types")
public class ProductTypeController {

    private final ProductTypeRepository productTypeRepository;
    private final MallBrandRepository mallBrandRepository;

    public ProductTypeController(ProductTypeRepository productTypeRepository, MallBrandRepository mallBrandRepository) {
        this.productTypeRepository = productTypeRepository;
        this.mallBrandRepository = mallBrandRepository;
    }

    public static class ProductTypeRequest {
        @NotBlank(message = "类型名称不能为空")
        public String name;
        
        public String imageUrl;
        public String description;
        
        @NotNull(message = "关联品牌ID不能为空")
        public Long brandId;
        
        public Boolean enabled;
        public Integer sortOrder;
    }

    @GetMapping
    public List<ProductTypeView> getAllProductTypes() {
        return productTypeRepository.findAllByOrderBySortOrderAsc().stream()
                .map(ProductTypeView::from)
                .toList();
    }

    @GetMapping("/brand/{brandId}")
    public List<ProductTypeView> getProductTypesByBrand(@PathVariable Long brandId) {
        return productTypeRepository.findAllByMallBrandIdOrderBySortOrderAsc(brandId).stream()
                .map(ProductTypeView::from)
                .toList();
    }

    @GetMapping("/frontend/list")
    public List<ProductTypeView> getFrontendProductTypes(@RequestParam(required = false) Long brandId) {
        if (brandId == null) {
            List<MallBrand> enabledBrands = mallBrandRepository.findByEnabledTrueOrderBySortOrderAsc();
            if (enabledBrands.isEmpty()) {
                return List.of();
            }
            brandId = enabledBrands.get(0).getId();
        }
        
        return productTypeRepository.findByEnabledTrueAndMallBrandIdOrderBySortOrderAsc(brandId)
                .stream()
                .map(ProductTypeView::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductTypeView getProductType(@PathVariable Long id) {
        return productTypeRepository.findById(id)
                .map(ProductTypeView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductTypeView createProductType(@RequestBody ProductTypeRequest req) {
        MallBrand brand = mallBrandRepository.findById(req.brandId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "关联品牌不存在"));

        ProductType pt = new ProductType();
        pt.setName(req.name);
        pt.setImageUrl(req.imageUrl);
        pt.setDescription(req.description);
        pt.setMallBrand(brand);
        
        if (req.enabled != null) pt.setEnabled(req.enabled);
        if (req.sortOrder != null) pt.setSortOrder(req.sortOrder);

        return ProductTypeView.from(productTypeRepository.save(pt));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductTypeView updateProductType(@PathVariable Long id, @RequestBody ProductTypeRequest req) {
        ProductType existing = productTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type not found"));

        if (req.brandId != null && !req.brandId.equals(existing.getMallBrand().getId())) {
            MallBrand brand = mallBrandRepository.findById(req.brandId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "关联品牌不存在"));
            existing.setMallBrand(brand);
        }

        if (req.name != null) existing.setName(req.name);
        if (req.imageUrl != null) existing.setImageUrl(req.imageUrl);
        if (req.description != null) existing.setDescription(req.description);
        if (req.enabled != null) existing.setEnabled(req.enabled);
        if (req.sortOrder != null) existing.setSortOrder(req.sortOrder);

        return ProductTypeView.from(productTypeRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProductType(@PathVariable Long id) {
        productTypeRepository.deleteById(id);
    }
}
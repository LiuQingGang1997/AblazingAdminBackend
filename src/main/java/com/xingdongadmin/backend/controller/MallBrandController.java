package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.MallBrandView;
import com.xingdongadmin.backend.entity.MallBrand;
import com.xingdongadmin.backend.repository.MallBrandRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.xingdongadmin.backend.dto.ProductTypeView;
import com.xingdongadmin.backend.repository.ProductTypeRepository;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mall-brands")
public class MallBrandController {

    private final MallBrandRepository mallBrandRepository;
    private final ProductTypeRepository productTypeRepository;

    public MallBrandController(MallBrandRepository mallBrandRepository, ProductTypeRepository productTypeRepository) {
        this.mallBrandRepository = mallBrandRepository;
        this.productTypeRepository = productTypeRepository;
    }

    public static class MallBrandRequest {
        @NotBlank(message = "品牌名称不能为空")
        public String name;
        @NotBlank(message = "品牌logo不能为空")
        public String logoUrl;
        
        public String slogan;
        public String introduction;
        public String promoImageUrl;
        public String promoVideoUrl;
        public String mobilePromoVideoUrl;
        public String detailDescription;
        public Boolean enabled;
        public Integer sortOrder;
    }

    @GetMapping
    public java.util.List<MallBrandView> getAllMallBrands() {
        return mallBrandRepository.findAllByOrderBySortOrderAsc().stream()
                .map(MallBrandView::from)
                .toList();
    }

    @GetMapping("/frontend/detail")
    public Map<String, Object> getFrontendBrandDetail(@RequestParam(required = false) Long brandId) {
        java.util.List<MallBrand> allEnabledBrands = mallBrandRepository.findByEnabledTrueOrderBySortOrderAsc();
        if (allEnabledBrands.isEmpty()) {
            Map<String, Object> emptyRes = new HashMap<>();
            emptyRes.put("brands", java.util.List.of());
            emptyRes.put("currentBrand", null);
            emptyRes.put("productTypes", java.util.List.of());
            return emptyRes;
        }

        MallBrand targetBrand = null;
        if (brandId != null) {
            targetBrand = allEnabledBrands.stream()
                    .filter(b -> b.getId().equals(brandId))
                    .findFirst()
                    .orElse(null);
        }
        if (targetBrand == null) {
            targetBrand = allEnabledBrands.get(0);
        }

        java.util.List<ProductTypeView> types = productTypeRepository.findByEnabledTrueAndMallBrandIdOrderBySortOrderAsc(targetBrand.getId())
                .stream().map(ProductTypeView::from).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("brands", allEnabledBrands.stream().map(MallBrandView::from).toList());
        response.put("currentBrand", MallBrandView.from(targetBrand));
        response.put("productTypes", types);
        
        return response;
    }

    @GetMapping("/{id}")
    public MallBrandView getMallBrand(@PathVariable Long id) {
        return mallBrandRepository.findById(id)
                .map(MallBrandView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mall Brand not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MallBrandView createMallBrand(@RequestBody MallBrandRequest req) {
        MallBrand brand = new MallBrand();
        brand.setName(req.name);
        brand.setLogoUrl(req.logoUrl);
        brand.setSlogan(req.slogan);
        brand.setIntroduction(req.introduction);
        brand.setPromoImageUrl(req.promoImageUrl);
        brand.setPromoVideoUrl(req.promoVideoUrl);
        brand.setMobilePromoVideoUrl(req.mobilePromoVideoUrl);
        brand.setDetailDescription(req.detailDescription);
        
        if (req.enabled != null) brand.setEnabled(req.enabled);
        if (req.sortOrder != null) brand.setSortOrder(req.sortOrder);

        return MallBrandView.from(mallBrandRepository.save(brand));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MallBrandView updateMallBrand(@PathVariable Long id, @RequestBody MallBrandRequest req) {
        MallBrand existing = mallBrandRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mall Brand not found"));

        if (req.name != null) existing.setName(req.name);
        if (req.logoUrl != null) existing.setLogoUrl(req.logoUrl);
        if (req.slogan != null) existing.setSlogan(req.slogan);
        if (req.introduction != null) existing.setIntroduction(req.introduction);
        if (req.promoImageUrl != null) existing.setPromoImageUrl(req.promoImageUrl);
        if (req.promoVideoUrl != null) existing.setPromoVideoUrl(req.promoVideoUrl);
        if (req.mobilePromoVideoUrl != null) existing.setMobilePromoVideoUrl(req.mobilePromoVideoUrl);
        if (req.detailDescription != null) existing.setDetailDescription(req.detailDescription);
        if (req.enabled != null) existing.setEnabled(req.enabled);
        if (req.sortOrder != null) existing.setSortOrder(req.sortOrder);

        return MallBrandView.from(mallBrandRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMallBrand(@PathVariable Long id) {
        mallBrandRepository.deleteById(id);
    }
}
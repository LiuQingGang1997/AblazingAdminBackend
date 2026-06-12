package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.BrandView;
import com.xingdongadmin.backend.entity.Brand;
import com.xingdongadmin.backend.repository.BrandRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandRepository brandRepository;

    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public static class BrandRequest {
        @NotBlank(message = "品牌名称不能为空")
        public String name;
        
        @NotBlank(message = "品牌logo不能为空")
        public String logoUrl;
        
        public Integer sortOrder;
    }

    @GetMapping
    public List<BrandView> getAllBrands() {
        return brandRepository.findAllByOrderBySortOrderAsc().stream()
                .map(BrandView::from)
                .toList();
    }

    @GetMapping("/{id}")
    public BrandView getBrand(@PathVariable Long id) {
        return brandRepository.findById(id)
                .map(BrandView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BrandView createBrand(@RequestBody BrandRequest req) {
        Brand brand = new Brand();
        brand.setName(req.name);
        brand.setLogoUrl(req.logoUrl);
        brand.setSortOrder(req.sortOrder != null ? req.sortOrder : 0);

        return BrandView.from(brandRepository.save(brand));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BrandView updateBrand(@PathVariable Long id, @RequestBody BrandRequest req) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));

        if (req.name != null) existing.setName(req.name);
        if (req.logoUrl != null) existing.setLogoUrl(req.logoUrl);
        if (req.sortOrder != null) existing.setSortOrder(req.sortOrder);

        return BrandView.from(brandRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBrand(@PathVariable Long id) {
        brandRepository.deleteById(id);
    }
}
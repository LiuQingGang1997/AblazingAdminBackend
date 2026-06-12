package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.ProductSeriesView;
import com.xingdongadmin.backend.entity.MallBrand;
import com.xingdongadmin.backend.entity.ProductSeries;
import com.xingdongadmin.backend.repository.MallBrandRepository;
import com.xingdongadmin.backend.repository.ProductSeriesRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product-series")
public class ProductSeriesController {

    private final ProductSeriesRepository productSeriesRepository;
    private final MallBrandRepository mallBrandRepository;

    public ProductSeriesController(ProductSeriesRepository productSeriesRepository, MallBrandRepository mallBrandRepository) {
        this.productSeriesRepository = productSeriesRepository;
        this.mallBrandRepository = mallBrandRepository;
    }

    public static class ProductSeriesRequest {
        @NotBlank(message = "系列名称不能为空")
        public String name;
        
        public String description;
        @NotNull(message = "品牌ID不能为空")
        public Long brandId;
        public String coverImageUrl;
        public Boolean enabled;
        public Integer sortOrder;
    }

    @GetMapping
    public List<ProductSeriesView> getAllProductSeries() {
        List<MallBrand> brands = mallBrandRepository.findAll();
        Map<Long, String> brandNameMap = brands.stream()
                .collect(Collectors.toMap(MallBrand::getId, MallBrand::getName));

        return productSeriesRepository.findAllByOrderBySortOrderAsc().stream()
                .map(series -> {
                    ProductSeriesView view = ProductSeriesView.from(series);
                    return view.withBrandName(brandNameMap.get(series.getBrandId()));
                })
                .toList();
    }

    @GetMapping("/brand/{brandId}")
    public List<ProductSeriesView> getProductSeriesByBrand(@PathVariable Long brandId) {
        MallBrand brand = mallBrandRepository.findById(brandId).orElse(null);
        String brandName = brand != null ? brand.getName() : null;

        return productSeriesRepository.findByBrandIdOrderBySortOrderAsc(brandId).stream()
                .map(series -> ProductSeriesView.from(series).withBrandName(brandName))
                .toList();
    }

    @GetMapping("/frontend/list")
    public List<ProductSeriesView> getFrontendProductSeries(@RequestParam(required = false) Long brandId) {
        List<MallBrand> brands = mallBrandRepository.findByEnabledTrueOrderBySortOrderAsc();
        Map<Long, String> brandNameMap = brands.stream()
                .collect(Collectors.toMap(MallBrand::getId, MallBrand::getName));

        List<ProductSeries> seriesList;
        if (brandId != null) {
            seriesList = productSeriesRepository.findByEnabledTrueAndBrandIdOrderBySortOrderAsc(brandId);
        } else {
            if (brands.isEmpty()) {
                return List.of();
            }
            seriesList = productSeriesRepository.findByEnabledTrueAndBrandIdOrderBySortOrderAsc(brands.get(0).getId());
        }

        return seriesList.stream()
                .map(series -> {
                    ProductSeriesView view = ProductSeriesView.from(series);
                    return view.withBrandName(brandNameMap.get(series.getBrandId()));
                })
                .toList();
    }

    @GetMapping("/{id}")
    public ProductSeriesView getProductSeries(@PathVariable Long id) {
        return productSeriesRepository.findById(id)
                .map(series -> {
                    ProductSeriesView view = ProductSeriesView.from(series);
                    if (series.getBrandId() != null) {
                        mallBrandRepository.findById(series.getBrandId())
                                .ifPresent(brand -> view.withBrandName(brand.getName()));
                    }
                    return view;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Series not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductSeriesView createProductSeries(@RequestBody ProductSeriesRequest req) {
        MallBrand brand = mallBrandRepository.findById(req.brandId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "关联品牌不存在"));

        ProductSeries series = new ProductSeries();
        series.setName(req.name);
        series.setDescription(req.description);
        series.setBrandId(req.brandId);
        series.setCoverImageUrl(req.coverImageUrl);
        if (req.enabled != null) series.setEnabled(req.enabled);
        if (req.sortOrder != null) series.setSortOrder(req.sortOrder);

        ProductSeries saved = productSeriesRepository.save(series);
        return ProductSeriesView.from(saved).withBrandName(brand.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductSeriesView updateProductSeries(@PathVariable Long id, @RequestBody ProductSeriesRequest req) {
        ProductSeries existing = productSeriesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Series not found"));

        if (req.brandId != null && !req.brandId.equals(existing.getBrandId())) {
            MallBrand brand = mallBrandRepository.findById(req.brandId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "关联品牌不存在"));
            existing.setBrandId(req.brandId);
        }

        if (req.name != null) existing.setName(req.name);
        if (req.description != null) existing.setDescription(req.description);
        if (req.coverImageUrl != null) existing.setCoverImageUrl(req.coverImageUrl);
        if (req.enabled != null) existing.setEnabled(req.enabled);
        if (req.sortOrder != null) existing.setSortOrder(req.sortOrder);

        ProductSeries saved = productSeriesRepository.save(existing);
        String brandName = mallBrandRepository.findById(saved.getBrandId())
                .map(MallBrand::getName)
                .orElse(null);
        return ProductSeriesView.from(saved).withBrandName(brandName);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProductSeries(@PathVariable Long id) {
        productSeriesRepository.deleteById(id);
    }
}
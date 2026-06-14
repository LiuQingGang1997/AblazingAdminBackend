package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.ProductView;
import com.xingdongadmin.backend.entity.*;
import com.xingdongadmin.backend.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final MallBrandRepository mallBrandRepository;
    private final ProductSceneRepository productSceneRepository;
    private final ProductTypeRepository productTypeRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSeriesRepository productSeriesRepository;

    private final ProductBrandRelationRepository brandRelationRepo;
    private final ProductSceneRelationRepository sceneRelationRepo;
    private final ProductTypeRelationRepository typeRelationRepo;

    public ProductController(
            ProductRepository productRepository,
            MallBrandRepository mallBrandRepository,
            ProductSceneRepository productSceneRepository,
            ProductTypeRepository productTypeRepository,
            CategoryRepository categoryRepository,
            ProductSeriesRepository productSeriesRepository,
            ProductBrandRelationRepository brandRelationRepo,
            ProductSceneRelationRepository sceneRelationRepo,
            ProductTypeRelationRepository typeRelationRepo) {
        this.productRepository = productRepository;
        this.mallBrandRepository = mallBrandRepository;
        this.productSceneRepository = productSceneRepository;
        this.productTypeRepository = productTypeRepository;
        this.categoryRepository = categoryRepository;
        this.productSeriesRepository = productSeriesRepository;
        this.brandRelationRepo = brandRelationRepo;
        this.sceneRelationRepo = sceneRelationRepo;
        this.typeRelationRepo = typeRelationRepo;
    }

    public static class ProductRequest {
        @NotBlank(message = "产品名称不能为空")
        public String name;
        
        public String model;
        public String summary;
        public String detailDescription;
        public BigDecimal price;
        public String coverImageUrl;
        
        public Long brandId;
        public Long sceneId;
        public Long typeId;
        public Long categoryId;
        public Long seriesId;
        
        public List<String> detailImages;
        public Map<String, String> parameters;
        
        public Boolean enabled;
        public Integer sortOrder;
    }

    @GetMapping
    public List<ProductView> getAllProducts(
            @RequestParam(required = false) Long brandId) {
        if (brandId != null) {
            List<Long> productIds = brandRelationRepo.findByBrandId(brandId).stream()
                    .map(ProductBrandRelation::getProductId)
                    .toList();
            if (productIds.isEmpty()) {
                return List.of();
            }
            return productRepository.findAllById(productIds).stream()
                    .sorted(java.util.Comparator.comparing(Product::getSortOrder))
                    .map(this::buildProductView)
                    .toList();
        }
        return productRepository.findAllByOrderBySortOrderAsc().stream()
                .map(this::buildProductView)
                .toList();
    }

    @GetMapping("/frontend/search")
    public List<ProductView> searchProducts(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) Long sceneId,
            @RequestParam(required = false) String parameterKey,
            @RequestParam(required = false) String parameterValue) {

        if (brandId == null) {
            List<MallBrand> brands = mallBrandRepository.findByEnabledTrueOrderBySortOrderAsc();
            if (brands.isEmpty()) {
                return List.of();
            }
            brandId = brands.get(0).getId();
        }

        List<Long> productIds = new java.util.ArrayList<>(brandRelationRepo.findByBrandId(brandId).stream()
                .map(ProductBrandRelation::getProductId)
                .toList());

        if (typeId != null) {
            List<Long> typeProductIds = typeRelationRepo.findByTypeId(typeId).stream()
                    .map(ProductTypeRelation::getProductId)
                    .toList();
            productIds.retainAll(typeProductIds);
        }

        if (sceneId != null) {
            List<Long> sceneProductIds = sceneRelationRepo.findBySceneId(sceneId).stream()
                    .map(ProductSceneRelation::getProductId)
                    .toList();
            productIds.retainAll(sceneProductIds);
        }

        if (productIds.isEmpty()) {
            return List.of();
        }

        return productRepository.findAllById(productIds).stream()
                .filter(p -> Boolean.TRUE.equals(p.getEnabled()))
                .filter(p -> {
                    if (parameterKey != null && !parameterKey.isEmpty() && parameterValue != null && !parameterValue.isEmpty()) {
                        String val = p.getParameters().get(parameterKey);
                        return val != null && val.contains(parameterValue);
                    }
                    return true;
                })
                .sorted(java.util.Comparator.comparing(Product::getSortOrder))
                .map(this::buildProductView)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductView getProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return buildProductView(product);
    }
    
    @GetMapping("/frontend/detail")
    public ProductView getFrontendProductDetail(
            @RequestParam Long id,
            @RequestParam(required = false, defaultValue = "true") Boolean enabled) {
        Product product = productRepository.findByIdAndEnabled(id, enabled)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return buildProductView(product);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @Transactional
    public ProductView createProduct(@RequestBody ProductRequest req) {
        Product product = new Product();
        updateProductBasicFields(product, req);
        product = productRepository.save(product);
        
        updateRelations(product.getId(), req.brandId, req.sceneId, req.typeId);
        
        return buildProductView(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @Transactional
    public ProductView updateProduct(@PathVariable Long id, @RequestBody ProductRequest req) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
                
        updateProductBasicFields(existing, req);
        existing = productRepository.save(existing);
        
        updateRelations(existing.getId(), req.brandId, req.sceneId, req.typeId);
        
        return buildProductView(existing);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @Transactional
    public void deleteProduct(@PathVariable Long id) {
        brandRelationRepo.deleteByProductId(id);
        sceneRelationRepo.deleteByProductId(id);
        typeRelationRepo.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    private void updateProductBasicFields(Product product, ProductRequest req) {
        if (req.name != null) product.setName(req.name);
        if (req.model != null) product.setModel(req.model);
        if (req.summary != null) product.setSummary(req.summary);
        if (req.detailDescription != null) product.setDetailDescription(req.detailDescription);
        if (req.price != null) product.setPrice(req.price);
        if (req.coverImageUrl != null) product.setCoverImageUrl(req.coverImageUrl);
        
        if (req.categoryId != null) product.setCategoryId(req.categoryId);
        if (req.seriesId != null) product.setSeriesId(req.seriesId);
        
        if (req.detailImages != null) {
            product.getDetailImages().clear();
            product.getDetailImages().addAll(req.detailImages);
        }
        
        if (req.parameters != null) {
            product.getParameters().clear();
            product.getParameters().putAll(req.parameters);
        }
        
        if (req.enabled != null) product.setEnabled(req.enabled);
        if (req.sortOrder != null) product.setSortOrder(req.sortOrder);
    }
    
    private void updateRelations(Long productId, Long brandId, Long sceneId, Long typeId) {
        if (brandId != null) {
            Optional<ProductBrandRelation> existing = brandRelationRepo.findByProductId(productId);
            if (existing.isPresent()) {
                existing.get().setBrandId(brandId);
                brandRelationRepo.save(existing.get());
            } else {
                brandRelationRepo.save(new ProductBrandRelation(productId, brandId));
            }
        }
        
        if (sceneId != null) {
            Optional<ProductSceneRelation> existing = sceneRelationRepo.findByProductId(productId);
            if (existing.isPresent()) {
                existing.get().setSceneId(sceneId);
                sceneRelationRepo.save(existing.get());
            } else {
                sceneRelationRepo.save(new ProductSceneRelation(productId, sceneId));
            }
        }
        
        if (typeId != null) {
            Optional<ProductTypeRelation> existing = typeRelationRepo.findByProductId(productId);
            if (existing.isPresent()) {
                existing.get().setTypeId(typeId);
                typeRelationRepo.save(existing.get());
            } else {
                typeRelationRepo.save(new ProductTypeRelation(productId, typeId));
            }
        }
    }

    private ProductView buildProductView(Product product) {
        Long brandId = brandRelationRepo.findByProductId(product.getId()).map(ProductBrandRelation::getBrandId).orElse(null);
        Long sceneId = sceneRelationRepo.findByProductId(product.getId()).map(ProductSceneRelation::getSceneId).orElse(null);
        Long typeId = typeRelationRepo.findByProductId(product.getId()).map(ProductTypeRelation::getTypeId).orElse(null);

        String brandName = brandId != null ? mallBrandRepository.findById(brandId).map(MallBrand::getName).orElse(null) : null;
        String sceneName = sceneId != null ? productSceneRepository.findById(sceneId).map(ProductScene::getName).orElse(null) : null;
        String typeName = typeId != null ? productTypeRepository.findById(typeId).map(ProductType::getName).orElse(null) : null;
        String categoryName = product.getCategoryId() != null ? categoryRepository.findById(product.getCategoryId()).map(Category::getName).orElse(null) : null;
        String seriesName = product.getSeriesId() != null ? productSeriesRepository.findById(product.getSeriesId()).map(ProductSeries::getName).orElse(null) : null;

        return new ProductView(
            product.getId(),
            product.getName(),
            product.getModel(),
            product.getSummary(),
            product.getDetailDescription(),
            product.getPrice(),
            product.getCoverImageUrl(),
            brandId,
            sceneId,
            typeId,
            product.getCategoryId(),
            product.getSeriesId(),
            brandName,
            sceneName,
            typeName,
            categoryName,
            seriesName,
            product.getDetailImages(),
            product.getParameters(),
            product.getEnabled(),
            product.getSortOrder(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}
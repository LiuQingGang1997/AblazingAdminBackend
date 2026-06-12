package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.ProductSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSeriesRepository extends JpaRepository<ProductSeries, Long> {
    List<ProductSeries> findAllByOrderBySortOrderAsc();
    List<ProductSeries> findByBrandIdOrderBySortOrderAsc(Long brandId);
    List<ProductSeries> findByEnabledTrueOrderBySortOrderAsc();
    List<ProductSeries> findByEnabledTrueAndBrandIdOrderBySortOrderAsc(Long brandId);
}
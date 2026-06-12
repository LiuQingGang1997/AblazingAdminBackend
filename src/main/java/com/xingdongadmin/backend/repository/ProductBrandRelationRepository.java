package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.ProductBrandRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBrandRelationRepository extends JpaRepository<ProductBrandRelation, Long> {
    Optional<ProductBrandRelation> findByProductId(Long productId);
    List<ProductBrandRelation> findByBrandId(Long brandId);
    void deleteByProductId(Long productId);
}
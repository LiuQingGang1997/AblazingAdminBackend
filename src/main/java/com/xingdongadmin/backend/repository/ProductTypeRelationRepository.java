package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.ProductTypeRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeRelationRepository extends JpaRepository<ProductTypeRelation, Long> {
    Optional<ProductTypeRelation> findByProductId(Long productId);
    List<ProductTypeRelation> findByTypeId(Long typeId);
    void deleteByProductId(Long productId);
}
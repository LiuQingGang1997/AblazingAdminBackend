package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.ProductSceneRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSceneRelationRepository extends JpaRepository<ProductSceneRelation, Long> {
    Optional<ProductSceneRelation> findByProductId(Long productId);
    List<ProductSceneRelation> findBySceneId(Long sceneId);
    void deleteByProductId(Long productId);
}
package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.ProductScene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSceneRepository extends JpaRepository<ProductScene, Long> {
    // 获取所有的产品场景，按序号升序排列
    List<ProductScene> findAllByOrderBySortOrderAsc();

    // 获取启用的产品场景，按序号升序排列（前端使用）
    List<ProductScene> findByEnabledTrueOrderBySortOrderAsc();
}
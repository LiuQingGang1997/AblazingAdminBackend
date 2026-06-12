package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    // 根据品牌ID查询产品类型，并按照序号升序排列
    List<ProductType> findAllByMallBrandIdOrderBySortOrderAsc(Long mallBrandId);
    
    // 根据品牌ID查询启用的产品类型，并按照序号升序排列
    List<ProductType> findByEnabledTrueAndMallBrandIdOrderBySortOrderAsc(Long mallBrandId);
    
    // 获取所有的产品类型，按序号升序排列
    List<ProductType> findAllByOrderBySortOrderAsc();
}
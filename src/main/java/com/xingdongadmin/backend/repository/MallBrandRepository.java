package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.MallBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MallBrandRepository extends JpaRepository<MallBrand, Long> {
    // 按序号升序排列
    List<MallBrand> findAllByOrderBySortOrderAsc();
    List<MallBrand> findByEnabledTrueOrderBySortOrderAsc();
}
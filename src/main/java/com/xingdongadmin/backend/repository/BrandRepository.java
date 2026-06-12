package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    // 按照序号升序排列
    List<Brand> findAllByOrderBySortOrderAsc();
}
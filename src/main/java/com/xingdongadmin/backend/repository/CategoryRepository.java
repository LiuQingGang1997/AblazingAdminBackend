package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIdOrderBySortAsc(Long parentId);
    List<Category> findByParentIdAndStatusOrderBySortAsc(Long parentId, Integer status);
    List<Category> findByBrandIdOrderBySortAsc(Long brandId);
    List<Category> findByBrandIdAndStatusOrderBySortAsc(Long brandId, Integer status);
    List<Category> findByLevelOrderBySortAsc(Integer level);
    List<Category> findByLevelAndStatusOrderBySortAsc(Integer level, Integer status);
    List<Category> findByStatusOrderBySortAsc(Integer status);
    List<Category> findAllByOrderBySortAsc();
}
package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderBySortOrderAsc();
    
    java.util.Optional<Product> findByIdAndEnabled(Long id, Boolean enabled);
}
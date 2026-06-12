package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAllByOrderByPriorityDesc();
    List<Banner> findByEnabledTrueOrderByPriorityDesc();
}
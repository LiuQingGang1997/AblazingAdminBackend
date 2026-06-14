package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.DynamicNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DynamicNewsRepository extends JpaRepository<DynamicNews, Long> {
    
    List<DynamicNews> findByEnabledTrueOrderBySortOrderAsc();
    
    List<DynamicNews> findByEnabledTrueOrderByPublishDateDesc();
    
    List<DynamicNews> findByDynamicTypeAndEnabledTrueOrderByPublishDateDesc(String dynamicType);
    
    List<DynamicNews> findByEnabledTrueAndPublishDateBetweenOrderByPublishDateDesc(
            LocalDateTime startDate, LocalDateTime endDate);
    
    List<DynamicNews> findByTitleContainingAndEnabledTrueOrderByPublishDateDesc(String keyword);
    
    @Modifying
    @Query("UPDATE DynamicNews n SET n.viewCount = n.viewCount + 1 WHERE n.id = :id")
    void incrementViewCount(@Param("id") Long id);
}
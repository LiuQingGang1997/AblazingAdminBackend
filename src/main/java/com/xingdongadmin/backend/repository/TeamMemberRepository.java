package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    
    // 获取所有团队成员，按排序顺序
    List<TeamMember> findAllByOrderBySortOrderAsc();
    
    // 获取启用的团队成员，按排序顺序（前端使用）
    List<TeamMember> findByEnabledTrueOrderBySortOrderAsc();
}

package com.xingdongadmin.backend.repository;

import com.xingdongadmin.backend.entity.CaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseInfoRepository extends JpaRepository<CaseInfo, Long> {
}
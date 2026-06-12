package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.CaseInfoLightView;
import com.xingdongadmin.backend.dto.CaseInfoView;
import com.xingdongadmin.backend.entity.CaseInfo;
import com.xingdongadmin.backend.repository.CaseInfoRepository;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cases")
public class CaseInfoController {

  private final CaseInfoRepository caseInfoRepository;

  public CaseInfoController(CaseInfoRepository caseInfoRepository) {
    this.caseInfoRepository = caseInfoRepository;
  }

  public static class CaseInfoRequest {
    @NotBlank(message = "案例名称不能为空")
    public String name;
    public String address;
    public String completionTime;
    @NotBlank(message = "首页图片不能为空")
    public String coverImage;
    public List<String> detailImages;
    public String titleDescription;
    public Map<String, Object> features;
  }

  @GetMapping
  public List<CaseInfoView> getAllCases() {
    return caseInfoRepository.findAll().stream().map(CaseInfoView::from).toList();
  }

  // 为官网首页专门提供的轻量级接口
  @GetMapping("/home")
  public List<CaseInfoLightView> getHomeCases() {
    return caseInfoRepository.findAll().stream().map(CaseInfoLightView::from).toList();
  }

  @GetMapping("/{id}")
  public CaseInfoView getCase(@PathVariable Long id) {
    return caseInfoRepository
        .findById(id)
        .map(CaseInfoView::from)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
  public CaseInfoView createCase(@RequestBody CaseInfoRequest req) {
    CaseInfo caseInfo = new CaseInfo();
    caseInfo.setName(req.name);
    caseInfo.setAddress(req.address);
    caseInfo.setCompletionTime(req.completionTime);
    caseInfo.setCoverImage(req.coverImage);
    caseInfo.setDetailImages(req.detailImages);
    caseInfo.setTitleDescription(req.titleDescription);
    caseInfo.setFeatures(req.features);
    
    return CaseInfoView.from(caseInfoRepository.save(caseInfo));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
  public CaseInfoView updateCase(@PathVariable Long id, @RequestBody CaseInfoRequest req) {
    CaseInfo existing =
        caseInfoRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
            
    if (req.name != null) existing.setName(req.name);
    if (req.address != null) existing.setAddress(req.address);
    if (req.completionTime != null) existing.setCompletionTime(req.completionTime);
    if (req.coverImage != null) existing.setCoverImage(req.coverImage);
    if (req.detailImages != null) existing.setDetailImages(req.detailImages);
    if (req.titleDescription != null) existing.setTitleDescription(req.titleDescription);
    if (req.features != null) existing.setFeatures(req.features);
    
    return CaseInfoView.from(caseInfoRepository.save(existing));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
  public void deleteCase(@PathVariable Long id) {
    caseInfoRepository.deleteById(id);
  }
}

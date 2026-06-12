package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.BannerView;
import com.xingdongadmin.backend.entity.Banner;
import com.xingdongadmin.backend.repository.BannerRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    private final BannerRepository bannerRepository;

    public BannerController(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public static class BannerRequest {
        @NotBlank(message = "图片地址不能为空")
        public String imageUrl;
        public String linkUrl;
        public String modifier;
        @NotNull(message = "是否启用不能为空")
        public Boolean enabled;
        @NotNull(message = "优先级不能为空")
        public Integer priority;
    }

    @GetMapping
    public List<BannerView> getAllBanners(@RequestParam(required = false) Boolean enabled) {
        if (Boolean.TRUE.equals(enabled)) {
            // 前端官网展示：仅返回启用状态的数据，并按优先级倒序排列
            return bannerRepository.findByEnabledTrueOrderByPriorityDesc().stream()
                    .map(BannerView::from)
                    .toList();
        }
        // 管理后台：返回全部数据，按优先级倒序排列
        return bannerRepository.findAllByOrderByPriorityDesc().stream()
                .map(BannerView::from)
                .toList();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BannerView createBanner(@RequestBody BannerRequest req) {
        Banner banner = new Banner();
        banner.setImageUrl(req.imageUrl);
        banner.setLinkUrl(req.linkUrl);
        banner.setModifier(req.modifier);
        banner.setEnabled(req.enabled);
        banner.setPriority(req.priority);
        return BannerView.from(bannerRepository.save(banner));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BannerView updateBanner(@PathVariable Long id, @RequestBody BannerRequest req) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Banner not found"));

        if (req.imageUrl != null) banner.setImageUrl(req.imageUrl);
        if (req.linkUrl != null) banner.setLinkUrl(req.linkUrl);
        if (req.modifier != null) banner.setModifier(req.modifier);
        if (req.enabled != null) banner.setEnabled(req.enabled);
        if (req.priority != null) banner.setPriority(req.priority);

        return BannerView.from(bannerRepository.save(banner));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBanner(@PathVariable Long id) {
        bannerRepository.deleteById(id);
    }
}
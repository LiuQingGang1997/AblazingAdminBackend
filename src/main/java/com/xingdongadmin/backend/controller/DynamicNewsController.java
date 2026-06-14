package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.DynamicNewsView;
import com.xingdongadmin.backend.entity.DynamicNews;
import com.xingdongadmin.backend.repository.DynamicNewsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dynamic-news")
public class DynamicNewsController {
    
    private final DynamicNewsRepository newsRepository;
    
    public DynamicNewsController(DynamicNewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    
    public static class DynamicNewsRequest {
        @NotBlank(message = "标题不能为空")
        public String title;
        
        public String description;
        public String detailContent;
        public String imageUrl;
        public String dynamicType;
        public LocalDateTime publishDate;
        public String source;
        public Boolean enabled;
        public Integer sortOrder;
    }
    
    @GetMapping
    public List<DynamicNewsView> getAllNews(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword) {
        List<DynamicNews> newsList;
        if (type != null && !type.isEmpty()) {
            newsList = newsRepository.findByDynamicTypeAndEnabledTrueOrderByPublishDateDesc(type);
        } else if (keyword != null && !keyword.isEmpty()) {
            newsList = newsRepository.findByTitleContainingAndEnabledTrueOrderByPublishDateDesc(keyword);
        } else {
            newsList = newsRepository.findByEnabledTrueOrderByPublishDateDesc();
        }
        return newsList.stream().map(DynamicNewsView::from).toList();
    }
    
    @GetMapping("/{id}")
    @Transactional
    public DynamicNewsView getNews(@PathVariable Long id) {
        newsRepository.incrementViewCount(id);
        return newsRepository.findById(id)
                .map(DynamicNewsView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "动态新闻不存在"));
    }
    
    @GetMapping("/frontend/list")
    public List<DynamicNewsView> getFrontendNews(
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        List<DynamicNews> newsList;
        if (type != null && !type.isEmpty()) {
            newsList = newsRepository.findByDynamicTypeAndEnabledTrueOrderByPublishDateDesc(type);
        } else {
            newsList = newsRepository.findByEnabledTrueOrderByPublishDateDesc();
        }
        return newsList.stream()
                .limit(limit != null ? limit : 10)
                .map(DynamicNewsView::from)
                .toList();
    }
    
    @GetMapping("/frontend/detail")
    @Transactional
    public DynamicNewsView getFrontendDetail(@RequestParam Long id) {
        newsRepository.incrementViewCount(id);
        return newsRepository.findById(id)
                .filter(n -> Boolean.TRUE.equals(n.getEnabled()))
                .map(DynamicNewsView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "动态新闻不存在"));
    }
    
    @GetMapping("/types")
    public List<Map<String, String>> getTypes() {
        return Arrays.asList(
            Map.of("value", "NEWS", "label", "新闻动态"),
            Map.of("value", "ANNOUNCEMENT", "label", "公告通知"),
            Map.of("value", "EVENT", "label", "活动资讯"),
            Map.of("value", "PRESS", "label", "新闻发布会"),
            Map.of("value", "REPORT", "label", "专题报道")
        );
    }
    
    @GetMapping("/by-date")
    public List<DynamicNewsView> getNewsByDate(
            @RequestParam LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<DynamicNews> newsList = newsRepository.findByEnabledTrueAndPublishDateBetweenOrderByPublishDateDesc(startOfDay, endOfDay);
        return newsList.stream().map(DynamicNewsView::from).toList();
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public DynamicNewsView createNews(@RequestBody DynamicNewsRequest req) {
        DynamicNews news = new DynamicNews();
        updateNewsFields(news, req);
        news.setPublishDate(req.publishDate != null ? req.publishDate : LocalDateTime.now());
        return DynamicNewsView.from(newsRepository.save(news));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DynamicNewsView updateNews(@PathVariable Long id, @RequestBody DynamicNewsRequest req) {
        DynamicNews existing = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "动态新闻不存在"));
        updateNewsFields(existing, req);
        if (req.publishDate != null) {
            existing.setPublishDate(req.publishDate);
        }
        return DynamicNewsView.from(newsRepository.save(existing));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteNews(@PathVariable Long id) {
        newsRepository.deleteById(id);
    }
    
    private void updateNewsFields(DynamicNews news, DynamicNewsRequest req) {
        if (req.title != null) news.setTitle(req.title);
        if (req.description != null) news.setDescription(req.description);
        if (req.detailContent != null) news.setDetailContent(req.detailContent);
        if (req.imageUrl != null) news.setImageUrl(req.imageUrl);
        if (req.dynamicType != null) news.setDynamicType(req.dynamicType);
        if (req.source != null) news.setSource(req.source);
        if (req.enabled != null) news.setEnabled(req.enabled);
        if (req.sortOrder != null) news.setSortOrder(req.sortOrder);
    }
}
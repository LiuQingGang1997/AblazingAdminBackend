package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.service.OssService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final OssService ossService;

    public UploadController(OssService ossService) {
        this.ossService = ossService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = ossService.uploadFile(file);
            Map<String, Object> response = new HashMap<>();
            // 返回的结构与前端组件期待的 response.data 结构对齐
            response.put("code", 200);
            response.put("message", "上传成功");
            response.put("data", url); // 前端可以通过 response.data 取到URL
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "上传失败: " + e.getMessage(), e);
        }
    }
}
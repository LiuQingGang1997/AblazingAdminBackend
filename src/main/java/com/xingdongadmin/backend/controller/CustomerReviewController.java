package com.xingdongadmin.backend.controller;

import com.xingdongadmin.backend.dto.CustomerReviewView;
import com.xingdongadmin.backend.entity.CustomerReview;
import com.xingdongadmin.backend.repository.CustomerReviewRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customer-reviews")
public class CustomerReviewController {

    private final CustomerReviewRepository reviewRepository;

    public CustomerReviewController(CustomerReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public static class CustomerReviewRequest {
        @NotBlank(message = "客户评价内容不能为空")
        public String content;
        public String avatarUrl;
        public String companyName;
        public String position;
        @NotBlank(message = "客户名称不能为空")
        public String customerName;
        public String reviewDate;
    }

    @GetMapping
    public List<CustomerReviewView> getAllReviews() {
        return reviewRepository.findAll().stream().map(CustomerReviewView::from).toList();
    }

    @GetMapping("/{id}")
    public CustomerReviewView getReview(@PathVariable Long id) {
        return reviewRepository.findById(id)
                .map(CustomerReviewView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerReviewView createReview(@RequestBody CustomerReviewRequest req) {
        CustomerReview review = new CustomerReview();
        review.setContent(req.content);
        review.setAvatarUrl(req.avatarUrl);
        review.setCompanyName(req.companyName);
        review.setPosition(req.position);
        review.setCustomerName(req.customerName);
        review.setReviewDate(req.reviewDate);

        return CustomerReviewView.from(reviewRepository.save(review));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerReviewView updateReview(@PathVariable Long id, @RequestBody CustomerReviewRequest req) {
        CustomerReview existing = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

        if (req.content != null) existing.setContent(req.content);
        if (req.avatarUrl != null) existing.setAvatarUrl(req.avatarUrl);
        if (req.companyName != null) existing.setCompanyName(req.companyName);
        if (req.position != null) existing.setPosition(req.position);
        if (req.customerName != null) existing.setCustomerName(req.customerName);
        if (req.reviewDate != null) existing.setReviewDate(req.reviewDate);

        return CustomerReviewView.from(reviewRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }
}
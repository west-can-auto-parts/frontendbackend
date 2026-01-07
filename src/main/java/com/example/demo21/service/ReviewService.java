package com.example.demo21.service;

import com.example.demo21.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {
    public void getAllTopRecentReviews();
    public List<ReviewResponse> getReviewsFromDataBase();
}

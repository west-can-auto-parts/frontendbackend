package com.example.demo21.controller;

import com.example.demo21.dto.ReviewResponse;
import com.example.demo21.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/getAllReviews")
    public ResponseEntity<List<ReviewResponse>> getAllReview(){
        List<ReviewResponse> reviewResponses=reviewService.getAllTopRecentReviews();
        return ResponseEntity.ok().body(reviewResponses);
    }
}

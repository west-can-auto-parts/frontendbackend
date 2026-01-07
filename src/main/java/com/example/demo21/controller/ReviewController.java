package com.example.demo21.controller;

import com.example.demo21.dto.ReviewResponse;
import com.example.demo21.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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

    @GetMapping("/getReviews")
    public ResponseEntity<List<ReviewResponse>> getAllReview(){
        List<ReviewResponse> reviewResponses=reviewService.getReviewsFromDataBase();
        return ResponseEntity.ok().body(reviewResponses);
    }
    @Scheduled(cron = "0 0 1 1 * ?")
    @GetMapping("/saveReviews")
    public ResponseEntity<String> saveAllReview(){
        reviewService.getAllTopRecentReviews();
        return ResponseEntity.ok().body("Review data save in database");
    }
}

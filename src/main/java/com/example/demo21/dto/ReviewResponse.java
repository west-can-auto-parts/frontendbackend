package com.example.demo21.dto;

import lombok.Data;

@Data
public class ReviewResponse {
    private String name;
    private String url;
    private String description;
    private int rating;
    private String photoUrl;
}

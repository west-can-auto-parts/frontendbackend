package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "UserReview")
public class ReviewDocument {
    private String id;
    private String name;
    private String url;
    private String description;
    private int rating;
    private String photoUrl;
}

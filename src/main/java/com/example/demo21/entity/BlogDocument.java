package com.example.demo21.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "Blog")
public class BlogDocument {
    private String id;
    private String title;
    private String content;
    private String imageUrl;
    private List<String> categories;
    private List<String> tags;
    private String authorName;
    private String authorLinkedin;
    private Date createdAt;
    private boolean featured;
    private boolean newBlog;
}

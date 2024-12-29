package com.example.demo21.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
public class BlogResponse {

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

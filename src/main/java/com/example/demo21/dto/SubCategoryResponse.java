package com.example.demo21.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubCategoryResponse {
    private String id;
    private String name;
    private String description;
    private String categoryName;
    private List<String> images;
    private List<String> tags;
    private boolean featured;
    private boolean bestSeller;
}

package com.example.demo21.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private List<String> imageUrl;
    private String categoryName;
    private String subCategoryName;
    private List<String> tags;
    private boolean featured;
    private boolean bestSeller;
}

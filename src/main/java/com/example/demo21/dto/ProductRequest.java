package com.example.demo21.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private List<String> imageUrl;
    private String categoryId;
    private String subCategoryId;
    private List<String> tags;
    private boolean featured;
    private boolean bestSeller;
}

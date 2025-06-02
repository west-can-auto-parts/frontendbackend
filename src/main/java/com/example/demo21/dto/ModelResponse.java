package com.example.demo21.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ModelResponse {
    private String id;
    private String name;
    private String imageUrl;
    private Map<String, List<ProductItem>> subCategoryAndProduct;
}

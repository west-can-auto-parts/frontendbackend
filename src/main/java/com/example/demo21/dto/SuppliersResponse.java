package com.example.demo21.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SuppliersResponse {
    private String id;
    private String name;
    private String imageUrl;
    private String description;
    private List<String> category;
    private Map<String,Integer> subCategoryAndPosition;
    private List<String> productCategory;
    private Map<String,SupplierSubCategoryResponse> subCategory;
    private String bannerUrl;

    public SuppliersResponse(String id, String name, String logoUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = logoUrl;
    }

    public SuppliersResponse(String id, String name, String imageUrl, String description,
                             List<String> category, Map<String, Integer> subCategoryAndPosition,
                             List<String> productCategory) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.category = category;
        this.subCategoryAndPosition = subCategoryAndPosition;
        this.productCategory = productCategory;
    }

    public SuppliersResponse (String id, String name, String imageUrl, String description, Map<String, SupplierSubCategoryResponse> subCategory,String bannerUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.subCategory = subCategory;
        this.bannerUrl=bannerUrl;
    }
}

package com.example.demo21.dto;

import lombok.Data;

import java.util.List;

@Data
public class SuppliersResponse {
    private String id;
    private String name;
    private String logoUrl;
    private String category;
    private List<String> subcategory;
    private List<String> productCategory;
}

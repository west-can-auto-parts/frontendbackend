package com.example.demo21.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExtraRequest {
    private List<ProductRequest> data;
    private String categoryName;
}

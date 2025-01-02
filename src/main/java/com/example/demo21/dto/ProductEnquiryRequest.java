package com.example.demo21.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductEnquiryRequest {
    private String name;
    private String email;
    private String store;
    private List<String> productName;
    private String message;
}

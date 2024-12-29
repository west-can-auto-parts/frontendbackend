package com.example.demo21.dto;

import lombok.Data;

@Data
public class ProductEnquiryRequest {
    private String name;
    private String email;
    private String store;
    private String productName;
    private String message;
}

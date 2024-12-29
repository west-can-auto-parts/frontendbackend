package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ProductEnquiryForm")
public class ProductEnquiryDocument {
    private String id;
    private String name;
    private String email;
    private String store;
    private String productName;
    private String message;
}

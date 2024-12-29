package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Suppliers")
public class SuppliersDocument {
    private String id;
    private String name;
    private String logoUrl;
    private String category;
    private List<String> subcategory;
    private List<String> productCategory;
}

package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Category")
public class CategoryDocument {
    private String id;
    @Indexed
    private String name;
    private String description;
    private List<String> images;
    private List<String> properties;
    private List<String> tags;
    private boolean featured;
    private boolean bestSeller;
}

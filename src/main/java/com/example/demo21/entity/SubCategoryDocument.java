package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "SubCategory")
public class SubCategoryDocument {
    private String id;
    private String description;
    private List<String> images;
    private String parentId;
    private String categoryName;
    private List<String> tags;
    private boolean featured;
    private boolean bestSeller;
    private Map<String,Integer> productCategoryAndPosition;
    @Indexed
    private String categoryId;
    @Indexed
    private String name;
}

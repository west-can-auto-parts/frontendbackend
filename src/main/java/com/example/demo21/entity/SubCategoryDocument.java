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
    @Indexed(unique = true)
    private String name;
    private String description;
    private List<String> images;
    private String parentId;
    private String categoryId;
    private String categoryName;
    private List<String> tags;
    private boolean featured;
    private boolean bestSeller;
    private Map<String,Integer> productCategoryAndPosition;
}

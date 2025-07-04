package com.example.demo21.entity;

import com.example.demo21.dto.ProductItem;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "Model")
public class ModelDocument {
    private String id;
    private String name;
    private String imageUrl;
    private String bannerImageUrl;
    private Map<String, List<ProductItem>> subCategoryAndProduct;
}

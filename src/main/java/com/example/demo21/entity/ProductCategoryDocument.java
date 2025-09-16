package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "ProductCategory")
@CompoundIndexes({
        @CompoundIndex(def = "{'categoryId': 1, 'subCategoryId': 1}"),
        @CompoundIndex(def = "{'categoryId': 1, 'bestSeller': 1}"),
        @CompoundIndex(def = "{'featured': 1, 'productPosition': 1}")
})
public class ProductCategoryDocument {
    private String id;
    private String description;
    private List<String> imageUrl;
    private String categoryName;
    private String subCategoryName;
    private List<String> tags;
    private Integer productPosition;
    private int bestSellerPosition;
    private Map<String,Integer> brandAndPosition;
    @Indexed
    private String categoryId;
    @Indexed
    private String subCategoryId;
    @Indexed
    private String name;
    @Indexed
    private boolean bestSeller;
    @Indexed
    private boolean featured;
}

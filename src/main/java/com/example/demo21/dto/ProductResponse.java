package com.example.demo21.dto;

import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private List<String> imageUrl;
    private String categoryName;
    private String subCategoryName;
    private List<String> tags;
    private boolean featured;
    private boolean bestSeller;
    private Integer productPosition;
    private int bestSellerPosition;
    private Map<String,Integer> brandAndPosition;

    public ProductResponse (String name, List<String> imageUrl, String categoryName, String subCategoryName) {
        this.name=name;
        this.imageUrl=imageUrl;
        this.categoryName=categoryName;
        this.subCategoryName=subCategoryName;
    }

    public ProductResponse (String productName, String imageUrl) {
        this.name=productName;
        this.imageUrl= Collections.singletonList(imageUrl);
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl=" + imageUrl +
                '}';
    }

}

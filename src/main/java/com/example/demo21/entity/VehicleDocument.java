package com.example.demo21.entity;

import com.example.demo21.dto.ProductItem;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document(collection = "Vehicle")
public class VehicleDocument {
    private String id;
    private String name;
    private String imageUrl;
    private String bannerImageUrl;
    private List<ProductItem> model;
}

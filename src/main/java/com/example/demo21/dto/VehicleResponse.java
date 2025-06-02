package com.example.demo21.dto;

import lombok.Data;

import java.util.List;

@Data
public class VehicleResponse {
    private String id;
    private String name;
    private String imageUrl;
    private String bannerImageUrl;
    private List<ProductItem> model;
}

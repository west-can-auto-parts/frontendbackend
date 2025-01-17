package com.example.demo21.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultResponse {
    private List<ProductResponse> productResponseList;
    private List<SubCategoryResponse> subCategoryResponses;
}

package com.example.demo21.service;

import com.example.demo21.dto.ProductEnquiryRequest;
import com.example.demo21.dto.ProductResponse;
import com.example.demo21.dto.SubCategoryResponse;
import com.example.demo21.entity.ProductCategoryDocument;
import com.example.demo21.entity.SubCategoryDocument;

import java.util.List;

public interface ProductService {

    public List<ProductResponse> getAllCategory();
    public List<SubCategoryResponse> getAllSubCategory();
    public List<SubCategoryDocument> getSubCategoryByCategoryId(String id);
    public List<ProductResponse> getProductCategoryByCatIdAndSubCatId(String categoryId, String subCategoryId);
    public ProductResponse getProductCategoryByName(String id);
    public List<ProductResponse> getProductCategoriesBySubCategoryName(String subCategoryName);
    public List<ProductResponse> getAllProductCategory();
    public List<ProductResponse> getProductCategoryByCategoryName(String categoryName);
    public SubCategoryResponse getSubCategoryByName(String name);
    public String saveEnquiry(ProductEnquiryRequest enquiryRequest);
    public List<ProductResponse> getAllBestSellingProduct();
}

package com.example.demo21.controller;


import com.example.demo21.dto.ProductEnquiryRequest;
import com.example.demo21.dto.ProductRequest;
import com.example.demo21.dto.ProductResponse;
import com.example.demo21.dto.SubCategoryResponse;
import com.example.demo21.entity.SubCategoryDocument;
import com.example.demo21.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponse>> getAllCategory(){
        List<ProductResponse> pr=productService.getAllCategory();
        return ResponseEntity.ok().body(pr);
    }

    @GetMapping("/subcategory")
    public ResponseEntity<List<SubCategoryResponse>> getAllSubCategory(){
        List<SubCategoryResponse> pr=productService.getAllSubCategory();
        return ResponseEntity.ok().body(pr);
    }

    @GetMapping("/subcategory/category/{id}")
    public ResponseEntity<List<SubCategoryDocument>> getAllCategory(@PathVariable ("id") String id){
        List<SubCategoryDocument> pr=productService.getSubCategoryByCategoryId(id);
        return ResponseEntity.ok().body(pr);
    }

    @GetMapping("/subcategory/{name}")
    public ResponseEntity<SubCategoryResponse> fetchSubCategoryByName(@PathVariable ("name") String name){
        SubCategoryResponse responses =productService.getSubCategoryByName(name);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/product-category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryAndSubCategory(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subcategory) {
        List<ProductResponse> products = productService.getProductCategoryByCatIdAndSubCatId(category, subcategory);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/product-category/{name}")
    public ResponseEntity<ProductResponse> fetchProductCategoryById(@PathVariable ("name") String name){
        ProductResponse responses =productService.getProductCategoryByName(name);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/products-category/subCategoryName")
    public ResponseEntity<List<ProductResponse>> getProductsBySubCategoryName(
            @RequestParam String subCategoryName) {
        List<ProductResponse> productCategories =
                productService.getProductCategoriesBySubCategoryName(subCategoryName);
        return ResponseEntity.ok(productCategories);
    }

    @GetMapping("/product-category/all")
    public ResponseEntity<List<ProductResponse>> fetchAllProductCategory(){
        List<ProductResponse> responses =productService.getAllProductCategory();
        return ResponseEntity.ok().body(responses);
    }
    @GetMapping("/product-category/category/{categoryName}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryName(@PathVariable String categoryName) {
        try {
            List<ProductResponse> products = productService.getProductCategoryByCategoryName(categoryName);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }
    @PostMapping("/productenquiry")
    public ResponseEntity<String> submitEnquiry( @RequestBody ProductEnquiryRequest enquiryRequest) {
        try {
            String response=productService.saveEnquiry(enquiryRequest);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while submitting the enquiry.");
        }
    }

}

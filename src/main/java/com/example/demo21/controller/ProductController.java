package com.example.demo21.controller;


import com.example.demo21.dto.*;
import com.example.demo21.entity.SubCategoryDocument;
import com.example.demo21.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
@Tag(name = "Products", description = "Product management API endpoints")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;


    @Operation(summary = "Get all categories", description = "Retrieve a list of all product categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> getAllCategory(){
        try {
            long startTime = System.nanoTime();
            List<ProductResponse> pr = productService.getAllCategory();
            long endTime = System.nanoTime();
            logger.info("getAllCategory API took {} ms", TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
            return ResponseEntity.ok().body(pr);
        } catch (Exception e) {
            logger.error("Error in getAllCategory: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @Operation(summary = "Get all subcategories", description = "Retrieve a list of all product subcategories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subcategory list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/subcategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubCategoryResponse>> getAllSubCategory(){
        try {
            long startTime = System.nanoTime();
            List<SubCategoryResponse> pr = productService.getAllSubCategory();
            long endTime = System.nanoTime();
            logger.info("getAllSubCategory API took {} ms", TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
            return ResponseEntity.ok().body(pr);
        } catch (Exception e) {
            logger.error("Error in getAllSubCategory: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @Operation(summary = "Get subcategories by category ID", description = "Retrieve subcategories belonging to a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subcategory list")
    })
    @GetMapping("/subcategory/category/{id}")
    public ResponseEntity<List<SubCategoryDocument>> getAllCategory(
            @Parameter(description = "ID of the category") @PathVariable ("id") String id){
        List<SubCategoryDocument> pr=productService.getSubCategoryByCategoryId(id);
        return ResponseEntity.ok().body(pr);
    }

    @Operation(summary = "Get subcategory by name", description = "Retrieve subcategory information by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subcategory"),
            @ApiResponse(responseCode = "404", description = "Subcategory not found")
    })
    @GetMapping("/subcategory/{name}")
    public ResponseEntity<SubCategoryResponse> fetchSubCategoryByName(
            @Parameter(description = "Name of the subcategory") @PathVariable ("name") String name){
        String originalName = slugToOriginalName(name);
        SubCategoryResponse responses =productService.getSubCategoryByName(originalName);
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "Get products by category and subcategory", description = "Filter products by category and subcategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered product list")
    })
    @GetMapping("/product-category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryAndSubCategory(
            @Parameter(description = "Category filter") @RequestParam(required = false) String category,
            @Parameter(description = "Subcategory filter") @RequestParam(required = false) String subcategory) {
        List<ProductResponse> products = productService.getProductCategoryByCatIdAndSubCatId(category, subcategory);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product category by name", description = "Retrieve product category information by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product category"),
            @ApiResponse(responseCode = "404", description = "Product category not found")
    })
    @GetMapping("/product-category/{name}")
    public ResponseEntity<ProductResponse> fetchProductCategoryById(
            @Parameter(description = "Name of the product category") @PathVariable ("name") String name){
        String originalName = slugToOriginalName(name);
        ProductResponse responses = productService.getProductCategoryByName(originalName);
        return ResponseEntity.ok().body(responses);
    }

    private String slugToOriginalName(String slug) {
        if (slug == null || slug.isEmpty()) {
            return slug;
        }

        String formattedString = slug.replace("-", " ");
        formattedString = capitalizeFirstLetterOfEachWord(formattedString);

        // Replace placeholders with actual commas
        return formattedString.replace("~", ",");
    }

    private String capitalizeFirstLetterOfEachWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Handle parentheses and commas by adding spaces around them
        str = str.replace("(", " ( ").replace(")", " ) ").replace(",", " , ");
        String[] words = str.split("\\s+");
        StringBuilder capitalizedStr = new StringBuilder();

        for (String word : words) {
            if (word.equalsIgnoreCase("and")) {
                capitalizedStr.append(word.toLowerCase()).append(" ");
            } else if (word.equals("(") || word.equals(")") || word.equals(",")) {
                // Add parentheses and commas without spaces
                capitalizedStr.append(word);
            } else if (!word.isEmpty()) {
                capitalizedStr.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase()).append(" ");
            }
        }

        // Clean up any double spaces and ensure proper formatting of parentheses and commas
        return capitalizedStr.toString().trim()
                .replaceAll("\\s+", " ")
                .replace(" ( ", "(")
                .replace(" ) ", ")")
                .replace("( ", "(")
                .replace(" )", ")")
                .replace(" , ", ", ");
    }

    @Operation(summary = "Get product categories by subcategory name", description = "Retrieve products belonging to a specific subcategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product categories")
    })
    @GetMapping("/products-category/subCategoryName")
    public ResponseEntity<List<ProductResponse>> getProductsBySubCategoryName(
            @Parameter(description = "Name of the subcategory") @RequestParam String subCategoryName) {
        String originalName = slugToOriginalName(subCategoryName);
        List<ProductResponse> productCategories =
                productService.getProductCategoriesBySubCategoryName(originalName);
        return ResponseEntity.ok(productCategories);
    }

    @Operation(summary = "Get all product categories", description = "Retrieve a list of all product categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product category list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/product-category/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> fetchAllProductCategory(){
        try {
            long startTime = System.nanoTime();
            List<ProductResponse> responses = productService.getAllProductCategory();
            long endTime = System.nanoTime();
            logger.info("fetchAllProductCategory API took {} ms", TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
            return ResponseEntity.ok().body(responses);
        } catch (Exception e) {
            logger.error("Error in fetchAllProductCategory: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @Operation(summary = "Get products by category name", description = "Retrieve products belonging to a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product list"),
            @ApiResponse(responseCode = "400", description = "Invalid category name"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/product-category/category/{categoryName}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryName(
            @Parameter(description = "Name of the category") @PathVariable String categoryName) {
        try {
            List<ProductResponse> products = productService.getProductCategoryByCategoryName(categoryName);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    @Operation(summary = "Submit product enquiry", description = "Submit an enquiry about a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enquiry submitted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/productenquiry")
    public ResponseEntity<String> submitEnquiry(@RequestBody ProductEnquiryRequest enquiryRequest) {
        try {
            String response=productService.saveEnquiry(enquiryRequest);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while submitting the enquiry.");
        }
    }

    @Operation(summary = "Get best selling products", description = "Retrieve a list of best selling products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved best selling products"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/bestsellingproduct")
    public ResponseEntity<List<ProductResponse>> fetchAllBestSellingProduct() {
        try {
            List<ProductResponse> response=productService.getAllBestSellingProduct();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ArrayList<>());
        }
    }

    @Operation(summary = "Get shop by category data", description = "Retrieve category mapping for shopping functionality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved shop by category data")
    })
    @GetMapping("/shop-by-category")
    public ResponseEntity<Map<String, Map<String, String>>> shopByCategory() {
        Map<String, Map<String, String>> response=productService.getShopByCategory();
       return ResponseEntity.ok().body(response);
    }

}

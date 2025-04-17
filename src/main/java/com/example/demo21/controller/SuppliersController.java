package com.example.demo21.controller;

import com.example.demo21.dto.ProductResponse;
import com.example.demo21.dto.SuppliersResponse;
import com.example.demo21.service.SuppliersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
@Tag(name = "Suppliers", description = "Suppliers management API endpoints")
public class SuppliersController {
    @Autowired
    private SuppliersService suppliersService;

    @Operation(summary = "Search suppliers by product category", description = "Find suppliers that provide products in a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved suppliers list",
                    content = @Content(schema = @Schema(implementation = SuppliersResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<SuppliersResponse>> search(
            @Parameter(description = "Product category to search by") @RequestParam String query) {
        return ResponseEntity.ok().body(suppliersService.getSuppliersByProductCategory(query));
    }

    @Operation(summary = "Search suppliers by subcategory", description = "Find suppliers that provide products in a specific subcategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved suppliers list",
                    content = @Content(schema = @Schema(implementation = SuppliersResponse.class)))
    })
    @GetMapping("/subcategory/search")
    public ResponseEntity<List<SuppliersResponse>> searchBySubCategory(
            @Parameter(description = "Subcategory to search by") @RequestParam String query) {
        return ResponseEntity.ok().body(suppliersService.getSuppliersBySubCategory(query));
    }

    @Operation(summary = "Get supplier by name", description = "Retrieve a specific supplier by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved supplier details",
                    content = @Content(schema = @Schema(implementation = SuppliersResponse.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    @GetMapping("/name")
    public ResponseEntity<SuppliersResponse> getCategories(
            @Parameter(description = "Name of the supplier (slug format)") @RequestParam String names){
        names=slugToOriginalName(names);
        SuppliersResponse response =suppliersService.getSuppliersByName(names);
        return  ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all suppliers", description = "Retrieve a list of all suppliers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all suppliers",
                    content = @Content(schema = @Schema(implementation = SuppliersResponse.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<SuppliersResponse>> getAllSuppliers(){
        List<SuppliersResponse> suppliersResponses=suppliersService.getAll();
        return ResponseEntity.ok().body(suppliersResponses);
    }

    private String slugToOriginalName(String slug) {
        if (slug == null || slug.isEmpty()) {
            return slug;
        }
        // Replace hyphens with spaces, underscores with slashes, and convert the string to uppercase
        return slug.replace("-", " ").replace("%2B","-").replace("_", "/").toUpperCase();
    }
}

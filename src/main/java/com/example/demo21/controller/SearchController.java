package com.example.demo21.controller;

import com.example.demo21.dto.SearchResultResponse;
import com.example.demo21.service.SearchService;
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
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Search", description = "Search API endpoints")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Operation(summary = "Search products and categories", description = "Search across products, categories and subcategories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid search query")
    })
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @Parameter(description = "Search query (minimum 3 characters)") 
            @RequestParam String query) {

        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Query cannot be null or empty"));
        }

        if (query.trim().length() < 3) {
            return ResponseEntity.badRequest().body(Map.of("error", "Search query must be at least 3 characters long."));
        }
        query=slugToOriginalName(query);
        Map<String, Object> results = searchService.search(query);
        return ResponseEntity.ok(results);
    }

    private String slugToOriginalName(String slug) {
        slug = slug.replace("and", "&");

        return capitalizeFirstLetterOfEachWord(slug.replace("-", " "));
    }
    private String capitalizeFirstLetterOfEachWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Split the string into words, capitalize the first letter of each word, and join them back
        String[] words = str.split(" ");
        StringBuilder capitalizedStr = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedStr.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase()) // Keep the rest of the word lowercase
                        .append(" ");
            }
        }
        return capitalizedStr.toString().trim();
    }
}
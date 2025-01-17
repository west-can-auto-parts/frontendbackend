package com.example.demo21.controller;

import com.example.demo21.dto.SearchResultResponse;
import com.example.demo21.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String query) {
        System.out.println("Received query: " + query); // Debugging

        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Query cannot be null or empty"));
        }

        if (query.trim().length() < 3) {
            return ResponseEntity.badRequest().body(Map.of("error", "Search query must be at least 3 characters long."));
        }

        Map<String, Object> results = searchService.search(query);
        return ResponseEntity.ok(results);
    }

}
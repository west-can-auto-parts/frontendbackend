package com.example.demo21.controller;

import com.example.demo21.dto.SuppliersResponse;
import com.example.demo21.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SuppliersController {
    @Autowired
    private SuppliersService suppliersService;

    @GetMapping("/search")
    public ResponseEntity<List<SuppliersResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok().body(suppliersService.getSuppliersByProductCategory(query));
    }

    @GetMapping("/subcategory/search")
    public ResponseEntity<List<SuppliersResponse>> searchBySubCategory(@RequestParam String query) {
        return ResponseEntity.ok().body(suppliersService.getSuppliersBySubCategory(query));
    }
}

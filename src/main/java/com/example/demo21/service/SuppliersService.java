package com.example.demo21.service;

import com.example.demo21.dto.SuppliersResponse;

import java.util.List;

public interface SuppliersService {
   public List<SuppliersResponse> getSuppliersByProductCategory(String query);
   public List<SuppliersResponse> getSuppliersBySubCategory(String query);
}

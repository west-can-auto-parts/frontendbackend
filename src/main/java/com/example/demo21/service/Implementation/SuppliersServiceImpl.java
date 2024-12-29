package com.example.demo21.service.Implementation;

import com.example.demo21.dto.SuppliersResponse;
import com.example.demo21.entity.SuppliersDocument;
import com.example.demo21.repository.SuppliersRepository;
import com.example.demo21.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SuppliersServiceImpl implements SuppliersService {
    @Autowired
    private SuppliersRepository suppliersRepository;
    @Override
    public List<SuppliersResponse> getSuppliersByProductCategory (String query) {
        List<SuppliersDocument> suppliers = suppliersRepository.searchByName(query);

        List<SuppliersResponse> response = new ArrayList<>();
        for (SuppliersDocument supplier : suppliers) {
            SuppliersResponse suppliersResponse = new SuppliersResponse();
            suppliersResponse.setId(supplier.getId());
            suppliersResponse.setName(supplier.getName());
            suppliersResponse.setLogoUrl(supplier.getLogoUrl());
            suppliersResponse.setCategory(supplier.getCategory());  // Assuming headings field in Supplier
            suppliersResponse.setSubcategory(supplier.getSubcategory());
            suppliersResponse.setProductCategory(supplier.getProductCategory());
            response.add(suppliersResponse);
        }

        return response;
    }

    @Override
    public List<SuppliersResponse> getSuppliersBySubCategory (String query) {
        String regex = Pattern.compile("&").matcher(query).replaceAll("(and|&)");
        List<SuppliersDocument> suppliers = suppliersRepository.searchByNameBySubCategory(regex);
        List<SuppliersResponse> response = new ArrayList<>();
        for (SuppliersDocument supplier : suppliers) {
            SuppliersResponse suppliersResponse = new SuppliersResponse();
            suppliersResponse.setId(supplier.getId());
            suppliersResponse.setName(supplier.getName());
            suppliersResponse.setLogoUrl(supplier.getLogoUrl());
            suppliersResponse.setCategory(supplier.getCategory());  // Assuming headings field in Supplier
            suppliersResponse.setSubcategory(supplier.getSubcategory());
            suppliersResponse.setProductCategory(supplier.getProductCategory());
            response.add(suppliersResponse);
        }

        return response;
    }
}

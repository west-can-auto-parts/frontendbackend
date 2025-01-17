package com.example.demo21.service.Implementation;

import com.example.demo21.dto.ProductResponse;
import com.example.demo21.dto.SearchResultResponse;
import com.example.demo21.dto.SubCategoryResponse;
import com.example.demo21.entity.CategoryDocument;
import com.example.demo21.entity.ProductCategoryDocument;
import com.example.demo21.entity.SubCategoryDocument;
import com.example.demo21.repository.CategoryRepository;
import com.example.demo21.repository.ProductCategoryRepository;
import com.example.demo21.repository.SubCategoryRepository;
import com.example.demo21.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Map<String, Object> search (String query) {
        try {
            // Prepare the search query with regex
            String regexQuery = ".*" + query + ".*";

            // Fetch results from the repositories
            List<ProductCategoryDocument> productCategories = productCategoryRepository.searchByName(regexQuery);
            List<SubCategoryDocument> subCategories = subCategoryRepository.searchByName(regexQuery);

            // Limit results if needed
            int productCategoryLimit = (int) Math.ceil(productCategories.size() * 0.7);
            int subCategoryLimit = (int) Math.ceil(subCategories.size() * 0.3);

            List<ProductCategoryDocument> limitedProductCategories = productCategories.subList(0, Math.min(productCategoryLimit, productCategories.size()));
            List<SubCategoryDocument> limitedSubCategories = subCategories.subList(0, Math.min(subCategoryLimit, subCategories.size()));

            List<ProductResponse> productResponseList=productCategory(limitedProductCategories);
            List<SubCategoryResponse> subCategoryResponseList=getAllSubCategory(limitedSubCategories);

            // Construct response
            Map<String, Object> response = new HashMap<>();
            response.put("ProductCategory", productResponseList);
            response.put("SubCategory", subCategoryResponseList);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during the search operation.", e);
        }




    }

    private Map<String, String> subCategoryData () {
        List<SubCategoryDocument> categoryDocumentsList=subCategoryRepository.findAll();
        Map<String,String> mpList=new HashMap<>();
        for(SubCategoryDocument cat: categoryDocumentsList){
            mpList.put(cat.getId(),cat.getName());
        }
        return mpList;
    }

    private Map<String, String> categoryData (boolean value) {
        List<CategoryDocument> categoryDocumentsList=categoryRepository.findAll();
        Map<String,String> mpList=new HashMap<>();
        if(value) {
            for (CategoryDocument cat : categoryDocumentsList) {
                mpList.put(cat.getId(), cat.getName());
            }
        }
        else {
            for (CategoryDocument cat : categoryDocumentsList) {
                mpList.put(cat.getName(), cat.getId());
            }
        }
        return mpList;
    }
    public List<ProductResponse> productCategory (List<ProductCategoryDocument> productCategoryDocumentList) {
        List<ProductResponse> productResponseList=new ArrayList<>();
        Map<String,String> mp1=categoryData(true);
        Map<String,String>mp2=subCategoryData();
        for (ProductCategoryDocument pro : productCategoryDocumentList) {
            ProductResponse proRes = new ProductResponse();
            proRes.setId(pro.getId());
            proRes.setName(pro.getName());
            proRes.setDescription(pro.getDescription());
            proRes.setImageUrl(pro.getImageUrl());
            proRes.setCategoryName(mp1.get(pro.getCategoryId()));
            proRes.setSubCategoryName(mp2.get(pro.getSubCategoryId()));
            proRes.setTags(pro.getTags());
            proRes.setFeatured(pro.isFeatured());
            proRes.setBestSeller(pro.isBestSeller());
            productResponseList.add(proRes);
        }
        return productResponseList;
    }
    public List<SubCategoryResponse> getAllSubCategory (List<SubCategoryDocument> limitedSubCategories) {
        List<SubCategoryResponse> productResponseList=new ArrayList<>();
        Map<String, String> mp = categoryData(true);
        for(SubCategoryDocument sub: limitedSubCategories){
            SubCategoryResponse subCat=new SubCategoryResponse();
            subCat.setId(sub.getId());
            subCat.setName(sub.getName());
            subCat.setDescription(sub.getDescription());
            subCat.setCategoryName(mp.get(sub.getCategoryId()));
            subCat.setImages(sub.getImages());
            subCat.setTags(sub.getTags());
            subCat.setFeatured(sub.isFeatured());
            subCat.setBestSeller(sub.isBestSeller());
            productResponseList.add(subCat);
        }
        return productResponseList;
    }
}

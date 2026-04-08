package com.example.demo21.service.Implementation;

import com.example.demo21.dto.*;
import com.example.demo21.entity.CategoryDocument;
import com.example.demo21.entity.ProductCategoryDocument;
import com.example.demo21.entity.SubCategoryDocument;
import com.example.demo21.repository.CategoryRepository;
import com.example.demo21.repository.ProductCategoryRepository;
import com.example.demo21.repository.SubCategoryRepository;
import com.example.demo21.service.SearchService;
import com.example.demo21.utils.Constants;
import com.example.demo21.utils.QueryBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.elasticsearch.core.suggest.response.CompletionSuggestion;


import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public SearchServiceImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

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

    @Override
    @SuppressWarnings("unchecked")
    public SuggestionResponse fetchSuggestions(SuggestionRequestParameters parameters) {
        log.info("suggestion request: {}", parameters);

        var query = QueryBuilderService.toSuggestQuery(parameters);
        var searchHits = this.elasticsearchOperations.search(
                query, Map.class, Constants.Index.SUGGESTION
        );

        List<SuggestionItem> productCategories = new ArrayList<>();
        List<SuggestionItem> subCategories = new ArrayList<>();

        var suggest = searchHits.getSuggest();
        if (suggest == null) return SuggestionResponse.empty();

        var raw = suggest.getSuggestion(Constants.Suggestion.SUGGEST_NAME);
        if (raw == null) return SuggestionResponse.empty();

        // ✅ Fix: Cast with explicit generic type <Map> so getEntries/getOptions/getDocument resolve correctly
        CompletionSuggestion<Map> completionSuggestion = (CompletionSuggestion<Map>) raw;

        for (CompletionSuggestion.Entry<Map> entry : completionSuggestion.getEntries()) {
            for (CompletionSuggestion.Entry.Option<Map> option : entry.getOptions()) {

                SearchHit<Map> hit = option.getSearchHit();
                if (hit == null) continue;

                Map<String, Object> source = hit.getContent();
                if (source == null) continue;

                String displayName = source.get("display_name") != null
                        ? source.get("display_name").toString()
                        : option.getText();

                String type = source.get("type") != null
                        ? source.get("type").toString()
                        : "";

                if ("Product Categories".equals(type)) {
                    productCategories.add(new SuggestionItem(displayName));
                } else if ("Sub Categories".equals(type)) {
                    subCategories.add(new SuggestionItem(displayName));
                }
            }
        }

        return new SuggestionResponse(productCategories, subCategories);
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

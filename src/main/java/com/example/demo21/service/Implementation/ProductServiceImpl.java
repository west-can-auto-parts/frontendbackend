package com.example.demo21.service.Implementation;

import com.example.demo21.dto.ProductEnquiryRequest;
import com.example.demo21.dto.ProductResponse;
import com.example.demo21.dto.SubCategoryResponse;
import com.example.demo21.entity.*;
import com.example.demo21.repository.*;
import com.example.demo21.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // Constants for better maintainability
    private static final String REPLACEMENT_PARTS = "Replacement Parts";
    private static final String FLUIDS_LUBRICANTS = "Fluids & Lubricants";
    private static final String TOOLS_EQUIPMENTS = "Tools & Equipments";
    private static final String INDUSTRIAL_SAFETY = "Industrial & Safety";
    private static final String ADMIN_EMAIL = "info@westcanauto.com";
    private static final int CACHE_TTL_HOURS = 48;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductEnquiryRepository productEnquiryRepository;

    @Autowired
    private ContactServiceImpl contactService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Cacheable(value = "categories")
    public List<ProductResponse> getAllCategory() {
        try {
            List<CategoryDocument> categoryDocuments = categoryRepository.findAll();

            return categoryDocuments.stream()
                    .map(this::mapCategoryToProductResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving all categories", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "subcategories")
    public List<SubCategoryResponse> getAllSubCategory() {
        try {
            List<SubCategoryDocument> subCategoryDocuments = subCategoryRepository.findAll();
            return subCategoryDocuments.stream()
                    .map(this::mapSubCategoryToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving all subcategories", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "subcategories", key = "#id")
    public List<SubCategoryDocument> getSubCategoryByCategoryId(String id) {
        try {
            List<SubCategoryDocument> subCategoryDocuments = subCategoryRepository.findByCategoryId(id);

            return subCategoryDocuments.stream()
                    .map(this::mapToSimpleSubCategory)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving subcategories for categoryId: {}", id, e);
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "products", key = "#categoryId + '_' + #subCategoryId")
    public List<ProductResponse> getProductCategoryByCatIdAndSubCatId(String categoryId, String subCategoryId) {
        try {
            List<ProductCategoryDocument> productDocuments = fetchProductDocuments(categoryId, subCategoryId);
            if (productDocuments.isEmpty()) {
                return Collections.emptyList();
            }
            return productDocuments.stream()
                    .map(this::mapProductToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving products for categoryId: {} and subCategoryId: {}",
                    categoryId, subCategoryId, e);
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "products", key = "#name")
    public ProductResponse getProductCategoryByName(String name) {
        try {
            ProductCategoryDocument document = productCategoryRepository.findByName(name);

            if (document == null) {
                return null;
            }
            ProductResponse response = mapProductToResponse(document);
            response.setProductPosition(document.getProductPosition());
            response.setBrandAndPosition(document.getBrandAndPosition());

            return response;
        } catch (Exception e) {
            logger.error("Error retrieving product by name: {}", name, e);
            return null;
        }
    }

    @Override
    @Cacheable(value = "products", key = "#subCategoryName")
    public List<ProductResponse> getProductCategoriesBySubCategoryName(String subCategoryName) {
        try {
            List<ProductCategoryDocument> productDocuments =
                    productCategoryRepository.findBySubCategoryName(subCategoryName);

            return productDocuments.stream()
                    .map(this::mapProductToResponseWithDirectNames)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving products by subcategory name: {}", subCategoryName, e);
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponse> getAllProductCategory() {
        try {
            long startTime = System.nanoTime();
            List<ProductCategoryDocument> productDocuments = productCategoryRepository.findAll();
            List<ProductResponse> result = productDocuments.stream()
                    .map(this::mapProductToResponse)
                    .collect(Collectors.toList());

            logTimeTaken("getAllProductCategory", startTime);
            return result;
        } catch (Exception e) {
            logger.error("Error retrieving all products", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "products", key = "#categoryName")
    public List<ProductResponse> getProductCategoryByCategoryName(String categoryName) {
        try {
            long startTime = System.nanoTime();
            List<String> categoryNames = getCategoryNamesForSearch(categoryName);

            if (categoryNames.isEmpty()) {
                logger.warn("No category names found for search: {}", categoryName);
                return Collections.emptyList();
            }

            List<ProductCategoryDocument> productDocuments;

            // Single category search
            productDocuments = productCategoryRepository.findByCategoryName(categoryNames.get(0), categoryNames.get(1));

            List<ProductResponse> result = productDocuments.stream()
                    .map(this::mapProductToResponseDirect)
                    .collect(Collectors.toList());

            logTimeTaken("getProductCategoryByCategoryName for " + categoryName, startTime);
            return result;
        } catch (Exception e) {
            logger.error("Error retrieving products by category name: {}", categoryName, e);
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "subcategories", key = "#name")
    public SubCategoryResponse getSubCategoryByName(String name) {
        try {
            SubCategoryDocument entity = subCategoryRepository.findByName(name);

            if (entity == null) {
                return null;
            }
            SubCategoryResponse response = mapSubCategoryToResponse(entity);
            response.setProductCategoryAndPosition(entity.getProductCategoryAndPosition());

            return response;
        } catch (Exception e) {
            logger.error("Error retrieving subcategory by name: {}", name, e);
            return null;
        }
    }

    @Override
    @CacheEvict(value = {"products", "categories", "subcategories"}, allEntries = true)
    public String saveEnquiry(ProductEnquiryRequest enquiryRequest) {
        try {
            ProductEnquiryDocument enquiry = mapEnquiryRequestToDocument(enquiryRequest);

            String subject = "Product Enquiry";
            String emailText = buildEnquiryEmailText(enquiryRequest);

            contactService.sendEmail(ADMIN_EMAIL, subject, emailText);
            productEnquiryRepository.save(enquiry);

            logger.info("Product enquiry saved successfully for email: {}", enquiryRequest.getEmail());
            return "Enquiry saved successfully";
        } catch (Exception e) {
            logger.error("Error saving product enquiry", e);
            throw new RuntimeException("Failed to save enquiry", e);
        }
    }

    @Override
    @Cacheable(value = "products", key = "'bestsellers'")
    public List<ProductResponse> getAllBestSellingProduct() {
        try {
            List<ProductCategoryDocument> bestSellerDocuments = productCategoryRepository.getAllBestSeller();
            return bestSellerDocuments.stream()
                    .map(product -> {
                        ProductResponse response = mapProductToResponse(product);
                        response.setBestSellerPosition(product.getBestSellerPosition());
                        return response;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving best selling products", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "shopByCategory")
    public Map<String, Map<String, String>> getShopByCategory() {
        try {
            List<ProductCategoryDocument> productDocuments = productCategoryRepository.findAll();

            return productDocuments.stream()
                    .filter(product -> product.getSubCategoryName() != null && !product.getSubCategoryName().isEmpty())
                    .collect(Collectors.groupingBy(
                            ProductCategoryDocument::getSubCategoryName,
                            Collectors.toMap(
                                    ProductCategoryDocument::getName,
                                    product -> product.getCategoryName() != null ? product.getCategoryName() : "Unknown",
                                    (existing, replacement) -> existing
                            )
                    ));
        } catch (Exception e) {
            logger.error("Error building shop by category map", e);
            return Collections.emptyMap();
        }
    }

    private ProductResponse mapProductToResponseDirect(ProductCategoryDocument product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setCategoryName(product.getCategoryName());
        response.setSubCategoryName(product.getSubCategoryName());
        response.setTags(product.getTags());
        response.setFeatured(product.isFeatured());
        response.setBestSeller(product.isBestSeller());
        return response;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getCachedMap(String redisKey) {
        try {
            return (Map<String, String>) redisTemplate.opsForValue().get(redisKey);
        } catch (Exception e) {
            logger.warn("Error retrieving map from Redis cache for key: {}", redisKey, e);
            return null;
        }
    }

    private void cacheMap(String redisKey, Map<String, String> map) {
        try {
            redisTemplate.opsForValue().set(redisKey, map, CACHE_TTL_HOURS, TimeUnit.HOURS);
        } catch (Exception e) {
            logger.warn("Error storing map in Redis cache for key: {}", redisKey, e);
        }
    }

    private List<String> getCategoryNamesForSearch(String categoryName) {
        List<String> categoryNames = new ArrayList<>();

        if (REPLACEMENT_PARTS.equals(categoryName)) {
            categoryNames.add(REPLACEMENT_PARTS);
            categoryNames.add(FLUIDS_LUBRICANTS);
        } else {
            categoryNames.add(TOOLS_EQUIPMENTS);
            categoryNames.add(INDUSTRIAL_SAFETY);
        }

        return categoryNames;
    }

    private void addIfPresent(List<String> list, String value) {
        if (value != null) {
            list.add(value);
        }
    }

    private List<ProductCategoryDocument> fetchProductDocuments(String categoryId, String subCategoryId) {
        if (categoryId != null && subCategoryId == null) {
            return productCategoryRepository.findByCategoryIds(categoryId, null);
        } else {
            return productCategoryRepository.findByCategoryAndSubcategory(categoryId, subCategoryId);
        }
    }

    // Mapping methods
    private ProductResponse mapCategoryToProductResponse(CategoryDocument category) {
        ProductResponse response = new ProductResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setImageUrl(category.getImages());
        response.setTags(category.getTags());
        response.setFeatured(category.isFeatured());
        response.setBestSeller(category.isBestSeller());
        return response;
    }

    private SubCategoryResponse mapSubCategoryToResponse(SubCategoryDocument subCategory) {
        SubCategoryResponse response = new SubCategoryResponse();
        response.setId(subCategory.getId());
        response.setName(subCategory.getName());
        response.setDescription(subCategory.getDescription());
        response.setCategoryName(subCategory.getCategoryName());
        response.setImages(subCategory.getImages());
        response.setTags(subCategory.getTags());
        response.setFeatured(subCategory.isFeatured());
        response.setBestSeller(subCategory.isBestSeller());
        return response;
    }

    private SubCategoryDocument mapToSimpleSubCategory(SubCategoryDocument original) {
        SubCategoryDocument simple = new SubCategoryDocument();
        simple.setId(original.getId());
        simple.setName(original.getName());
        return simple;
    }

    private ProductResponse mapProductToResponse(ProductCategoryDocument product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setCategoryName(product.getCategoryName());
        response.setSubCategoryName(product.getSubCategoryName());
        response.setTags(product.getTags());
        response.setFeatured(product.isFeatured());
        response.setBestSeller(product.isBestSeller());
        return response;
    }

    private ProductResponse mapProductToResponseWithDirectNames(ProductCategoryDocument product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setCategoryName(product.getCategoryName());
        response.setSubCategoryName(product.getSubCategoryName());
        response.setTags(product.getTags());
        response.setFeatured(product.isFeatured());
        response.setBestSeller(product.isBestSeller());
        return response;
    }

    private ProductEnquiryDocument mapEnquiryRequestToDocument(ProductEnquiryRequest request) {
        ProductEnquiryDocument enquiry = new ProductEnquiryDocument();
        enquiry.setName(request.getName());
        enquiry.setEmail(request.getEmail());
        enquiry.setStore(request.getStore());
        enquiry.setProductName(request.getProductName());
        enquiry.setMessage(request.getMessage());
        return enquiry;
    }

    private String buildEnquiryEmailText(ProductEnquiryRequest request) {
        return String.format(
                "A new contact has been saved with the following details:\n\n" +
                        "Name: %s\n" +
                        "Email: %s\n" +
                        "Product Name: %s\n" +
                        "Store: %s\n" +
                        "Message: %s",
                request.getName(),
                request.getEmail(),
                request.getProductName(),
                request.getStore(),
                request.getMessage()
        );
    }

    private void logTimeTaken(String operation, long startTime) {
        long endTime = System.nanoTime();
        long durationMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        logger.info("{} completed in {} ms", operation, durationMs);
    }
}

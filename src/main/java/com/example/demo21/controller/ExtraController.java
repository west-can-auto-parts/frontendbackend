package com.example.demo21.controller;

import com.example.demo21.dto.ExtraDtoResponse;
import com.example.demo21.dto.ExtraRequest;
import com.example.demo21.dto.ProductRequest;
import com.example.demo21.dto.ProductResponse;
import com.example.demo21.entity.ProductCategoryDocument;
import com.example.demo21.repository.ProductCategoryRepository;
import com.example.demo21.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExtraController {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

//    @PostMapping("/add")
//    public void  addData(@RequestBody ExtraRequest extraRequest){
//        String categoryName=extraRequest.getCategoryName();
//        ExtraDtoResponse ext =subCategoryRepository.findByName(categoryName);
//        List<ProductRequest> productRequestList =extraRequest.getData();
//        List<ProductCategoryDocument> saveProduct=new ArrayList<>();
//        for(ProductRequest pro: productRequestList){
//            pro.setCategory(ext.getId());
//        }
//        for(ProductRequest pr: productRequestList){
//            ProductCategoryDocument pd=new ProductCategoryDocument();
//            pd.setCategory(pr.getCategory());
//            pd.setContent(pr.getContent());
//            pd.setImageUrl1(pr.getImageUrl1());
//            pd.setImageUrl2(pr.getImageUrl2());
//            pd.setImageUrl3(pr.getImageUrl3());
//            pd.setTags(pr.getTags());
//            pd.setName(pr.getListing());
//            saveProduct.add(pd);
//        }
//        productCategoryRepository.saveAll(saveProduct);
//    }
}

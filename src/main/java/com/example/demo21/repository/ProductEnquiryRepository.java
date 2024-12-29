package com.example.demo21.repository;

import com.example.demo21.entity.ProductEnquiryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface ProductEnquiryRepository extends MongoRepository<ProductEnquiryDocument,String> {
}

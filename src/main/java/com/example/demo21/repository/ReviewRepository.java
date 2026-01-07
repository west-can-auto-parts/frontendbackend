package com.example.demo21.repository;

import com.example.demo21.entity.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<ReviewDocument,String> {
}

package com.example.demo21.repository;

import com.example.demo21.entity.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
public interface CategoryRepository extends MongoRepository<CategoryDocument,String> {
}

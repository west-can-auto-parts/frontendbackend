package com.example.demo21.repository;

import com.example.demo21.entity.BlogDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
public interface BlogRepository extends MongoRepository<BlogDocument, String> {

}

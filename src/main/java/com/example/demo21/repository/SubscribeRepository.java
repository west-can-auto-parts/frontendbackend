package com.example.demo21.repository;

import com.example.demo21.entity.SubscribeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SubscribeRepository extends MongoRepository<SubscribeDocument,String> {
    @Query("{'email':?0}")
    SubscribeDocument findByEmail(String email);
}

package com.example.demo21.repository;

import com.example.demo21.entity.PublicUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;
@EnableMongoRepositories
public interface PublicUserRepository extends MongoRepository<PublicUserDocument,String> {
    @Query("{'email': ?0}")
    PublicUserDocument findByEmail(String email);
    @Query("{'resetToken': ?0}")
    PublicUserDocument findByResetToken(String resetToken);
}

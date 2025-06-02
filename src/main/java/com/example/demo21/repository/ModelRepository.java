package com.example.demo21.repository;

import com.example.demo21.entity.ModelDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ModelRepository extends MongoRepository<ModelDocument,String> {
    Optional<ModelDocument> findByName(String name);

}

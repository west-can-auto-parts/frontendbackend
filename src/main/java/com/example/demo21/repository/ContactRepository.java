package com.example.demo21.repository;

import com.example.demo21.entity.ContactDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<ContactDocument,String> {
}

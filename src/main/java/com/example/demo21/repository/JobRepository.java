package com.example.demo21.repository;

import com.example.demo21.entity.JobDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<JobDocument,String> {
}

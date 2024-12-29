package com.example.demo21.repository;

import com.example.demo21.entity.JobApplicationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends MongoRepository<JobApplicationDocument,String> {
}

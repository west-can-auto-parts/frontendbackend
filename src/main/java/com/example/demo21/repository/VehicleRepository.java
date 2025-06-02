package com.example.demo21.repository;

import com.example.demo21.entity.VehicleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VehicleRepository extends MongoRepository<VehicleDocument,String> {
    Optional<VehicleDocument> findByName(String name);

}

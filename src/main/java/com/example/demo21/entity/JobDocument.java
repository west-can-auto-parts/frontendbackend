package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "JobOpening")
public class JobDocument {

    private String id;
    private String jobTitle;
    private String location;
    private String company;
    private String jobDescription;
    private List<String> responsibilities;
    private List<String> qualifications;
    private String employmentType;
    private String salaryRange;
    private LocalDateTime applicationDeadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
}

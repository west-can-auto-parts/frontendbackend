package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "JobApplication")
public class JobApplicationDocument {

    private String id;
    private String applicantName;
    private String applicantEmail;
    private String phone;
    private String resume;
    private String coverLetter;
    private String positionApplied;
    private String location;
    private int experienceYears;
    private String status;
    private LocalDateTime applicationDate;
}

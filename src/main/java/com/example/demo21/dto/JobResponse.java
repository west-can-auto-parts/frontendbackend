package com.example.demo21.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobResponse {
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

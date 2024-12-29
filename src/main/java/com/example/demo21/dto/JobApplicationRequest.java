package com.example.demo21.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobApplicationRequest {
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

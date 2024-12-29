package com.example.demo21.service;

import com.example.demo21.dto.JobApplicationRequest;
import com.example.demo21.dto.JobResponse;

import java.util.List;

public interface JobService {
    public List<JobResponse> fetchAll();
    public JobResponse getById(String id);
    public String userApplication(JobApplicationRequest jobApplicationRequest);
}

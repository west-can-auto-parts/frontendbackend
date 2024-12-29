package com.example.demo21.controller;

import com.example.demo21.dto.JobApplicationRequest;
import com.example.demo21.dto.JobResponse;
import com.example.demo21.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("")
    public ResponseEntity<List<JobResponse>> fetchAll(){
        List<JobResponse> jobResponseList=jobService.fetchAll();
        return ResponseEntity.ok().body(jobResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getById(@PathVariable ("id") String id){
        JobResponse jobResponse=jobService.getById(id);
        return ResponseEntity.ok().body(jobResponse);
    }

    @PostMapping("/applied")
    public ResponseEntity<String> jobAppliedByUser(@RequestBody JobApplicationRequest jobApplicationRequest){
        String response=jobService.userApplication(jobApplicationRequest);
        return ResponseEntity.ok().body(response);
    }

}

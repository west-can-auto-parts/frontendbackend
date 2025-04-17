package com.example.demo21.controller;

import com.example.demo21.dto.JobApplicationRequest;
import com.example.demo21.dto.JobResponse;
import com.example.demo21.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
@Tag(name = "Jobs", description = "Job management API endpoints")
public class JobController {

    @Autowired
    private JobService jobService;

    @Operation(summary = "Get all jobs", description = "Retrieve a list of all available jobs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved job list",
                    content = @Content(schema = @Schema(implementation = JobResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<JobResponse>> fetchAll(){
        List<JobResponse> jobResponseList=jobService.fetchAll();
        return ResponseEntity.ok().body(jobResponseList);
    }

    @Operation(summary = "Get job by ID", description = "Retrieve a job by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the job",
                    content = @Content(schema = @Schema(implementation = JobResponse.class))),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getById(
            @Parameter(description = "ID of the job to be retrieved") @PathVariable ("id") String id){
        JobResponse jobResponse=jobService.getById(id);
        return ResponseEntity.ok().body(jobResponse);
    }

    @Operation(summary = "Apply for a job", description = "Submit a job application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/applied")
    public ResponseEntity<String> jobAppliedByUser(@RequestBody JobApplicationRequest jobApplicationRequest){
        String response=jobService.userApplication(jobApplicationRequest);
        return ResponseEntity.ok().body(response);
    }

}

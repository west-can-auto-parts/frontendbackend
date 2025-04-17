package com.example.demo21.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test", description = "Application health test endpoint")
public class TestController {

    @Operation(summary = "Test application status", description = "Check if the application is up and running")
    @ApiResponse(responseCode = "200", description = "Application is running successfully")
    @GetMapping("/")
    public String testStatus(){
        return "Application is deploy successfully";
    }
}

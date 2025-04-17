package com.example.demo21.controller;

import com.example.demo21.dto.PublicUserRequest;
import com.example.demo21.dto.PublicUserResponse;
import com.example.demo21.security.JwtUtils;
import com.example.demo21.security.JwtValidationResult;
import com.example.demo21.service.PublicUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
@Tag(name = "User", description = "User management API endpoints")
@SecurityRequirement(name = "bearerAuth")
public class PublicUserController {

    @Autowired
    private PublicUserService publicUserService;

    @Autowired
    private JwtUtils jwtTokenProvider; // Assuming you have a JwtTokenProvider for token validation

    @Operation(summary = "Get user profile", description = "Retrieve user information using JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user profile",
                    content = @Content(schema = @Schema(implementation = PublicUserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid token")
    })
    @GetMapping("")
    public ResponseEntity<?> getUserByEmail(
            @Parameter(description = "JWT authorization token with Bearer prefix") 
            @RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix from the token if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Validate the token and handle errors
        JwtValidationResult result = jwtTokenProvider.validateJwtToken(token);
        if (!result.isValid()) {
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", result.getMessage()));
        }

        // Extract email from the token
        String email = jwtTokenProvider.getEmailFromToken(token);

        // Fetch the user by email
        PublicUserResponse user = publicUserService.getUserByEmail(email);

        // Return the user details in the response
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Edit user profile", description = "Update user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully",
                    content = @Content(schema = @Schema(implementation = PublicUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/edit")
    public ResponseEntity<PublicUserResponse> editUser(
            @Parameter(description = "Updated user information") 
            @RequestBody PublicUserRequest publicUserRequest) {
        // Edit user details and return updated user
        PublicUserResponse updatedUser = publicUserService.editUser(publicUserRequest);
        return ResponseEntity.ok(updatedUser);
    }
}

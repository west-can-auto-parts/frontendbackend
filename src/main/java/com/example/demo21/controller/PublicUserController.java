package com.example.demo21.controller;

import com.example.demo21.dto.PublicUserRequest;
import com.example.demo21.dto.PublicUserResponse;
import com.example.demo21.security.JwtUtils;
import com.example.demo21.service.PublicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class PublicUserController {

    @Autowired
    private PublicUserService publicUserService;

    @Autowired
    private JwtUtils jwtTokenProvider; // Assuming you have a JwtTokenProvider for token validation

    // Endpoint to get user by email (using JWT token)
    @GetMapping("")
    public ResponseEntity<PublicUserResponse> getUserByEmail(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String email;
        try {
            email = jwtTokenProvider.getEmailFromToken(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

        PublicUserResponse user = publicUserService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // Endpoint to edit user details
    @PutMapping("/edit")
    public ResponseEntity<PublicUserResponse> editUser(@RequestBody PublicUserRequest publicUserRequest) {
        // Edit user details and return updated user
        PublicUserResponse updatedUser = publicUserService.editUser(publicUserRequest);
        return ResponseEntity.ok(updatedUser);
    }

}

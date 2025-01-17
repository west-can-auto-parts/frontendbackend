package com.example.demo21.dto;

import lombok.Data;

@Data
public class PublicUserRequest {
    private String id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private String address;
    private String nearestStore;
    private String signUpMethod;
}

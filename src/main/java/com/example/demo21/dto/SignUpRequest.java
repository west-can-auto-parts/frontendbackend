package com.example.demo21.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String nearestStore;
}

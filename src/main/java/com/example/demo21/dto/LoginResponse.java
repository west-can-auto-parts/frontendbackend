package com.example.demo21.dto;


import lombok.Data;


@Data
public class LoginResponse {
    private String jwt_token;
    private String username;
    public LoginResponse(String username, String jwt_token) {
        this.username = username;
        this.jwt_token = jwt_token;
    }

}



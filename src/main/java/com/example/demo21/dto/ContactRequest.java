package com.example.demo21.dto;

import lombok.Data;

@Data
public class ContactRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private String phoneNumber;
    private String message;
    private boolean agreed;
}

package com.example.demo21.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ContactResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private String phoneNumber;
    private String message;
    private boolean agreed;
    private Date createdAt;
}

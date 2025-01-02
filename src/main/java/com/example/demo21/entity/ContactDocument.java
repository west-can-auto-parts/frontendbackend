package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "Contact")
public class ContactDocument {
    private String id;
    private String firstName;
    private String lastName;
    private String company;
    private String email;
    private String phoneNumber;
    private String message;
    private boolean agreed;
    private Date createdAt;
}

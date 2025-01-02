package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "Subscribe")
public class SubscribeDocument {
    private String id;
    private String email;
    private Date subscribedAt;
}

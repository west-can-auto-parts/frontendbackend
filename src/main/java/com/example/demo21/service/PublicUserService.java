package com.example.demo21.service;

import com.example.demo21.dto.PublicUserRequest;
import com.example.demo21.dto.PublicUserResponse;
import com.example.demo21.entity.PublicUserDocument;

public interface PublicUserService {
    public PublicUserResponse getUserByEmail(String email);
    public PublicUserResponse editUser(PublicUserRequest publicUserRequest);
}

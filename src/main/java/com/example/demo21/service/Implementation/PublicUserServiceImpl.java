package com.example.demo21.service.Implementation;

import com.example.demo21.dto.PublicUserRequest;
import com.example.demo21.dto.PublicUserResponse;
import com.example.demo21.entity.PublicUserDocument;
import com.example.demo21.repository.PublicUserRepository;
import com.example.demo21.service.PublicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PublicUserServiceImpl implements PublicUserService {

    @Autowired
    private PublicUserRepository publicUserRepository;

    @Override
    public PublicUserResponse getUserByEmail(String email) {
        PublicUserDocument user = publicUserRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }
        return mapToRequest(user);
    }

    @Override
    public PublicUserResponse editUser(PublicUserRequest publicUserRequest) {
        PublicUserDocument existingUser = publicUserRepository.findByEmail(publicUserRequest.getEmail());
        if (existingUser == null) {
            throw new RuntimeException("User not found with email: " + publicUserRequest.getEmail());
        }

        if (publicUserRequest.getName() != null) existingUser.setName(publicUserRequest.getName());
        if (publicUserRequest.getPassword() != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode(publicUserRequest.getPassword());
            existingUser.setPassword(encryptedPassword);
        }
        if (publicUserRequest.getPhoneNumber() != null) existingUser.setPhoneNumber(publicUserRequest.getPhoneNumber());
        if (publicUserRequest.getAddress() != null) existingUser.setAddress(publicUserRequest.getAddress());
        if (publicUserRequest.getNearestStore() != null) existingUser.setNearestStore(publicUserRequest.getNearestStore());

        PublicUserDocument updatedUser = publicUserRepository.save(existingUser);
        return mapToRequest(updatedUser);
    }

    private PublicUserResponse mapToRequest(PublicUserDocument user) {
        PublicUserResponse request = new PublicUserResponse();
        request.setId(user.getId());
        request.setEmail(user.getEmail());
        request.setName(user.getName());
        request.setPhoneNumber(user.getPhoneNumber());
        request.setAddress(user.getAddress());
        request.setNearestStore(user.getNearestStore());
        request.setSignUpMethod(user.getSignUpMethod());
        return request;
    }
}

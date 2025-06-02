package com.example.demo21.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "PublicUser")
public class PublicUserDocument implements UserDetails {
    private String id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private String address;
    private String nearestStore;
    private String signUpMethod;
    private String resetToken;

    public PublicUserDocument (String name,String email) {
        this.name=name;
        this.email=email;
    }
    public PublicUserDocument(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return List.of();
    }

    @Override
    public String getUsername () {
        return "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNearestStore() {
        return nearestStore;
    }

    public void setNearestStore(String nearestStore) {
        this.nearestStore = nearestStore;
    }

    public String getSignUpMethod() {
        return signUpMethod;
    }

    public void setSignUpMethod(String signUpMethod) {
        this.signUpMethod = signUpMethod;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}

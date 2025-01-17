package com.example.demo21.security;

import com.example.demo21.entity.PublicUserDocument;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final PublicUserDocument publicUserDocument;

    public CustomUserDetails(PublicUserDocument publicUserDocument) {
        this.publicUserDocument = publicUserDocument;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming roles are not part of your PublicUserDocument, using a default role (e.g., ROLE_USER)
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // Modify this if you have roles
    }

    @Override
    public String getPassword() {
        return publicUserDocument.getPassword();
    }

    @Override
    public String getUsername() {
        return publicUserDocument.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Implement your custom logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Implement your custom logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Implement your custom logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // Implement your custom logic if needed
    }

    public PublicUserDocument getPublicUserDocument() {
        return publicUserDocument;
    }
    public String getName() {
        return publicUserDocument.getName();  // Extracting name directly from PublicUserDocument
    }
    public String getEmail(){
        return publicUserDocument.getEmail();
    }
}

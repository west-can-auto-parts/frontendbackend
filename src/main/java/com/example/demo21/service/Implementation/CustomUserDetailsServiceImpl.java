package com.example.demo21.service.Implementation;

import com.example.demo21.entity.PublicUserDocument;
import com.example.demo21.repository.PublicUserRepository;
import com.example.demo21.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PublicUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PublicUserDocument user = userRepository.findByEmail(username);  // Assuming findByEmail is defined in your repository
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user); // Wrap the PublicUserDocument in CustomUserDetails
    }
}


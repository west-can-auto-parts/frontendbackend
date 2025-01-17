package com.example.demo21.config;

import com.example.demo21.entity.PublicUserDocument;
import com.example.demo21.security.JwtUtils;
import com.example.demo21.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private final JwtUtils jwtUtils;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private AuthService authService;

    String username;
    String userEmail;
    String idAttributeKey;

    public void onAuthenticationSuccess (HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();
            if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
                username = email.split("@")[0];
                userEmail=email;
                idAttributeKey = "sub";
            } else {
                username = "";
                idAttributeKey = "id";
            }
            System.out.println("HELLO OAUTH: " + email + " : " + name + " : " + username);

            PublicUserDocument user = authService.findByEmail(email); // Assume this returns null if user not found

            if (user != null) {
                // Existing user logic
                DefaultOAuth2User oauthUser = new DefaultOAuth2User(
                        null,
                        attributes,
                        idAttributeKey
                );
                Authentication securityAuth = new OAuth2AuthenticationToken(
                        oauthUser,
                        null,
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId()
                );
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            } else {
                // New user registration logic
                PublicUserDocument newUser = new PublicUserDocument();
                newUser.setEmail(email);
                newUser.setName(username);
                newUser.setSignUpMethod(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

                authService.registerUser(newUser);

                DefaultOAuth2User oauthUser = new DefaultOAuth2User(
                        null,
                        attributes,
                        idAttributeKey
                );
                Authentication securityAuth = new OAuth2AuthenticationToken(
                        oauthUser,
                        null,
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId()
                );
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            }
            this.setAlwaysUseDefaultTargetUrl(true);

            // JWT TOKEN LOGIC
            DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
            attributes = oauth2User.getAttributes();

            // Extract necessary attributes
            email = (String) attributes.get("email");
            System.out.println("OAuth2LoginSuccessHandler: " + username + " : " + email);

            // Create UserDetailsImpl instance
            PublicUserDocument userDetails = new PublicUserDocument(
                    username,
                    userEmail
            );

            // Generate JWT token
            String jwtToken = jwtUtils.generateTokenFromOauth(userDetails);

            // Redirect to the frontend with the JWT token
            String targetUrl = UriComponentsBuilder.fromUriString(frontendUrl)
                    .queryParam("token", jwtToken)
                    .build().toUriString();
            this.setDefaultTargetUrl(targetUrl);
            super.onAuthenticationSuccess(request, response, authentication);

        }
    }
}

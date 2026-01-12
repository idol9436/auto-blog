package com.sangsang.autoblog.domain.model;

import com.sangsang.autoblog.application.command.SignupCommand;

public class User {
    
    public final String username;
    public String password;
    public final String role;
    public final String email;
    public String provider;
    public String providerId;
    public final String extraInfo;    
    public final boolean rememberMe;
    public final boolean agreeToTerms;

    enum Role { ROLE_USER }
    enum AuthProvider { GOOGLE, KAKAO }

    private User(String username,
                 String password,
                 String role,
                 String email, 
                 String provider,
                 String providerId,
                 String extraInfo, 
                 boolean rememberMe, 
                 boolean agreeToTerms) {
        
        this.username = username;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.rememberMe = rememberMe;
        this.agreeToTerms = agreeToTerms;
        this.extraInfo = extraInfo;
    }

    public static User restore(
        String username,
        String password,
        String role,
        String email, 
        String provider,
        String providerId,
        String extraInfo, 
        boolean rememberMe, 
        boolean agreeToTerms
    ) {
        return new User(
            username,
            password,
            role,
            provider,
            providerId,
            email,       
            extraInfo,
            rememberMe,  
            agreeToTerms
        );
    }

    public static User signupOriginFrom(SignupCommand cmd) {
        return new User(cmd.username(),
                        cmd.password(),
                        Role.ROLE_USER.name(),
                        cmd.email(),
                        null,
                        null, 
                        cmd.extraInfo(), 
                        false, 
                        cmd.agreeToTerms());
    }

    public static User signupOAuthFrom(SignupCommand cmd) {
        return new User(null, 
                        null, 
                        Role.ROLE_USER.name(), 
                        cmd.email(), 
                        cmd.provider(), 
                        cmd.providerId(), 
                        cmd.extraInfo(), 
                        false, 
                        cmd.agreeToTerms());
    }

    public void changePassword(String encodedPassword) {
        if (provider != null) {
            throw new IllegalStateException("OAuth user");
        }
        this.password = encodedPassword;
    }
}

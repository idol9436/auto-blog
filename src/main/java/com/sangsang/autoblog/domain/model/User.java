package com.sangsang.autoblog.domain.model;

public class User {
    
    public final String username;
    public final String password;
    public final String email;
    public final String provider;
    public final String providerId;
    public final String extraInfo;    
    public final boolean rememberMe;
    public final boolean agreeToTerms;

    private User(String username,
                 String password, 
                 String email, 
                 String provider,
                 String providerId,
                 String extraInfo, 
                 boolean rememberMe, 
                 boolean agreeToTerms) {
        
        this.username = username;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.rememberMe = rememberMe;
        this.agreeToTerms = agreeToTerms;
        this.extraInfo = extraInfo;
    }

    public static User signupWithOrigin(String username, String password, String email, String extraInfo, boolean agreeToTerms) {
        return new User(username, password, email, null, null, extraInfo, false, agreeToTerms);
    }

    public static User signupWithOAuth(String provider, String providerId, String email, String extraInfo, boolean agreeToTerms) {
        return new User(null, null, email, provider, providerId, extraInfo, false, agreeToTerms);
    }

    public static User signinWithOrigin(String username, String password, boolean rememberMe) {
        return new User(username, password, null, null, null, null, rememberMe, false);
    }
}

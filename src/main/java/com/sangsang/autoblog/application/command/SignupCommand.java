package com.sangsang.autoblog.application.command;

public record SignupCommand (
    
    Long id,
    String username,
    String password,
    String email,
    String extraInfo,
    boolean agreeToTerms,
    String provider,
    String providerId

){}

package com.sangsang.autoblog.adapter.in.web.dto;

import com.sangsang.autoblog.application.command.SignupCommand;

public record SignupReqeustDTO(
    String email,
    String password,
    String extraInfo,
    String userId,
    boolean agreeToTerms,
    String provider,
    String providerId
) {
    public SignupCommand toCommand() {
        return new SignupCommand(
            null,
            userId,
            password,
            email,
            extraInfo,
            agreeToTerms,
            provider,
            providerId
        );
    }
}
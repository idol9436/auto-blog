package com.sangsang.autoblog.application.command;

import com.sangsang.autoblog.domain.model.User;

public class SignupCommand {
    
    public final String username;
    public final String password;
    public final String email;
    public final String extraInfo;
    public final boolean agreeToTerms;

    public SignupCommand(String username, String password, String email, String extraInfo, boolean agreeToTerms){
        this.username = username;
        this.password = password;
        this.email = email;
        this.extraInfo = extraInfo;
        this.agreeToTerms = agreeToTerms;
    }

    public User toUserDomain() {
        return User.signupWithOrigin(username, password, email, extraInfo, agreeToTerms);
    }
}
